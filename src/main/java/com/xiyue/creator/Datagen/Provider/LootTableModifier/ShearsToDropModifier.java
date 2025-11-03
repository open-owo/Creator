package com.xiyue.creator.Datagen.Provider.LootTableModifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class ShearsToDropModifier extends LootModifier {

    private final float dropChance;
    private final ItemStack itemStack;

    public static final MapCodec<ShearsToDropModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(inst.group(
                    Codec.FLOAT.fieldOf("drop_chance").forGetter(e -> e.dropChance),
                    ItemStack.CODEC.fieldOf("drop_item").forGetter(e -> e.itemStack)
                    )
            ).apply(inst, ShearsToDropModifier::new)
    );

    public ShearsToDropModifier(LootItemCondition[] conditions, float dropChance, ItemStack itemStack) {
        super(conditions);
        this.dropChance = dropChance;
        this.itemStack = itemStack;

    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        if (tool != null && tool.is(Items.SHEARS)) {
            return generatedLoot;
        }

        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        if (state == null) {
            return generatedLoot;
        }

        if (state.getBlock() instanceof DoublePlantBlock) {
            if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
                return generatedLoot;
            }
        }

        if (context.getRandom().nextFloat() < dropChance) {
            generatedLoot.add(this.itemStack.copy());
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
