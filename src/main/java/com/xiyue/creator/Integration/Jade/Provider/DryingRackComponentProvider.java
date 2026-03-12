package com.xiyue.creator.Integration.Jade.Provider;

import com.xiyue.creator.Creator;
import com.xiyue.creator.api.BlockEntities.DryingRackEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.items.ItemStackHandler;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import static com.xiyue.creator.api.Blocks.DryingRackBlock.HAVE_SUN;

public enum DryingRackComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;
    public static final ResourceLocation DRYING_RACK_JADE_PLUGIN = ResourceLocation.fromNamespaceAndPath(Creator.MODID, "drying_rack_jade_plugin");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        IElementHelper element = IElementHelper.get();
        CompoundTag tag = accessor.getServerData();
        if (!(accessor.getBlockEntity() instanceof DryingRackEntity entity)) return;

        IElement have_sun;
        if (tag.getBoolean("have_sun")) {
            have_sun = element.sprite(ResourceLocation.fromNamespaceAndPath(Creator.MODID, "sun"), 8, 8);
        } else {
            have_sun = element.sprite(ResourceLocation.fromNamespaceAndPath(Creator.MODID, "no_sun"), 8, 8);
        }
        tooltip.add(have_sun);

        ItemStackHandler itemStackHandler = entity.getItemHandler();
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            ItemStack itemStack = itemStackHandler.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                ITooltip sub = element.tooltip();
                sub.add(element.item(itemStack, 0.6f).translate(new Vec2(-5f, -2f)));
                String remainingTime = tag.getString("slot" + i);
                IElement timeText = element.text(Component.translatable("creator.jade.Drying_time", remainingTime));
                sub.append(timeText);
                IElement box = element.box(sub, BoxStyle.getTransparent());
                tooltip.add(box);
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag Tag, BlockAccessor accessor) {
        BlockState blockState = accessor.getBlockState();
        Tag.putBoolean("have_sun", blockState.getValue(HAVE_SUN));
        if (!(accessor.getBlockEntity() instanceof DryingRackEntity entity)) return;
        for (int i = 0; i < entity.getItemHandler().getSlots(); i++) {
            Tag.putString("slot" + i, entity.getRemainingTime(i));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return DRYING_RACK_JADE_PLUGIN;
    }
}
