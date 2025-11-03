package com.xiyue.creator.api.BlockEntities.StrainerFrameEntity;

import com.xiyue.creator.ModItems.FunctionItems.Meshes;
import com.xiyue.creator.MyEnum.DustType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.xiyue.creator.api.Blocks.StrainerFrameBlock.*;

public abstract class StrainerFrameEntity extends BlockEntity implements MenuProvider {
    private static final float DURABILITY_LOSS_CHANCE = 0.8F;
    private static final int BASE_WORKTIME = 400;
    private final ItemStackHandler itemHandler;
    public int tickCounter;
    public int workTime;
    private boolean workInLava = false;
    private final Random random = new Random();

    public boolean isWorkInLava() {
        return workInLava;
    }

    public ItemStackHandler getItemHandler() {
        return this.itemHandler;
    }

    public StrainerFrameEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int itemHandlerSize) {
        super(type, pos, state);
        this.itemHandler = new ItemStackHandler(itemHandlerSize) {
            @Override
            protected void onContentsChanged(int slot) {
                updateMeshState();
                setChanged();
            }
        };
    }

    public StrainerFrameEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int itemHandlerSize, boolean workInLava) {
        super(type, pos, state);
        this.itemHandler = new ItemStackHandler(itemHandlerSize) {

            @Override
            protected void onContentsChanged(int slot) {
                updateMeshState();
                setChanged();
            }
        };
        this.workInLava = workInLava;
    }

    private void updateMeshState() {
        if (level != null && !level.isClientSide) {
            BlockState state = level.getBlockState(worldPosition);
            level.setBlock(worldPosition, state.setValue(HAS_MESH, !this.itemHandler.getStackInSlot(0).isEmpty()), 3);
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }
    //数据存储
    public void loadAdditional(@NotNull CompoundTag tag,@NotNull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        CompoundTag inventory = tag.getCompound("Inventory");
        this.itemHandler.deserializeNBT(registries, inventory);
        this.tickCounter = tag.getInt("tickCounter");
        this.workTime = tag.getInt("workTime");
        this.workInLava = tag.getBoolean("workInLava");
    }

    public void saveAdditional(@NotNull CompoundTag tag,@NotNull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", this.itemHandler.serializeNBT(registries));
        tag.putInt("tickCounter", this.tickCounter);
        tag.putInt("workTime", this.workTime);
        tag.putBoolean("workInLava", this.workInLava);
    }
    //数据同步
    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    //工作逻辑
    public static void serverTick(Level level, BlockPos pos, BlockState state, StrainerFrameEntity blockEntity) {
        ItemStack meshStack = blockEntity.itemHandler.getStackInSlot(0);
        if (blockEntity.tickCounter == 0 && (state.getValue(WATERLOGGED) || state.getValue(LAVALOGGED)) && blockEntity.itemHandler.getStackInSlot(0).getItem() instanceof Meshes mesh) {
            if (state.getValue(LAVALOGGED) && !mesh.getCan_used_in_lava()) {
                meshStack.setDamageValue(meshStack.getDamageValue() + 1);
                if (meshStack.getDamageValue() >= meshStack.getMaxDamage()) {
                    blockEntity.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
                    blockEntity.setChanged();
                }
                return;
            }
            blockEntity.workTime = BASE_WORKTIME * level.random.nextIntBetweenInclusive(mesh.getMin_workTime(), mesh.getMax_workTime());
        } else if ((!state.getValue(WATERLOGGED) && !state.getValue(LAVALOGGED)) || !(blockEntity.itemHandler.getStackInSlot(0).getItem() instanceof Meshes)) {
            blockEntity.workTime = 0;
            blockEntity.tickCounter = 0;
            blockEntity.setChanged();
            return;
        }
        ++blockEntity.tickCounter;
        if (blockEntity.tickCounter >= blockEntity.workTime) {
            blockEntity.Generate(level, pos);
            blockEntity.tickCounter = 0;
            blockEntity.workTime = 0;
        }
        blockEntity.setChanged();
    }

    public void Generate(Level level, BlockPos pos) {
        List<BiomeDustMap.WeightedDust> list = getDustList(level, pos);
        ItemStack meshStack = this.itemHandler.getStackInSlot(0);
        if (list.isEmpty()) return;
        addDustToOutput(list);
        if (level.random.nextFloat() < DURABILITY_LOSS_CHANCE) {
            meshStack.setDamageValue(meshStack.getDamageValue() + 1);
            meshStack.setDamageValue(meshStack.getDamageValue() + 1);
            if (meshStack.getDamageValue() >= meshStack.getMaxDamage()) {
                this.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
            }
        }
    }

    private List<BiomeDustMap.WeightedDust> getDustList(Level level, BlockPos pos) {
        Map<TagKey<Biome>, List<BiomeDustMap.WeightedDust>> dustMap;
        BlockState state = level.getBlockState(pos);
        if (state.getValue(WATERLOGGED)) {
            dustMap = BiomeDustMap.getWaterDustForBiome();
        } else if (workInLava && state.getValue(LAVALOGGED)) {
            dustMap = BiomeDustMap.getLavaDustForBiome();
        } else {
            return Collections.emptyList();
        }
        // 获取默认配置（null键）
        List<BiomeDustMap.WeightedDust> defaultList = dustMap.getOrDefault(null, Collections.emptyList());
        // 检查当前生物群系的标签
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
                // 尝试添加到输出槽
                for (int i = 1; i < this.itemHandler.getSlots(); i++) {
                    ItemStack existingStack = this.itemHandler.getStackInSlot(i);
                    if (existingStack.isEmpty()) {
                        this.itemHandler.setStackInSlot(i, stackToAdd.copy());
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
}
