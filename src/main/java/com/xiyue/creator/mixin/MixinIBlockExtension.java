package com.xiyue.creator.mixin;

import com.xiyue.creator.tag.ItemTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IBlockExtension.class)
public interface MixinIBlockExtension {
    @Intrinsic
    @Redirect(
            method = "getToolModifiedState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;canPerformAction(Lnet/neoforged/neoforge/common/ItemAbility;)Z"
            ),
            require = 1,
            remap = false
    )
    default boolean redirectCanPerformAction(ItemStack stack, ItemAbility ability) {
        return stack.canPerformAction(ability) || stack.is(ItemTag.KNIFE);
    }
}
