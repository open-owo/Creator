package com.xiyue.creator.MyRecipe.CraftingTable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class NoConsumeRecipeSerializer implements RecipeSerializer<NoConsumeRecipe> {
    public static final NoConsumeRecipeSerializer INSTANCE = new NoConsumeRecipeSerializer();

    // 复制并修改自 ShapelessRecipe.Serializer
    private static final MapCodec<NoConsumeRecipe> CODEC = RecordCodecBuilder.mapCodec((p_340779_) ->
            p_340779_.group(
                    Codec.STRING.optionalFieldOf("group", "").forGetter(NoConsumeRecipe::getGroup),
                    CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(NoConsumeRecipe::category),
                    ItemStack.STRICT_CODEC.fieldOf("result").forGetter((p_301142_) -> p_301142_.result),
                    Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap((p_301021_) -> {
                        Ingredient[] aingredient = p_301021_.toArray(new Ingredient[0]);
                        if (aingredient.length == 0) {
                            return DataResult.error(() -> "No ingredients for shapeless recipe");
                        } else {
                            return aingredient.length > ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth() ?
                                    DataResult.error(() -> "Too many ingredients for shapeless recipe. The maximum is: %s".formatted(ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth())) :
                                    DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                        }
                    }, DataResult::success).forGetter(NoConsumeRecipe::getIngredients)
            ).apply(p_340779_, NoConsumeRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NoConsumeRecipe> STREAM_CODEC =
            StreamCodec.of(NoConsumeRecipeSerializer::toNetwork, NoConsumeRecipeSerializer::fromNetwork);

    @Override
    public MapCodec<NoConsumeRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, NoConsumeRecipe> streamCodec() {
        return STREAM_CODEC;
    }

    private static NoConsumeRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        String s = buffer.readUtf();
        CraftingBookCategory craftingbookcategory = buffer.readEnum(CraftingBookCategory.class);
        int i = buffer.readVarInt();
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

        for(int j = 0; j < nonnulllist.size(); j++) {
            nonnulllist.set(j, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
        }

        ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
        return new NoConsumeRecipe(s, craftingbookcategory, itemstack, nonnulllist);
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, NoConsumeRecipe recipe) {
        buffer.writeUtf(recipe.getGroup());
        buffer.writeEnum(recipe.category());
        buffer.writeVarInt(recipe.getIngredients().size());

        for(Ingredient ingredient : recipe.getIngredients()) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
        }

        ItemStack.STREAM_CODEC.encode(buffer, recipe.getResultItem(null));
    }
}
