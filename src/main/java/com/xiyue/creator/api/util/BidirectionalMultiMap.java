package com.xiyue.creator.api.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.*;

/**
 * 双向多对多映射数据结构，支持键到值集合、值到键集合的双向查询。
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public class BidirectionalMultiMap<K, V> {
    // 键 -> 值集合
    private final Map<K, Set<V>> keyToValues = new Object2ObjectOpenHashMap<>();
    // 值 -> 键集合
    private final Map<V, Set<K>> valueToKeys = new Object2ObjectOpenHashMap<>();

    /**
     * 添加一个键值关联。若关联已存在则不做任何操作。
     *
     * @param key   键
     * @param value 值
     */
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("键和值均不能为null");
        }

        // 更新 keyToValues
        Set<V> values = keyToValues.computeIfAbsent(key, k -> new ObjectOpenHashSet<>());
        if (values.add(value)) {
            // 只有新添加时才更新反向映射
            Set<K> keys = valueToKeys.computeIfAbsent(value, v -> new ObjectOpenHashSet<>());
            keys.add(key);
        }
    }

    /**
     * 删除一个键值关联。若关联不存在则不做任何操作。
     *
     * @param key   键
     * @param value 值
     */
    public void remove(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("键和值均不能为null");
        }

        // 从 keyToValues 中移除
        Set<V> values = keyToValues.get(key);
        if (values != null && values.remove(value)) {
            // 如果该键对应的值集合为空，则移除该键条目
            if (values.isEmpty()) {
                keyToValues.remove(key);
            }

            // 从 valueToKeys 中移除
            Set<K> keys = valueToKeys.get(value);
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
    public void removeKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("键不能为null");
        }

        Set<V> values = keyToValues.remove(key);
        if (values != null) {
            for (V value : values) {
                Set<K> keys = valueToKeys.get(value);
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
    public void removeValue(V value) {
        if (value == null) {
            throw new IllegalArgumentException("值不能为null");
        }

        Set<K> keys = valueToKeys.remove(value);
        if (keys != null) {
            for (K key : keys) {
                Set<V> values = keyToValues.get(key);
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
    public Set<V> getValues(K key) {
        if (key == null) {
            throw new IllegalArgumentException("键不能为null");
        }
        Set<V> values = keyToValues.get(key);
        return values == null ? Collections.emptySet() : Collections.unmodifiableSet(values);
    }

    /**
     * 获取指定值关联的所有键（只读视图）。
     *
     * @param value 值
     * @return 键集合（不可变，若值不存在则返回空集）
     */
    public Set<K> getKeys(V value) {
        if (value == null) {
            throw new IllegalArgumentException("值不能为null");
        }
        Set<K> keys = valueToKeys.get(value);
        return keys == null ? Collections.emptySet() : Collections.unmodifiableSet(keys);
    }

    /**
     * 判断是否存在指定键值关联。
     *
     * @param key   键
     * @param value 值
     * @return 存在返回true，否则false
     */
    public boolean contains(K key, V value) {
        if (key == null || value == null) {
            return false;
        }
        Set<V> values = keyToValues.get(key);
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
    public void mergeKeys(K key1, K key2) {
        if (key1 == null || key2 == null) {
            throw new IllegalArgumentException("键不能为null");
        }
        if (key1.equals(key2)) {
            return; // 相同键无需操作
        }

        // 获取当前值集合（如果键不存在则视为空集）
        Set<V> values1 = keyToValues.getOrDefault(key1, Collections.emptySet());
        Set<V> values2 = keyToValues.getOrDefault(key2, Collections.emptySet());

        // 计算并集
        Set<V> merged = new ObjectOpenHashSet<>(values1);
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
        for (V value : merged) {
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
     * @return 键集合
     */
    public Set<K> keys() {
        return Collections.unmodifiableSet(keyToValues.keySet());
    }

    /**
     * 获取所有值（只读视图）。
     *
     * @return 值集合
     */
    public Set<V> values() {
        return Collections.unmodifiableSet(valueToKeys.keySet());
    }

    public Map<K, Set<V>> getKeyToValues() {
        return keyToValues;
    }

    public Map<V, Set<K>> getValueToKeys() {
        return valueToKeys;
    }

    /**
     * 返回映射的字符串表示。
     */
    @Override
    public String toString() {
        return "BidirectionalMultiMap{" +
                "keyToValues=" + keyToValues +
                ", valueToKeys=" + valueToKeys +
                '}';
    }
}

