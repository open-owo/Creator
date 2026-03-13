package com.xiyue.creator.api.BlockEntities;

import com.xiyue.creator.MyRecipe.DryingRackRecipe.DryingRackInput;
import com.xiyue.creator.MyRecipe.DryingRackRecipe.DryingRackRecipe;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.xiyue.creator.api.Blocks.DryingRackBlock.HAVE_SUN;

public class DryingRackEntity extends BlockEntity {
    private final ItemStackHandler itemHandler;
    private int[] dryingProgress;
    private int[] finish;
    private final DryingRackRecipe[] recipes;

    public ItemStackHandler getItemHandler(){
        return itemHandler;
    }

    public int getFinish(int i) {
        return finish[i];
    }

    public int getDryingProgress(int i) {
        return dryingProgress[i];
    }

    public String getRemainingTime(int i){
        if (this.recipes[i] == null){
            getRecipe(itemHandler.getStackInSlot(i), level, i);
            if (this.recipes[i] == null){
                return "✘";
            }
        }
        return ((this.recipes[i].processing_time() - this.getDryingProgress(i)) / 20) + "s";
    }

    public DryingRackEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int itemHandlerSize) {
        super(type, pos, blockState);
        this.itemHandler = new ItemStackHandler(itemHandlerSize) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                dryingProgress[slot] = 0;
                finish[slot] = 0;
                recipes[slot] = null;
                if (level != null) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                }
            }
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

        };
        dryingProgress = new int[itemHandlerSize];
        recipes = new DryingRackRecipe[itemHandlerSize];
        finish = new int[itemHandlerSize];
        for (int i = 0; i < itemHandlerSize; i++) {
            dryingProgress[i] = 0;
            recipes[i] = null;
            finish[i] = 0;
        }
    }

    //数据存储
    public void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        CompoundTag inventory = tag.getCompound("Inventory");
        this.itemHandler.deserializeNBT(registries, inventory);
        dryingProgress = tag.getIntArray("DryingProgress");
        finish = tag.getIntArray("finish");
    }

    public void saveAdditional(@NotNull CompoundTag tag,@NotNull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", this.itemHandler.serializeNBT(registries));
        tag.putIntArray("DryingProgress", dryingProgress);
        tag.putIntArray("finish", finish);
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

    //逻辑
    public static void serverTick(Level level, BlockPos blockPos, BlockState state, DryingRackEntity DryingRackEntity) {
        ItemStackHandler itemStackHandler = DryingRackEntity.itemHandler;

        if(!state.getValue(HAVE_SUN) && level.dimensionType().hasSkyLight() && level.isDay() && level.canSeeSky(blockPos.above())){
            level.setBlock(blockPos, state.setValue(HAVE_SUN, true), 3);
        } else if (state.getValue(HAVE_SUN) && !(level.dimensionType().hasSkyLight() && level.isDay() && level.canSeeSky(blockPos.above()))) {
            level.setBlock(blockPos, state.setValue(HAVE_SUN, false), 3);
        }
        if (state.getValue(HAVE_SUN)) {
            for (int i = 0; i < itemStackHandler.getSlots(); i++) {

                ItemStack itemStack = itemStackHandler.getStackInSlot(i);
                if (itemStack.isEmpty()) {
                    DryingRackEntity.dryingProgress[i] = 0;
                    DryingRackEntity.recipes[i] = null;
                    DryingRackEntity.finish[i] = 0;
                    continue;
                }

                DryingRackEntity.getRecipe(itemStack, level, i);

                if (DryingRackEntity.recipes[i] == null) {
                    DryingRackEntity.dryingProgress[i] = 0;
                    continue;
                }

                DryingRackEntity.dryingProgress[i]++;
                if (DryingRackEntity.dryingProgress[i] >= DryingRackEntity.recipes[i].processing_time()){
                    ItemStack result = DryingRackEntity.recipes[i].resultItem();
                    result.setCount(result.getCount() * DryingRackEntity.getItemHandler().getStackInSlot(i).getCount());
                    itemStackHandler.setStackInSlot(i, result);
                    DryingRackEntity.finish[i] = 1;
                    DryingRackEntity.setChanged();
                }
            }
        }
    }
    private void getRecipe(ItemStack inputItem, Level level, int i){
        if (recipes[i] != null) return;
        RecipeManager recipe = level.getRecipeManager();
        Optional<RecipeHolder<DryingRackRecipe>> optional = recipe.getRecipeFor(RegisterRecipe.DRYING_RACK_TYPE.get(), new DryingRackInput(inputItem), level);
        if (optional.isEmpty()) return;
        recipes[i] = optional.get().value();
    }
}
