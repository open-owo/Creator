package com.xiyue.creator.Integration.Jei.Recipe.StrainerFrameRecipe.RecipeType;

import com.xiyue.creator.ModBlockEntities.MyModBlockEntities.StrainerFrameEntity.BiomeDustMap;
import com.xiyue.creator.ModBlockEntities.MyModBlockEntities.StrainerFrameEntity.GetDustForTag;
import com.xiyue.creator.tag.ItemTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xiyue.creator.Integration.Jei.Recipe.StrainerFrameRecipe.RecipeType.StrainerFrameRecipeType.fluidEnum.*;

public class StrainerFrameRecipeType {
    public enum fluidEnum{
        LAVA,
        WATER
    }

    private static final Map<TagKey<Biome>, List<BiomeDustMap.WeightedDust>> WATER_BIOME_MAP = BiomeDustMap.getWaterDustForBiome();
    private static final Map<TagKey<Biome>, List<BiomeDustMap.WeightedDust>> LAVA_BIOME_MAP = BiomeDustMap.getLavaDustForBiome();

    private final fluidEnum fluid;
    private final TagKey<Biome> tag;

    private final String BiomeName;
    private final Ingredient ingredients;

    public Fluid getFluids() {
        if (fluid == WATER) {
            return Fluids.WATER;
        }else return  Fluids.LAVA;
    }

    public String getBiomeName() {
        return BiomeName;
    }

    public Ingredient getIngredients() {
        return ingredients;
    }



    public StrainerFrameRecipeType(fluidEnum fluid, TagKey<Biome> tag){
        this.fluid = fluid;
        this.tag = tag;
        this.BiomeName = BiomeDustMap.getTagName(tag);
        if (fluid == WATER) {
            ingredients = Ingredient.of(ItemTag.MESH);
        }else ingredients = Ingredient.of(ItemTag.LAVA_MESH);
    }

    private Map<TagKey<Biome>, List<BiomeDustMap.WeightedDust>> getMap(){
        switch (fluid) {
            case LAVA -> {
                return LAVA_BIOME_MAP;
            }
            case WATER -> {
                return WATER_BIOME_MAP;
            }
            default -> {
                return null;
            }
        }
    }

    private List<BiomeDustMap.WeightedDust> getList(){
        Map<TagKey<Biome>, List<BiomeDustMap.WeightedDust>> Map = getMap();
        if (Map == null) throw new IllegalArgumentException("BiomeMap Don't exist");
        if (Map.get(tag) == null) throw new IllegalArgumentException("BiomeTag Don't exist");
        return Map.get(tag);
    }

    public List<ItemStack> getItemStackList(){
        List<BiomeDustMap.WeightedDust> list = getList();
        List<ItemStack> ItemStackList = new ArrayList<>();
        for (BiomeDustMap.WeightedDust weightedDust : list) {
            ItemStack stack = GetDustForTag.getDefaultStack(weightedDust.type());
            if (!stack.isEmpty())  ItemStackList.add(stack);
        }
        return ItemStackList;
    }
}
