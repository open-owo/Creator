package com.xiyue.creator.MyRecipe;

import com.xiyue.creator.Creator;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderRecipe;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderSerializer;
import com.xiyue.creator.MyRecipe.CraftingTable.NoConsumeRecipe;
import com.xiyue.creator.MyRecipe.CraftingTable.NoConsumeRecipeSerializer;
import com.xiyue.creator.MyRecipe.DryingRackRecipe.DryingRackRecipe;
import com.xiyue.creator.MyRecipe.DryingRackRecipe.DryingRackSerializer;
import com.xiyue.creator.MyRecipe.StrippingRecipe.StrippingRecipe;
import com.xiyue.creator.MyRecipe.StrippingRecipe.StrippingSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegisterRecipe {

    private final RecipeManager recipeManager;

    public RegisterRecipe() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;

        if (level != null) {
            this.recipeManager = level.getRecipeManager();
        } else {
            throw new NullPointerException("minecraft world must not be null.");
        }
    }

    public RecipeManager getRecipeManager(){
        return recipeManager;
    }

    //配方类型
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, Creator.MODID);

    public static final Supplier<RecipeType<BuilderRecipe>> BUILDER_TYPE = RECIPE_TYPES.register("builder_block", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Creator.MODID, "builder_block")));
    public static final Supplier<RecipeType<StrippingRecipe>> STRIPPING_TYPE = RECIPE_TYPES.register("stripping", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Creator.MODID, "stripping")));
    public static final Supplier<RecipeType<DryingRackRecipe>> DRYING_RACK_TYPE = RECIPE_TYPES.register("drying_rack", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Creator.MODID, "drying_rack")));
//    public static final Supplier<RecipeType<NoConsumeRecipe>> NO_CONSUME = RECIPE_TYPES.register("no_consume", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Creator.MODID, "no_consume")));

    //序列化
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Creator.MODID);

    public static final Supplier<RecipeSerializer<BuilderRecipe>> BUILDER_SERIALIZERS = RECIPE_SERIALIZERS.register("builder_block", BuilderSerializer::new);
    public static final Supplier<RecipeSerializer<StrippingRecipe>> STRIPPING_SERIALIZERS = RECIPE_SERIALIZERS.register("stripping", StrippingSerializer::new);
    public static final Supplier<RecipeSerializer<DryingRackRecipe>>DRYING_RACK_SERIALIZERS = RECIPE_SERIALIZERS.register("drying_rack", DryingRackSerializer::new);
    public static final Supplier<RecipeSerializer<NoConsumeRecipe>> NO_SERIALIZERS = RECIPE_SERIALIZERS.register("no_consume", NoConsumeRecipeSerializer::new);


}
