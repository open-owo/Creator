package com.xiyue.creator.mixin;

import com.xiyue.creator.Datagen.Provider.MyRecipe.NoConsume.NoConsumeBuilder;
import com.xiyue.creator.MyRecipe.CraftingTable.NoConsumeRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mixin(ShapelessRecipeBuilder.class)
public abstract class MixinShapelessRecipeBuilder {
    @Redirect(method = "save", at = @At(value = "NEW", target = "net/minecraft/world/item/crafting/ShapelessRecipe"))
    private ShapelessRecipe createCustomRecipe(String group, CraftingBookCategory category, ItemStack result, NonNullList<Ingredient> ingredients) {
        ShapelessRecipeBuilder self = (ShapelessRecipeBuilder)(Object)this;
        if (self instanceof NoConsumeBuilder) {
            return new NoConsumeRecipe(group, category, result, ingredients);
        }
        return new ShapelessRecipe(group, category, result, ingredients);
    }
}
