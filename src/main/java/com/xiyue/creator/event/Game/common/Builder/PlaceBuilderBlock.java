package com.xiyue.creator.event.Game.common.Builder;

import com.xiyue.creator.BuilderSystem.BuilderSystem;
import com.xiyue.creator.Creator;
import com.xiyue.creator.ModBlocks.ModBlockGroup;
import com.xiyue.creator.networking.BuilderBlockNetworking.BuildingPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = Creator.MODID)
public class PlaceBuilderBlock {

@SubscribeEvent(priority = EventPriority.HIGHEST)
public static void placeBuilder(PlayerInteractEvent.RightClickBlock event) {
    Player player = event.getEntity();
    CompoundTag data = player.getPersistentData();

    if (!data.getBoolean("BuildingStrainer")) return;

    String RecipeID = data.getString("recipe");
    if (RecipeID.isEmpty()) return;

    Level level = event.getLevel();
    int colonIndex = RecipeID.indexOf(':');
    if (colonIndex == -1) return;

    String namespace = RecipeID.substring(0, colonIndex);
    String path = RecipeID.substring(colonIndex + 1);
    if (namespace.isEmpty() || path.isEmpty()) return;

    event.setCanceled(true);
    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));

    if (!level.isClientSide()) {
        BlockPos placementPos = calculatePlacementPos(event);

        if (placementPos != null && level.getBlockState(placementPos).canBeReplaced()) {

            if (event.getFace() != null) {
                level.setBlockAndUpdate(placementPos, ModBlockGroup.BUILDER.get().defaultBlockState());
            }

            if (level.getBlockEntity(placementPos) instanceof BuilderSystem.BuilderBlockEntity builderEntity) {
                builderEntity.setRecipe(namespace, path, level);
                if (event.getFace() != null) {
                    level.sendBlockUpdated(placementPos, builderEntity.getBlockState(), builderEntity.getBlockState(), Block.UPDATE_CLIENTS);
                }
            }



            level.playSound(null, placementPos, SoundEvents.ITEM_FRAME_PLACE, SoundSource.BLOCKS, 1, 1);
            player.getPersistentData().putBoolean("BuildingStrainer", false);

            PacketDistributor.sendToPlayer((ServerPlayer) player,
                    new BuildingPacket("", false));
        }
    } else {
        BuilderSystem.reset();
    }
}
    private static BlockPos calculatePlacementPos(PlayerInteractEvent.RightClickBlock event) {
        BlockPos clickedPos = event.getPos();
        Level level = event.getLevel();

        if (level.getBlockState(clickedPos).canBeReplaced()) {
            return clickedPos;
        }

        if (event.getFace() != null) {
            BlockPos adjacentPos = clickedPos.relative(event.getFace());
            if (level.getBlockState(adjacentPos).canBeReplaced()) {
                return adjacentPos;
            }
        }

        return null;
    }
}
