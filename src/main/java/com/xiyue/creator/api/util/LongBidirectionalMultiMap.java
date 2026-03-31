package com.xiyue.creator.api.util;

import it.unimi.dsi.fastutil.longs.*;

/**
 * 专门处理 long 类型键和值的双向多对多映射，内部使用 fastutil 原始类型集合，性能高效。
 * 支持键到值集合、值到键集合的双向查询。
 */
public class LongBidirectionalMultiMap {

    // 键 -> 值集合 (原始 long 值集合)
    private final Long2ObjectOpenHashMap<LongSet> keyToValues = new Long2ObjectOpenHashMap<>();
    // 值 -> 键集合 (原始 long 键集合)
    private final Long2ObjectOpenHashMap<LongSet> valueToKeys = new Long2ObjectOpenHashMap<>();

    /**
     * 添加一个键值关联。若关联已存在则不做任何操作。
     *
     * @param key   键
     * @param value 值
     */
    public void put(long key, long value) {
        // 更新 keyToValues
        LongSet values = keyToValues.computeIfAbsent(key, k -> new LongOpenHashSet());
        if (values.add(value)) {
            // 只有新添加时才更新反向映射
            LongSet keys = valueToKeys.computeIfAbsent(value, v -> new LongOpenHashSet());
            keys.add(key);
        }
    }

    /**
     * 删除一个键值关联。若关联不存在则不做任何操作。
     *
     * @param key   键
     * @param value 值
     */
    public void remove(long key, long value) {
        // 从 keyToValues 中移除
        LongSet values = keyToValues.get(key);
        if (values != null && values.remove(value)) {
            // 如果该键对应的值集合为空，则移除该键条目
            if (values.isEmpty()) {
                keyToValues.remove(key);
            }

            // 从 valueToKeys 中移除
            LongSet keys = valueToKeys.get(value);
            if (keys != null) {
                keys.remove(key);
                if (keys.isEmpty()) {
                    valueToKeys.remove(value);
                }
            }
        }
    }

    /**
     * 删除指定键的所有关联。
     *
     * @param key 键
     */
    public void removeKey(long key) {
        LongSet values = keyToValues.remove(key);
        if (values != null) {
            LongIterator valueIterator = values.iterator();
            while (valueIterator.hasNext()) {
                long value = valueIterator.nextLong();
                LongSet keys = valueToKeys.get(value);
                if (keys != null) {
                    keys.remove(key);
                    if (keys.isEmpty()) {
                        valueToKeys.remove(value);
                    }
                }
            }
        }
    }

    /**
     * 删除指定值的所有关联。
     *
     * @param value 值
     */
    public void removeValue(long value) {
        LongSet keys = valueToKeys.remove(value);
        if (keys != null) {
            LongIterator keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                long key = keyIterator.nextLong();
                LongSet values = keyToValues.get(key);
                if (values != null) {
                    values.remove(value);
                    if (values.isEmpty()) {
                        keyToValues.remove(key);
                    }
                }
            }
        }
    }

    /**
     * 获取指定键关联的所有值（只读视图）。
     *
     * @param key 键
     * @return 值集合（不可变，若键不存在则返回空集）
     */
    public LongSet getValues(long key) {
        LongSet values = keyToValues.get(key);
        if (values == null) {
            return LongSet.of();
        }
        // 返回不可修改的视图，防止外部修改内部结构
        return LongSets.unmodifiable(values);
    }

    /**
     * 获取指定值关联的所有键（只读视图）。
     *
     * @param value 值
     * @return 键集合（不可变，若值不存在则返回空集）
     */
    public LongSet getKeys(long value) {
        LongSet keys = valueToKeys.get(value);
        if (keys == null) {
            return LongSet.of();
        }
        return LongSets.unmodifiable(keys);
    }

    /**
     * 判断是否存在指定键值关联。
     *
     * @param key   键
     * @param value 值
     * @return 存在返回 true，否则 false
     */
    public boolean contains(long key, long value) {
        LongSet values = keyToValues.get(key);
        return values != null && values.contains(value);
    }

    /**
     * 合并两个键的关联集合，使两个键都拥有两个集合的并集。
     * <p>
     * 若两个键相同，则不做任何操作。若某个键原本不存在，则视为空集。
     * 合并后，两个键原有的关联被替换为合并后的集合。
     *
     * @param key1 第一个键
     * @param key2 第二个键
     */
    public void mergeKeys(long key1, long key2) {
        if (key1 == key2) {
            return; // 相同键无需操作
        }

        // 获取当前值集合（如果键不存在则视为空集）
        LongSet values1 = keyToValues.getOrDefault(key1, new LongOpenHashSet());
        LongSet values2 = keyToValues.getOrDefault(key2, new LongOpenHashSet());

        // 计算并集
        LongSet merged = new LongOpenHashSet(values1);
        merged.addAll(values2);

        if (merged.isEmpty()) {
            // 如果并集为空，则确保两个键都没有关联
            removeKey(key1);
            removeKey(key2);
            return;
        }

        // 先移除两个键的所有关联（避免重复处理反向映射）
        removeKey(key1);
        removeKey(key2);

        // 为两个键添加合并后的值集合
        LongIterator iterator = merged.iterator();
        while (iterator.hasNext()) {
            long value = iterator.nextLong();
            put(key1, value);
            put(key2, value);
        }
    }

    /**
     * 清空所有映射。
     */
    public void clear() {
        keyToValues.clear();
        valueToKeys.clear();
    }

    /**
     * 获取所有键（只读视图）。
     *
     * @return 键集合（不可变）
     */
    public LongSet keys() {
        return LongSets.unmodifiable(keyToValues.keySet());
    }

    /**
     * 获取所有值（只读视图）。
     *
     * @return 值集合（不可变）
     */
    public LongSet values() {
        return LongSets.unmodifiable(valueToKeys.keySet());
    }

    /**
     * 返回映射的字符串表示。
     */
    @Override
    public String toString() {
        return "LongBidirectionalMultiMap{" + "keyToValues=" + keyToValues +
                ", valueToKeys=" + valueToKeys +
                '}';
    }
}
