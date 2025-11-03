package com.xiyue.creator.ModFluids;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

public class ModFluids {
    public static FluidResources.FluidStuff PURE_WATER ;

    public static void init(){
        PURE_WATER = FluidResources.register( ()-> FluidResources.addFluid("pure_water",
                new ModBaseFluidType.FluidInfo("pure_water", 0xFF1A75FF, 0, 2.5F,true),
                Block.Properties.of().mapColor(MapColor.WATER).noCollission().replaceable().liquid().pushReaction(PushReaction.DESTROY).noLootTable(),
                ModBaseFluidType::new,
                (supplier, properties) -> new LiquidBlock(supplier.get(),properties),
                prop -> prop.explosionResistance(100F).tickRate(5),
                FluidType.Properties.create()
                        .canExtinguish(true)
                        .supportsBoating(true)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                        .canHydrate(true)
                        .viscosity(1000)
                        .motionScale(0.014D)));
    }
}
