package com.xiyue.creator.ModItems.FunctionItems;

import com.xiyue.creator.Creator;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.security.DrbgParameters;

public class Meshes extends Item {
    private final int min_workTime;
    private final int max_workTime;
    private final ResourceLocation texture_path;

    public Boolean getCan_used_in_lava() {
        return can_used_in_lava;
    }

    private Boolean can_used_in_lava = false;

    public Meshes(Properties properties, int min_workTime, int max_workTime, Boolean can_used_in_lava, String texture_name) {
        super(properties);
        this.min_workTime = min_workTime;
        this.max_workTime = max_workTime;
        this.texture_path = ResourceLocation.fromNamespaceAndPath(Creator.MODID, "item/" + texture_name);
        this.can_used_in_lava = can_used_in_lava;
    }
    public Meshes(Properties properties, int min_workTime, int max_workTime, String texture_name) {
        super(properties);
        this.min_workTime = min_workTime;
        this.max_workTime = max_workTime;
        this.texture_path = ResourceLocation.fromNamespaceAndPath(Creator.MODID, "item/" + texture_name);
    }

    public int getMin_workTime() {
        return min_workTime;
    }

    public int getMax_workTime() {
        return max_workTime;
    }

    public ResourceLocation getTexture_path(){
        return texture_path;
    }
}
