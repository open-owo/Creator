package com.xiyue.creator.mixin;

import com.xiyue.creator.MyRecipe.CraftingTable.NoConsumeRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(RecipeManager.class)
public abstract class MixinGetRemainingItems {
    @Shadow public abstract <I extends RecipeInput, T extends Recipe<I>> Optional<RecipeHolder<T>> getRecipeFor(RecipeType<T> recipeType, I input, Level level);

    @Inject(method = "getRemainingItemsFor", at = @At("HEAD"), cancellable = true)
    public <I extends RecipeInput, T extends Recipe<I>> void getRemainingItemsFor(RecipeType<T> recipeType, I input, Level level, CallbackInfoReturnable<NonNullList<ItemStack>> cir) {
            Optional<RecipeHolder<T>> optional = this.getRecipeFor(recipeType, input, level);
            if (optional.isPresent()) {
                if (optional.get().value() instanceof NoConsumeRecipe recipe){
                    cir.setReturnValue(recipe.getRemainingItems((CraftingInput) input));
                    cir.cancel();
                }
            }
    }
}
