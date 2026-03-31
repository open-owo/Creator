package com.xiyue.creator.ModBlockEntities;

import com.xiyue.creator.MyRecipe.DryingRackRecipe.DryingRackInput;
import com.xiyue.creator.MyRecipe.DryingRackRecipe.DryingRackRecipe;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import com.xiyue.creator.api.BlockEntities.Machines.MachineBlockEntity;
import com.xiyue.creator.api.BlockEntities.Machines.MachineSpec;
import com.xiyue.creator.api.capability.HandlerContext;
import com.xiyue.creator.api.capability.item.ConfigurableItemHandler;
import com.xiyue.creator.api.capability.item.ItemConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.xiyue.creator.api.Blocks.Property.BlockStateProperties.HAVE_SUN;

public class DryingRackEntity extends MachineBlockEntity {
    private int[] dryingProgress;
    private int[] finish;
    private final DryingRackRecipe[] recipes;

    public int getFinish(int i) {
        return finish[i];
    }

    public int getDryingProgress(int i) {
        return dryingProgress[i];
    }

    public String getRemainingTime(int i){
        if (this.recipes[i] == null){
            getRecipe(getItemHandler().getStackInSlot(i), level, i);
            if (this.recipes[i] == null){
                return "✘";
            }
        }
        return ((this.recipes[i].processing_time() - this.getDryingProgress(i)) / 20) + "s";
    }

    public DryingRackEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, MachineSpec spec) {
        super(type, pos, blockState, spec);
        int size = getItemHandler().getSlots();
        dryingProgress = new int[size];
        recipes = new DryingRackRecipe[size];
        finish = new int[size];
        for (int i = 0; i < size; i++) {
            dryingProgress[i] = 0;
            recipes[i] = null;
            finish[i] = 0;
        }
    }
    @Override
    protected ItemStackHandler createItemHandler(ItemConfig config) {
        return new ItemStackHandler(config.getSlotCount()) {
            @Override
            protected void onContentsChanged(int slot) {
                onItemContentChange(slot);
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    @Override
    protected ConfigurableItemHandler createAutomationHandler(ItemStackHandler internal, ItemConfig config) {
        return new ConfigurableItemHandler(internal, config.forContext(HandlerContext.AUTOMATION), ((integer, stack) -> true), ((integer, stack) -> finish[integer] == 1));
    }

    //数据存储
    public void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        dryingProgress = tag.getIntArray("DryingProgress");
        finish = tag.getIntArray("finish");
    }


    public void saveAdditional(@NotNull CompoundTag tag,@NotNull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putIntArray("DryingProgress", dryingProgress);
        tag.putIntArray("finish", finish);
    }

    @Override
    protected void onItemContentChange(int slot) {
        setChanged();
        dryingProgress[slot] = 0;
        finish[slot] = 0;
        recipes[slot] = null;
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected boolean canWork(Level level, BlockPos pos, BlockState state) {
        if(!state.getValue(HAVE_SUN) && level.dimensionType().hasSkyLight() && level.isDay() && level.canSeeSky(pos.above())){
            level.setBlock(pos, state.setValue(HAVE_SUN, true), 3);
        } else if (state.getValue(HAVE_SUN) && !(level.dimensionType().hasSkyLight() && level.isDay() && level.canSeeSky(pos.above()))) {
            level.setBlock(pos, state.setValue(HAVE_SUN, false), 3);
        }
        return state.getValue(HAVE_SUN);
    }

    @Override
    protected void doWork(Level level, BlockPos pos, BlockState state) {
        ItemStackHandler itemStackHandler = this.getItemHandler();
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            ItemStack itemStack = itemStackHandler.getStackInSlot(i);

            if (itemStack.isEmpty()) {
                this.dryingProgress[i] = 0;
                this.recipes[i] = null;
                this.finish[i] = 0;
                continue;
            }

            this.getRecipe(itemStack, level, i);

            if (this.recipes[i] == null) {
                this.dryingProgress[i] = 0;
                continue;
            }

            this.dryingProgress[i]++;
            if (this.dryingProgress[i] >= this.recipes[i].processing_time()){
                ItemStack result = this.recipes[i].resultItem();
                result.setCount(result.getCount() * this.getItemHandler().getStackInSlot(i).getCount());
                itemStackHandler.setStackInSlot(i, result);
                this.finish[i] = 1;
                this.setChanged();
            }
        }
    }

    @Override
    protected void resetWork(Level level, BlockPos pos, BlockState state) {

    }


    //数据同步
    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    private void getRecipe(ItemStack inputItem, Level level, int i){
        if (recipes[i] != null) return;
        RecipeManager recipe = level.getRecipeManager();
        Optional<RecipeHolder<DryingRackRecipe>> optional = recipe.getRecipeFor(RegisterRecipe.DRYING_RACK_TYPE.get(), new DryingRackInput(inputItem), level);
        if (optional.isEmpty()) return;
        recipes[i] = optional.get().value();
    }
}
