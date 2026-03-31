package com.xiyue.creator.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;

public class ShapeHelper {
   public enum Context{
        CollisionShape,
        Shape
    }

    private final Map<Context, QuadFunction<BlockState, BlockGetter, BlockPos, CollisionContext, VoxelShape>> rules;

    private ShapeHelper(Map<Context, QuadFunction<BlockState, BlockGetter, BlockPos, CollisionContext, VoxelShape>> rules) {
        this.rules = Map.copyOf(rules);
    }

    public QuadFunction<BlockState, BlockGetter, BlockPos, CollisionContext, VoxelShape> forContext(Context context) {
        return rules.get(context);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<Context, QuadFunction<BlockState, BlockGetter, BlockPos, CollisionContext, VoxelShape>> ShapeRules =new HashMap<>();

        public Builder.ContextBuilder forContext(Context context) {
            return new Builder.ContextBuilder(this, context);
        }

        public ShapeHelper build(){
            return new ShapeHelper(ShapeRules);
        }

        public static class ContextBuilder{
            private final Builder builder;
            private final Context context;
            private QuadFunction<BlockState, BlockGetter, BlockPos, CollisionContext, VoxelShape> ShapeRule;


            public ContextBuilder(Builder builder, Context context) {
                this.builder = builder;
                this.context = context;
            }

            public ContextBuilder setShapeRule(QuadFunction<BlockState, BlockGetter, BlockPos, CollisionContext, VoxelShape> ShapeRule){
               this.ShapeRule = ShapeRule;
               return this;
            }

            /**
             * 从已定义的上下文复制规则到当前上下文
             * 这会在当前 builder 中创建一份独立的副本，后续修改不会影响源上下文
             * 如需复制规则到当前上下文，建议最先使用copy()
             */
            public ContextBuilder copy(Context context) {
                QuadFunction<BlockState, BlockGetter, BlockPos, CollisionContext, VoxelShape> rules = builder.ShapeRules.get(context);
                if (rules == null) {
                    throw new IllegalStateException("源上下文 '" + context + "' 尚未定义，请先定义或检查定义顺序");
                }
                builder.ShapeRules.put(this.context, rules);
                return this;
            }

            public Builder endContext(){
                this.builder.ShapeRules.put(context, ShapeRule);
                return builder;
            }
        }
    }
}
