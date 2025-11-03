package com.xiyue.creator.event.Game.common.Stripping;

import com.xiyue.creator.Creator;
import com.xiyue.creator.Integration.GT.GTceuIntegration.GTRegistryHelper;
import com.xiyue.creator.MyRecipe.ChanceResult;
import com.xiyue.creator.MyRecipe.StrippingRecipe.StrippingInput;
import com.xiyue.creator.MyRecipe.StrippingRecipe.StrippingRecipe;
import com.xiyue.creator.MyRecipe.RegisterRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.*;

@EventBusSubscriber(modid = Creator.MODID)
public class StrippingHandler {
    private static final Random random = new Random();
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        BlockHitResult hitResult = event.getHitVec();

        ItemStack inputItem = event.getItemStack();

        BlockState state = level.getBlockState(pos);
        RecipeManager recipes = level.getRecipeManager();

        //获取输出物品
        StrippingInput input = new StrippingInput(inputItem, state);
        Optional<RecipeHolder<StrippingRecipe>> optional = recipes.getRecipeFor(RegisterRecipe.STRIPPING_TYPE.get(), input, level);

        if (optional.isEmpty()) return;

        List<ChanceResult> results = optional.get().value().results();

        //获取结果方块
        UseOnContext context = new UseOnContext(level, player, event.getHand(), inputItem, hitResult);
        BlockState resultBlock = state.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false);
        if (resultBlock == null) return;

        //替换为去皮方块
        level.setBlock(pos, resultBlock, Block.UPDATE_ALL);
        level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

        if (results != null) drop(level, pos, results, player, inputItem, state, hitResult);

        //工具耐久消耗
        EquipmentSlot slot = event.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
        if (!player.isCreative()) {
            inputItem.hurtAndBreak(1, player, slot);
        }
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
    }


    private static void drop(Level level, BlockPos pos, List<ChanceResult> results, Player player, ItemStack inputItem, BlockState state, BlockHitResult hitResult){
        for (ChanceResult result : results) {
            if (result.stack().isEmpty()) continue;
            if(result.stack().is(GTRegistryHelper.getStickyResin())) {
                if (GTRegistryHelper.isNaturalRubberLog(state)) continue;
            }
            int count = totalCount(inputItem, result);
            ItemStack resultStack = result.stack().copy();
            resultStack.setCount(count);
            BlockPos dropPos = calculateDropPosition(level, pos, player, hitResult);
            ItemEntity barkDrop = new ItemEntity(level, dropPos.getX() + 0.5, dropPos.getY() + 0.5, dropPos.getZ() + 0.5, resultStack);
            level.addFreshEntity(barkDrop);
        }


    }

    private static int totalCount(ItemStack inputItem, ChanceResult result) {
        int minimum_guarantee_count = result.minimum_guarantee_count();
        int FortuneLevel = getFortuneLevel(inputItem);
        for (int i = 0; i < 1 + FortuneLevel; i++) {
            if (random.nextFloat(1) < result.chance()) {
                minimum_guarantee_count++;
            }
        }
        return minimum_guarantee_count;
    }

    private static int getFortuneLevel(ItemStack stack) {
        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null) return 0;
        for (Holder<Enchantment> e : enchantments.keySet()) {
            if (e.is(Enchantments.FORTUNE)) {
                return enchantments.getLevel(e);
            }
        }
        return 0;
    }

    private static BlockPos calculateDropPosition(Level level, BlockPos blockPos, Player player, BlockHitResult hitResult) {
        int X = blockPos.getX();
        int Y = blockPos.getY();
        int Z = blockPos.getZ();

        switch (hitResult.getDirection()) {
            case NORTH -> Z -= 1;
            case SOUTH -> Z += 1;
            case WEST -> X -= 1;
            case EAST -> X += 1;
            case UP -> Y += 1;
            case DOWN -> Y -= 1;
        }
        return new BlockPos(X, Y, Z);
    }
}