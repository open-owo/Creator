package com.xiyue.creator.Handler;

import com.lowdragmc.lowdraglib.side.fluid.IFluidHandlerModifiable;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.Predicate;

public class FluidStackHandler implements IFluidHandler, IFluidHandlerModifiable, INBTSerializable<CompoundTag> {

    protected FluidStack[] fluids;
    protected int[] capacities;
    @Nullable
    protected Predicate[] validators;


    public FluidStackHandler() {
        this(1, 4000);
    }

    //指定储罐数量
    public FluidStackHandler(int tanks) {
        this(tanks, 4000);
    }

    //指定储罐数量和统一容量
    public FluidStackHandler(int tanks, int tankCapacity) {
        this.fluids = new FluidStack[tanks];
        this.capacities = new int[tanks];
        this.validators = new Predicate[tanks];

        Arrays.fill(this.fluids, FluidStack.EMPTY);
        Arrays.fill(this.capacities, tankCapacity);
        this.PredicateFill(this.validators, fluids -> true);
    }

    //指定储罐容量数组
    public FluidStackHandler(int[] capacities) {
        this.fluids = new FluidStack[capacities.length];
        this.capacities = capacities.clone(); // 深度拷贝
        this.validators = new Predicate[capacities.length];

        Arrays.fill(this.fluids, FluidStack.EMPTY);
        Arrays.fill(this.validators, FluidStack.EMPTY);
        this.PredicateFill(this.validators, stack -> true);
    }

    //指定储罐数量及验证
    public FluidStackHandler(int[] capacities, Predicate<FluidStack>[] validators) {
        this.fluids = new FluidStack[capacities.length];
        this.capacities = capacities.clone(); // 深度拷贝
        this.validators = new Predicate[capacities.length];

        Arrays.fill(this.fluids, FluidStack.EMPTY);
        this.PredicateFill(this.validators, fluids -> true);
        this.PredicateFill(this.validators, validators);
    }

    //设置单个储罐容量
    public void setTankCapacity(int tank, int capacity) {
        validateTankIndex(tank);
        if (capacity < 0) throw new IllegalArgumentException("Capacity cannot be negative");
        capacities[tank] = capacity;

        if (!fluids[tank].isEmpty() && fluids[tank].getAmount() > capacity) {
            fluids[tank].setAmount(capacity);
            onContentsChanged(tank);
        }
    }

    //所有储罐统一容量
    public void setUniformCapacity(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Capacity cannot be negative");
        for (int i = 0; i < capacities.length; i++) {
            capacities[i] = capacity;
            //调整超过容量的流体
            if (!fluids[i].isEmpty() && fluids[i].getAmount() > capacity) {
                fluids[i].setAmount(capacity);
                onContentsChanged(i);
            }
        }
    }

    // 获取储罐容量
    public int getTankCapacity(int tank) {
        validateTankIndex(tank);
        return capacities[tank];
    }

    // 获取所有储罐容量数组
    public int[] getCapacities() {
        return capacities.clone();
    }

    //验证器管理
    public void setValidator(int tank, Predicate<FluidStack> validator) {
        validateTankIndex(tank);
        if (validators != null) {
            validators[tank] = validator;
        }
    }

    //IFluidHandler接口实现
    @Override
    public int getTanks() {
        return fluids.length;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        validateTankIndex(tank);
        return fluids[tank].copy(); // 返回副本防止外部修改
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        validateTankIndex(tank);
        return validators[tank].test(stack);
    }

    @Override
    public int fill(@NotNull FluidStack resource, @NotNull FluidAction action) {
        if (resource.isEmpty()) {
            return 0;
        }

        int totalFilled = 0;
        int remaining = resource.getAmount();

        // 尝试填充所有储罐
        for (int i = 0; i < fluids.length && remaining > 0; i++) {
            int filled = fillTank(i, resource.copyWithAmount(remaining), action);
            totalFilled += filled;
            remaining -= filled;
        }

        return totalFilled;
    }

    @Override
    public @NotNull FluidStack drain(@NotNull FluidStack resource, @NotNull FluidAction action) {
        if (resource.isEmpty()) {
            return FluidStack.EMPTY;
        }

        FluidStack drained = FluidStack.EMPTY;
        int remaining = resource.getAmount();

        // 尝试从所有储罐抽取
        for (int i = 0; i < fluids.length && remaining > 0; i++) {
            FluidStack partial = drainTank(i, resource.copyWithAmount(remaining), action);
            if (!partial.isEmpty()) {
                if (drained.isEmpty()) {
                    drained = partial;
                } else {
                    // 确保流体类型相同
                    if (FluidStack.isSameFluidSameComponents(drained, partial)) {
                        drained.grow(partial.getAmount());
                        remaining -= partial.getAmount();
                    } else {
                        // 流体类型不同时停止抽取
                        break;
                    }
                }
            }
        }

        return drained;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, @NotNull FluidAction action) {
        if (maxDrain <= 0) {
            return FluidStack.EMPTY;
        }

        // 优先从第一个非空储罐抽取
        for (int i = 0; i < fluids.length; i++) {
            if (!fluids[i].isEmpty()) {
                return drainTank(i, maxDrain, action);
            }
        }

        return FluidStack.EMPTY;
    }


