package com.xiyue.creator.event.common.Soaking;

import com.xiyue.creator.Creator;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import com.xiyue.creator.MyRecipe.SoakingRecipe.SoakingInput;
import com.xiyue.creator.MyRecipe.SoakingRecipe.SoakingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import java.util.Optional;

@EventBusSubscriber(modid = Creator.MODID)
public class SoakingHandler {
//    @SubscribeEvent
//    public static void onRightClickBlock(UseItemOnBlockEvent event) {
//        Level level = event.getLevel();
//        Player player = event.getPlayer();
//        ItemStack inputItem = event.getItemStack();
//        BlockPos pos = event.getPos();
//        Direction direction = event.getFace();
//        if (direction != null && player != null) {
//            System.out.println("pos:" + pos);
//
//            BlockPos pos1 = pos.relative(direction);
//            System.out.println("pos1:" + pos1);
//            BlockState state = level.getBlockState(pos1);
//
//            RecipeManager recipes = level.getRecipeManager();
//
//            SoakingInput input = new SoakingInput(inputItem, state);
//            Optional<RecipeHolder<SoakingRecipe>> optional = recipes.getRecipeFor(RegisterRecipe.SOAKING_TYPE.get(), input, level);
//            ItemStack result = optional
//                    .map(RecipeHolder::value)
//                    .map(e -> e.assemble(input, level.registryAccess()))
//                    .orElse(ItemStack.EMPTY);
//            System.out.println("res:" + result);
//            if (!result.isEmpty()) {
//                System.out.println("11111");
//                event.getItemStack().shrink(1);
//                player.getInventory().add(result);
//                event.cancelWithResult(ItemInteractionResult.sidedSuccess(level.isClientSide));
//            }
//        }
//    }
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickItem event) {
        Level level = event.getLevel();
        Player player = event.getEntity();
        ItemStack inputItem = event.getItemStack();

        if (level.isClientSide) return;

        BlockHitResult fluidHit = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (fluidHit.getType() == HitResult.Type.BLOCK) {
            BlockPos fluidPos = fluidHit.getBlockPos();
            BlockState fluidState = level.getBlockState(fluidPos);

            RecipeManager recipes = level.getRecipeManager();
            SoakingInput input = new SoakingInput(inputItem, fluidState);

            Optional<RecipeHolder<SoakingRecipe>> optional = recipes.getRecipeFor(RegisterRecipe.SOAKING_TYPE.get(), input, level);
            ItemStack result = optional
                    .map(RecipeHolder::value)
                    .map(e -> e.assemble(input, level.registryAccess()))
                    .orElse(ItemStack.EMPTY);

            if (!result.isEmpty()) {
                if (!player.hasInfiniteMaterials()) inputItem.shrink(1);
                if (!player.getInventory().add(result)) {
                    player.drop(result, false);
                }
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }
}
