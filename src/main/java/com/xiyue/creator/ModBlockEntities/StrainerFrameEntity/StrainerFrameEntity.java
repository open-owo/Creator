package com.xiyue.creator.ModBlockEntities.StrainerFrameEntity;

import com.xiyue.creator.ModGUIS.Menus.StrainerFrameMenu;
import com.xiyue.creator.ModItems.FunctionItems.Meshes;
import com.xiyue.creator.MyEnum.DustType;
import com.xiyue.creator.api.BlockEntities.Machines.MachineBlockEntity;
import com.xiyue.creator.api.BlockEntities.Machines.MachineSpec;
import com.xiyue.creator.api.ModGUIS.Menus.IBaseMenuProvider;
import com.xiyue.creator.api.ModGUIS.Menus.MenuConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Supplier;

import static com.xiyue.creator.api.Blocks.Property.BlockStateProperties.*;

public class StrainerFrameEntity extends MachineBlockEntity implements IBaseMenuProvider {
    private static final float DURABILITY_LOSS_CHANCE = 0.8F;
    private static final int BASE_WORKTIME = 400;
    public int tickCounter;
    public int workTime;
    private final boolean canWorkInLava;
    private final Random random = new Random();
    private final MenuConfig menuConfig;
    private final Supplier<MenuType<?>> menuTypeSupplier;

    public StrainerFrameEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, MachineSpec spec, boolean canWorkInLava, MenuConfig menuConfig, Supplier<MenuType<?>> menuTypeSupplier) {
        super(type, pos, state, spec);
        this.canWorkInLava = canWorkInLava;
        this.menuConfig = menuConfig;
        this.menuTypeSupplier = menuTypeSupplier;;
    }

    public boolean canWorkInLava() {
        return canWorkInLava;
    }

    @Override
    protected void onItemContentChange(int slot) {
        super.onItemContentChange(slot);
        updateMeshState();
    }

    private void updateMeshState() {
        if (level != null && !level.isClientSide) {
            BlockState state = level.getBlockState(worldPosition);
            level.setBlock(worldPosition, state.setValue(HAS_MESH, !this.getItemHandler().getStackInSlot(0).isEmpty()), 3);
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected boolean canWork(Level level, BlockPos pos, BlockState state) {
        if (!(state.getValue(WATERLOGGED) || (canWorkInLava() && state.getValue(LAVALOGGED)))) {
            return false;
        }
        ItemStack meshStack = getItemHandler().getStackInSlot(0);
        return meshStack.getItem() instanceof Meshes;
    }

    @Override
    protected void doWork(Level level, BlockPos pos, BlockState state) {
        ItemStack meshStack = getItemHandler().getStackInSlot(0);
        Meshes mesh = (Meshes) meshStack.getItem();

        if (state.getValue(LAVALOGGED) && !mesh.getCan_used_in_lava()) {
            meshStack.setDamageValue(meshStack.getDamageValue() + 1);
            if (meshStack.getDamageValue() >= meshStack.getMaxDamage()) {
                getItemHandler().setStackInSlot(0, ItemStack.EMPTY);
            }
            setChanged();
            resetWork(level,  pos, state);
            return;
        }

        if (tickCounter == 0) {
            workTime = BASE_WORKTIME * level.random.nextIntBetweenInclusive(mesh.getMin_workTime(), mesh.getMax_workTime());
        }

        ++tickCounter;
        if (tickCounter >= workTime) {
            Generate(level, pos);
            resetWork(level, pos, state);
        }
    }

    @Override
    protected void resetWork(Level level, BlockPos pos, BlockState state) {
        tickCounter = 0;
        workTime = 0;
    }

    //数据存储
    public void loadAdditional(@NotNull CompoundTag tag,@NotNull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.tickCounter = tag.getInt("tickCounter");
        this.workTime = tag.getInt("workTime");
    }

    public void saveAdditional(@NotNull CompoundTag tag,@NotNull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("tickCounter", this.tickCounter);
        tag.putInt("workTime", this.workTime);
    }

    public void Generate(Level level, BlockPos pos) {
        List<BiomeDustMap.WeightedDust> list = getDustList(level, pos);
        ItemStack meshStack = this.getItemHandler().getStackInSlot(0);
        if (list.isEmpty()) return;
        addDustToOutput(list);
        if (level.random.nextFloat() < DURABILITY_LOSS_CHANCE) {
            meshStack.setDamageValue(meshStack.getDamageValue() + 1);
            if (meshStack.getDamageValue() >= meshStack.getMaxDamage()) {
                this.getItemHandler().setStackInSlot(0, ItemStack.EMPTY);
            }
        }
    }

    private List<BiomeDustMap.WeightedDust> getDustList(Level level, BlockPos pos) {
        Map<TagKey<Biome>, List<BiomeDustMap.WeightedDust>> dustMap;
        BlockState state = level.getBlockState(pos);
        if (state.getValue(WATERLOGGED)) {
            dustMap = BiomeDustMap.getWaterDustForBiome();
        } else if (canWorkInLava() && state.getValue(LAVALOGGED)) {
            dustMap = BiomeDustMap.getLavaDustForBiome();
        } else {
            return Collections.emptyList();
        }
        List<BiomeDustMap.WeightedDust> defaultList = dustMap.getOrDefault(null, Collections.emptyList());
        Holder<Biome> biomeHolder = level.getBiome(pos);
        for (Map.Entry<TagKey<Biome>, List<BiomeDustMap.WeightedDust>> entry : dustMap.entrySet()) {
            TagKey<Biome> tag = entry.getKey();
            if (tag != null && biomeHolder.is(tag)) {
                return entry.getValue();
            }
        }
        return defaultList;
    }

    private void addDustToOutput(List<BiomeDustMap.WeightedDust> list) {
        if (list.isEmpty()) return;

        int totalWeight = list.stream().mapToInt(BiomeDustMap.WeightedDust::weight).sum();
        int target = random.nextInt(totalWeight) + 1;
        int accumulatedWeight = 0;

        for (BiomeDustMap.WeightedDust weightedDust : list) {
            accumulatedWeight += weightedDust.weight();
            if (accumulatedWeight >= target) {
                DustType dustType = weightedDust.type();
                ItemStack stackToAdd = GetDustForTag.getDefaultStack(dustType);
                if (stackToAdd.isEmpty()) continue;
                for (int i = 1; i < this.getItemHandler().getSlots(); i++) {
                    ItemStack existingStack = this.getItemHandler().getStackInSlot(i);
                    if (existingStack.isEmpty()) {
                        this.getItemHandler().setStackInSlot(i, stackToAdd.copy());
                        return;
                    }
                    if (ItemStack.isSameItem(existingStack, stackToAdd) && existingStack.getCount() < existingStack.getMaxStackSize()) {
                        existingStack.grow(1);
                        return;
                    }
                }
                break;
            }
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        if (this.getLevel() != null) {
            return new StrainerFrameMenu(menuTypeSupplier.get() ,id, inventory, this, ContainerLevelAccess.create(this.getLevel(), this.getBlockPos()));
        }
        return new StrainerFrameMenu(menuTypeSupplier.get(),id, inventory, this, ContainerLevelAccess.NULL);
    }

    @Override
    public MenuConfig getMenuConfig() {
        return menuConfig;
    }
}