    public int fillTank(int tank, FluidStack resource, FluidAction action) {
        validateTankIndex(tank);
        if (resource.isEmpty() || !isFluidValid(tank, resource)) {
            return 0;
        }

        FluidStack existing = fluids[tank];
        int tankCapacity = capacities[tank];
        int fillable;

        if (existing.isEmpty()) {
            fillable = Math.min(tankCapacity, resource.getAmount());
            if (action.execute()) {
                fluids[tank] = resource.copyWithAmount(fillable);
                onContentsChanged(tank);
            }
            return fillable;
        }

        if (!FluidStack.isSameFluidSameComponents(existing, resource)) {
            return 0;
        }

        fillable = Math.min(tankCapacity - existing.getAmount(), resource.getAmount());
        if (fillable > 0 && action.execute()) {
            existing.grow(fillable);
            onContentsChanged(tank);
        }
        return fillable;
    }

    public FluidStack drainTank(int tank, FluidStack resource, FluidAction action) {
        validateTankIndex(tank);
        if (resource.isEmpty() || !FluidStack.isSameFluidSameComponents(fluids[tank], resource)) {
            return FluidStack.EMPTY;
        }
        return drainTank(tank, resource.getAmount(), action);
    }

    public FluidStack drainTank(int tank, int maxDrain, FluidAction action) {
        validateTankIndex(tank);
        if (maxDrain <= 0) {
            return FluidStack.EMPTY;
        }

        FluidStack existing = fluids[tank];
        if (existing.isEmpty()) {
            return FluidStack.EMPTY;
        }

        int drainable = Math.min(existing.getAmount(), maxDrain);
        FluidStack drained = existing.copyWithAmount(drainable);

        if (action.execute()) {
            existing.shrink(drainable);
            if (existing.isEmpty()) {
                fluids[tank] = FluidStack.EMPTY;
            }
            onContentsChanged(tank);
        }
        return drained;
    }

    //NBT序列化
    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag nbt = new CompoundTag();
        ListTag tanksList = new ListTag();

        for (int i = 0; i < fluids.length; i++) {
            CompoundTag tankTag = new CompoundTag();
            tankTag.putInt("Tank", i);
            tankTag.putInt("Capacity", capacities[i]);
            fluids[i].save(provider, tankTag);
            tanksList.add(tankTag);
        }

        nbt.put("Tanks", tanksList);
        nbt.putInt("TankCount", fluids.length);
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull CompoundTag nbt) {
        ListTag tanksList = nbt.getList("Tanks", Tag.TAG_COMPOUND);
        int tankCount = nbt.getInt("TankCount");

        // 初始化或调整数组大小
        if (fluids == null || fluids.length != tankCount) {
            fluids = new FluidStack[tankCount];
            capacities = new int[tankCount];
            validators = new Predicate[tankCount];
            Arrays.fill(fluids, FluidStack.EMPTY);
            Arrays.fill(validators, (Predicate<FluidStack>) fluid -> true);
        }

        // 加载每个储罐的数据
        for (int i = 0; i < tanksList.size(); i++) {
            CompoundTag tankTag = tanksList.getCompound(i);
            int tankId = tankTag.getInt("Tank");
            if (tankId >= 0 && tankId < fluids.length) {
                capacities[tankId] = tankTag.getInt("Capacity");
                fluids[tankId] = FluidStack.parse(provider, tankTag).orElse(FluidStack.EMPTY);
            }
        }
    }

    //辅助方法
    protected void validateTankIndex(int tank) {
        if (tank < 0 || tank >= fluids.length) {
            throw new IndexOutOfBoundsException("Tank index out of range: " + tank);
        }
    }

    protected void onContentsChanged(int tank) {
    }

    public void PredicateFill(Predicate<FluidStack>[] predicates, Predicate<FluidStack> predicateFuns){
        for (int i = 0, len = predicates.length; i < len; i++){
            predicates[i] = predicateFuns;
        }
    }

    public void PredicateFill(Predicate<FluidStack>[] predicates1, Predicate<FluidStack>[] predicate2){
        if (predicates1.length < predicate2.length) throw new IndexOutOfBoundsException("predicate index out of range: " + predicates1.length);
        for (int i = 0, len = predicates1.length; i < len; i++){
            predicates1[i] = predicate2[i];
        }
    }

    @Override
    public void setFluidInTank(int tank, FluidStack stack) {
        validateTankIndex(tank);
        int capacity = capacities[tank];

        // 确保流体量不超过容量
        if (!stack.isEmpty() && stack.getAmount() > capacity) {
            stack = stack.copy();
            stack.setAmount(capacity);
        }

        fluids[tank] = stack.copy();
        onContentsChanged(tank);
    }
}