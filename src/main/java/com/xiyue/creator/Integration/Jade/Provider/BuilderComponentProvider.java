package com.xiyue.creator.Integration.Jade.Provider;

import com.xiyue.creator.BuilderSystem.BuilderSystem;
import com.xiyue.creator.Creator;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderInput;
import com.xiyue.creator.MyRecipe.BuilderRecipe.BuilderRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import java.util.List;
import java.util.Optional;

public enum BuilderComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    public static final ResourceLocation BUILDER_JADE_PLUGIN = ResourceLocation.fromNamespaceAndPath(Creator.MODID, "builder_jade_plugin");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        IElementHelper elements = IElementHelper.get();
        CompoundTag tag = accessor.getServerData();
        if (!(accessor.getBlockEntity() instanceof BuilderSystem.BuilderBlockEntity blockEntity)) return;
        BuilderRecipe recipe = null;
        if (blockEntity.getLevel() != null) {
            recipe = getRecipe(tag.getString("namespace"), tag.getString("path"), blockEntity.getLevel());
        }
        if (recipe == null) return;
        List<BuilderInput.BuilderStep> builderSteps = recipe.getBuilderSteps();

        String chanceText = String.format("%d", tag.getInt("remaining_time"));

        IElement existing_time = elements.text(Component.translatable(Creator.MODID + ".remaining_time", chanceText)).scale(0.6f);
        IElement recipeStep = elements.smallItem(builderSteps.get(tag.getInt("step")).stack().ingredient().getItems()[0]).translate(new Vec2(0, -2));
        IElement progress = elements.text(Component.literal(tag.getString("progress"))).scale(0.6f).translate(new Vec2(1, -1));
        IElement result_describe = elements.text(Component.translatable(Creator.MODID + ".result_describe")).scale(0.6f).translate(new Vec2(0, -4));
        IElement result = elements.smallItem(recipe.getResult().getBlock().asItem().getDefaultInstance()).translate(new Vec2(1, -5));

        tooltip.add(existing_time);
        tooltip.add(recipeStep);
        tooltip.append(progress);
        tooltip.add(result_describe);
        tooltip.append(result);
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor accessor) {
        if (!(accessor.getBlockEntity() instanceof BuilderSystem.BuilderBlockEntity blockEntity)) return;
        BuilderRecipe recipe = null;
        if (blockEntity.getLevel() != null) {
            recipe = getRecipe(blockEntity.getNamespace(), blockEntity.getPath(), blockEntity.getLevel());
        }
        if (recipe == null) return;
        List<BuilderInput.BuilderStep> builderSteps = recipe.getBuilderSteps();

        CompoundTag tag = accessor.getServerData();
        tag.putString("namespace", blockEntity.getNamespace());
        tag.putString("path", blockEntity.getPath());
        tag.putInt("step", blockEntity.getStep());
        tag.putString("progress" , (builderSteps.get(blockEntity.getStep()).stack().count() - blockEntity.getSingle_progress()) + "/" + builderSteps.get(blockEntity.getStep()).stack().count());
        tag.putInt("remaining_time" , (blockEntity.getTotalExistingTime() - blockEntity.getExistingTime()) / 20);
    }

    @Override
    public ResourceLocation getUid() {
        return BUILDER_JADE_PLUGIN;
    }

    public BuilderRecipe getRecipe(String namespace, String path, Level level) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, path);
        Optional<RecipeHolder<?>> option = level.getRecipeManager().byKey(resourceLocation);
        if (option.isPresent() && option.get().value() instanceof BuilderRecipe recipe){
            return recipe;
        }
        return null;
    }
}
