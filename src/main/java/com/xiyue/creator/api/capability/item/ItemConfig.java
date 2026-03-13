package com.xiyue.creator.api.capability.item;

import com.xiyue.creator.api.capability.HandlerContext;
import com.xiyue.creator.api.capability.SlotType;
import net.minecraft.world.item.ItemStack;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

public class ItemConfig {
    private final int slotCount;
    private final Map<HandlerContext, ContextRules> rules;

    private ItemConfig(Builder builder, Map<HandlerContext, ContextRules> rules) {
        slotCount = builder.slotCount;
        this.rules = Map.copyOf(rules);
    }

    public ContextRules forContext(HandlerContext context) {
        return rules.get(context);
    }

    public static Builder builder(int count) {
        return new Builder(count);
    }

    public static class ContextRules {
        private final Map<Integer, SlotType> slotTypes;
        private final BiPredicate<Integer, ItemStack> insertPredicate;
        private final BiPredicate<Integer, ItemStack> extractPredicate;

        private ContextRules(Map<Integer, SlotType> slotTypes, BiPredicate<Integer, ItemStack> insertPredicate, BiPredicate<Integer, ItemStack> extractPredicate) {
            this.slotTypes = Map.copyOf(slotTypes);
            this.insertPredicate = insertPredicate;
            this.extractPredicate = extractPredicate;
        }

        public SlotType getSlotType(int slot) {
            return slotTypes.getOrDefault(slot, SlotType.BOTH);
        }

        public boolean canInsert(int slot, ItemStack stack) {
            return insertPredicate.test(slot, stack);
        }

        public boolean canExtract(int slot, ItemStack stack) {
            return extractPredicate.test(slot, stack);
        }
    }

    public static class Builder {
        private final int slotCount;
        private final Map<HandlerContext, ContextRulesBuilder> contextBuilders = new HashMap<>();


        private Builder(int count) {
            this.slotCount = count;
        }

        public ContextBuilder forContext(HandlerContext context) {
            return new ContextBuilder(this, context);
        }

        private static class ContextRulesBuilder {
            private final Map<Integer, SlotType> slotTypes = new HashMap<>();
            private BiPredicate<Integer, ItemStack> insertPredicate = (slot, stack) -> true;
            private BiPredicate<Integer, ItemStack> extractPredicate = (slot, stack) -> true;

            public void changeSlotType(SlotType oldType, SlotType newType) {
                for (var entry : slotTypes.entrySet()) {
                    if (entry.getValue() == oldType) {
                        slotTypes.put(entry.getKey(), newType);
                    }
                }
            }

            public void setSlotType(int slot, SlotType slotType) {
                slotTypes.put(slot, slotType);
            }

            public void setInsertRule(BiPredicate<Integer, ItemStack> rule) {
                this.insertPredicate = rule;
            }

            public void setExtractRule(BiPredicate<Integer, ItemStack> rule) {
                this.extractPredicate = rule;
            }

            public ContextRules build() {
                return new ContextRules(slotTypes, insertPredicate, extractPredicate);
            }
        }

        public static class ContextBuilder {
            private final Builder parent;
            private final HandlerContext context;
            private final ContextRulesBuilder rulesBuilder = new ContextRulesBuilder();

            private ContextBuilder(Builder parent, HandlerContext context) {
                this.parent = parent;
                this.context = context;
            }

            /**
             * 从已定义的上下文复制规则到当前上下文
             * 这会在当前 builder 中创建一份独立的副本，后续修改不会影响源上下文
             * 如需复制规则到当前上下文，建议最先使用copy()
             */
            public ContextBuilder copy(HandlerContext source) {
                ContextRulesBuilder sourceBuilder = parent.contextBuilders.get(source);
                if (sourceBuilder == null) {
                    throw new IllegalStateException("源上下文 '" + source + "' 尚未定义，请先定义或检查定义顺序");
                }
                this.rulesBuilder.slotTypes.putAll(sourceBuilder.slotTypes);
                this.rulesBuilder.insertPredicate = sourceBuilder.insertPredicate;
                this.rulesBuilder.extractPredicate = sourceBuilder.extractPredicate;
                return this;
            }

            public ContextBuilder changeSlotType(SlotType Ole, SlotType New) {
                rulesBuilder.changeSlotType(Ole, New);
                return this;
            }

            public ContextBuilder setSlotType(int slot, SlotType type) {
                rulesBuilder.setSlotType(slot, type);
                return this;
            }

            public ContextBuilder setSlotType(SlotType type, int... slots) {
                for (int slot : slots) {
                    rulesBuilder.setSlotType(slot, type);
                }
                return this;
            }

            /**
             * 将指定范围内的所有槽位（包含两端）设置为同一类型
             *
             * @param slot1 起始槽索引
             * @param slot2 结束槽索引（包含）
             */
            public ContextBuilder setSlotType(int slot1, int slot2, SlotType type) {
                int min = Math.min(slot1, slot2);
                int max = Math.max(slot1, slot2);
                for (int i = min; i <= max; i++) {
                    rulesBuilder.setSlotType(i, type);
                }
                return this;
            }

            public ContextBuilder setInsertRule(BiPredicate<Integer, ItemStack> rule) {
                rulesBuilder.setInsertRule(rule);
                return this;
            }

            public ContextBuilder setExtractRule(BiPredicate<Integer, ItemStack> rule) {
                rulesBuilder.setExtractRule(rule);
                return this;
            }

            public Builder endContext() {
                parent.contextBuilders.put(context, rulesBuilder);
                return parent;
            }
        }

        public ItemConfig build() {
            if (contextBuilders.isEmpty()) {
                throw new IllegalStateException("至少需要为一个上下文配置规则");
            }
            Map<HandlerContext, ContextRules> builtRules = new EnumMap<>(HandlerContext.class);
            for (var entry : contextBuilders.entrySet()) {
                builtRules.put(entry.getKey(), entry.getValue().build());
            }
            return new ItemConfig(this, builtRules);
        }
    }

    public int getSlotCount() {
        return slotCount;
    }
}
