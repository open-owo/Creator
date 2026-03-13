package com.xiyue.creator.api.BlockEntities.Machines;

import com.xiyue.creator.api.BlockEntities.BaseBlockEntity;
import com.xiyue.creator.api.capability.HandlerContext;
import com.xiyue.creator.api.capability.item.ConfigurableItemHandler;
import com.xiyue.creator.api.capability.item.ItemConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public abstract class MachineBlockEntity extends BaseBlockEntity{
    private final MachineSpec spec;
    private ItemStackHandler itemHandler;
    private ConfigurableItemHandler automationHandler;
    private ConfigurableItemHandler playerHandler;

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public ConfigurableItemHandler getPlayerHandler() {
        return playerHandler;
    }

    public ConfigurableItemHandler getAutomationHandler() {
        return automationHandler;
    }

    private FluidTank fluidHandler;
    private EnergyStorage energyHandler;

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, MachineSpec spec) {
        super(type, pos, state);
        this.spec = spec;
        ItemConfig itemConfig = spec.getItemConfig();
        if (itemConfig != null){
            this.itemHandler = new ItemStackHandler(itemConfig.getSlotCount()) {
                @Override
                protected void onContentsChanged(int slot) {
                    onItemContentChange(slot);
                }
            };
            this.automationHandler = new ConfigurableItemHandler(itemHandler, itemConfig.forContext(HandlerContext.AUTOMATION));
            this.playerHandler = new ConfigurableItemHandler(itemHandler, itemConfig.forContext(HandlerContext.AUTOMATION));
        }
//        spec.getFluidConfig().ifPresent(cfg -> this.fluidHandler = new FluidTank(cfg.getCapacityPerTank() * cfg.getTankCount()) {
//            @Override
//            protected void onContentsChanged() {
//                setChanged();
//            }
//        });
//
//        spec.getEnergyConfig().ifPresent(cfg -> this.energyHandler = new EnergyStorage(cfg.getCapacity(), cfg.getMaxReceive(), cfg.getMaxExtract()));
    }

    protected void onItemContentChange(int slot){
        setChanged();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (itemHandler != null) {
            tag.put("Item", itemHandler.serializeNBT(registries));
        }
        if (fluidHandler != null) {
            CompoundTag fluidTag = new CompoundTag();
            fluidHandler.writeToNBT(registries, fluidTag);
            tag.put("Fluid", fluidTag);
        }
        if (energyHandler != null) {
            tag.put("Energy", energyHandler.serializeNBT(registries));
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (itemHandler != null && tag.contains("Item")) {
            itemHandler.deserializeNBT(registries, tag.getCompound("Item"));
        }
        if (fluidHandler != null && tag.contains("Fluid")) {
            fluidHandler.readFromNBT(registries, tag.getCompound("Fluid"));
        }
        if (energyHandler != null && tag.contains("Energy")) {
            energyHandler.deserializeNBT(registries, tag.getCompound("Energy"));
        }
    }

    protected abstract boolean canWork(Level level, BlockPos pos, BlockState state);
    protected abstract void doWork(Level level, BlockPos pos, BlockState state);
    protected abstract void resetWork(Level level, BlockPos pos, BlockState state);
    protected void OnClientTick(Level level, BlockPos pos, BlockState state) {

    }

    public static void ServerTick(Level level, BlockPos pos, BlockState state, MachineBlockEntity entity) {
        if (entity.canWork(level, pos, state)){
            entity.doWork(level, pos, state);
        }else{
            entity.resetWork(level, pos, state);
        }
    }
    public static void ClientTick(Level level, BlockPos pos, BlockState state, MachineBlockEntity entity) {
        entity.OnClientTick(level, pos, state);
    }
}
