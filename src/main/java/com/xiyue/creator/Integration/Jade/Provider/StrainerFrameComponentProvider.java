package com.xiyue.creator.Integration.Jade.Provider;

import com.xiyue.creator.Creator;
import com.xiyue.creator.ModBlockEntities.MyModBlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import com.xiyue.creator.tag.ItemTag;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import static com.xiyue.creator.ModBlocks.FunctionBlocks.StrainerFrame.StrainerFrameBlock.LAVALOGGED;
import static com.xiyue.creator.ModBlocks.FunctionBlocks.StrainerFrame.StrainerFrameBlock.WATERLOGGED;

public enum StrainerFrameComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    public static final ResourceLocation STRAINER_FRAME_JADE_PLUGIN = ResourceLocation.fromNamespaceAndPath(Creator.MODID, "strainer_frame_jade_plugin");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        IElementHelper elements = IElementHelper.get();
        CompoundTag tag = accessor.getServerData();
        float baseYOffset = 0.8f;
        if (!(accessor.getBlockEntity() instanceof StrainerFrameEntity blockEntity)) return;

        if (!tag.getBoolean("have_mesh") || (blockEntity.isWorkInLava() && !tag.getBoolean("have_lava_mesh") && tag.getBoolean("in_lava"))) {
            IElement f = elements.text(Component.translatable("creator.nomesh").withStyle(ChatFormatting.RED)).translate(new Vec2(0, baseYOffset));
            tooltip.add(f);
        }
        if (!tag.getBoolean("have_fluid")) {
            IElement FluidElement = elements.text((blockEntity.isWorkInLava() ? Component.translatable("creator.nolavaorwater") : Component.translatable("creator.nowater")).withStyle(ChatFormatting.RED)).translate(new Vec2(0, baseYOffset));
            tooltip.add(FluidElement);
        }
        if(blockEntity.isWorkInLava() && tag.getBoolean("in_lava") ? tag.getBoolean("have_fluid") && tag.getBoolean("have_lava_mesh") : tag.getBoolean("have_fluid") && tag.getBoolean("have_mesh")) {
            tooltip.add(elements.text(Component.translatable("creator.working").withStyle(ChatFormatting.GREEN)).translate(new Vec2(0, baseYOffset)));
        }
        if (tag.getInt("work_time") > 0) {
            float progress = Math.min(1.0f, (float) tag.getInt("tick_counter") / tag.getInt("work_time"));
            IElement pro = elements.progress(progress, ResourceLocation.fromNamespaceAndPath(Creator.MODID,"jade_strainer_base_progress"), ResourceLocation.fromNamespaceAndPath(Creator.MODID,"jade_strainer_progress"), 80,6, false);
            tooltip.add(pro);
        }
    }

    @Override
    public void appendServerData(CompoundTag Tag, BlockAccessor accessor) {
        StrainerFrameEntity blockEntity = (StrainerFrameEntity)accessor.getBlockEntity();
        BlockState blockState = accessor.getBlockState();
        Tag.putBoolean("have_mesh", !blockEntity.getItemHandler().getStackInSlot(0).isEmpty());
        Tag.putBoolean("have_lava_mesh", blockEntity.getItemHandler().getStackInSlot(0).is(ItemTag.LAVA_MESH));
        Tag.putBoolean("have_fluid", blockEntity.isWorkInLava() ? (blockState.getValue(LAVALOGGED) || blockState.getValue(WATERLOGGED)): blockState.getValue(WATERLOGGED));
        Tag.putBoolean("in_lava", blockState.getValue(LAVALOGGED));
        Tag.putInt("work_time", blockEntity.workTime);
        Tag.putInt("tick_counter", blockEntity.tickCounter);
    }

    @Override
    public ResourceLocation getUid() {
        return STRAINER_FRAME_JADE_PLUGIN;
    }
}
