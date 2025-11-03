package com.xiyue.creator.mixin;

import com.xiyue.creator.ModBlocks.ModBlockGroup;
import com.xiyue.creator.tag.BlockTag;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase {
    @Shadow public abstract boolean is(TagKey<Block> tag);

    @Shadow public abstract Block getBlock();

    @Shadow public abstract boolean is(TagKey<Block> tag, Predicate<BlockBehaviour.BlockStateBase> predicate);

    @Shadow public abstract boolean is(ResourceKey<Block> block);

    @Shadow public abstract boolean is(HolderSet<Block> holder);

    @Shadow public abstract boolean is(Holder<Block> block);

    @Inject(method = "requiresCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    public void requiresCorrectToolForDrops(CallbackInfoReturnable<Boolean> cir) {
        if (this.is(BlockTags.LOGS)) {
            cir.setReturnValue(true);
        }
    }
    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    public void getDestroySpeed(CallbackInfoReturnable<Float> cir){
        if (this.is(BlockTag.PLANTS) || this.is(BlockTags.FLOWERS) || this.is(BlockTags.SAPLINGS) || this.is(BlockTags.CORAL_PLANTS) || this.is(BlockTags.CROPS)) {
            cir.setReturnValue(0.6f);
        }else if (this.is(BlockTags.LEAVES)) {
            cir.setReturnValue(0.8f);
        }
    }
}
