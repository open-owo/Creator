package com.xiyue.creator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.xiyue.creator.api.util.BidirectionalMultiMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MultiblockMapData extends SavedData {
    private final BidirectionalMultiMap<BlockPos, BlockPos> MultiblockData = new BidirectionalMultiMap<>();

    public static final Codec<MultiblockMapData> CODEC = RecordCodecBuilder.create(inst ->inst.group(
            Codec.unboundedMap(BlockPos.CODEC, BlockPos.CODEC.listOf()).fieldOf("multiblock_map_data").forGetter(data -> data.transformedIntoList(data.MultiblockData.getKeyToValues()))
    ).apply(inst, MultiblockMapData::new));

    public MultiblockMapData() {
    }

    public MultiblockMapData(Map<BlockPos, List<BlockPos>> data) {
        for (var x : data.entrySet()){
            for (var y : x.getValue()){
                MultiblockData.put(x.getKey(), y);
            }
        }
    }


    private <T> Map<BlockPos, List<T>> transformedIntoList(Map<BlockPos, Set<T>> set){
        Map<BlockPos, List<T>> map = new HashMap<>();
        for (var entry : set.entrySet()){
            map.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return map;
    }

    public void bind(BlockPos master, BlockPos slave){
        MultiblockData.put(slave, master);
        setDirty();
    }

    public void unbind(BlockPos master, BlockPos slave){
        MultiblockData.remove(slave, master);
        setDirty();
    }

    public Set<BlockPos> getMater(BlockPos slave){
        return MultiblockData.getValues(slave);
    }

    public Set<BlockPos> getSlave(BlockPos master){
        return MultiblockData.getValues(master);
    }

    public boolean checkAndLink(BlockPos other, BlockPos me){
        if (MultiblockData.getValues(me).isEmpty() || MultiblockData.getValues(other).isEmpty()) return false;
        return compare(other, me);
    }

    private boolean compare(BlockPos other, BlockPos me){
        Set<BlockPos> set1 = MultiblockData.getValues(other);
        Set<BlockPos> set2 = MultiblockData.getValues(me);
        if (!set1.equals(set2)) {
            MultiblockData.mergeKeys(other, me);
            return false;
        }
        return true;
    }

    //数据持久化
    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
        CODEC.encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), this)
                .result()
                .ifPresent(result -> {
                    if (result instanceof CompoundTag compound) {
                        tag.merge(compound);
                    }
                });
        return tag;
    }

    // 加载方法
    public static MultiblockMapData load(CompoundTag tag, HolderLookup.Provider provider) {
        return CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), tag)
                .result()
                .orElse(new MultiblockMapData());
    }

    // 定义工厂方法
    public static SavedData.Factory<MultiblockMapData> factory() {
        return new SavedData.Factory<>(
                MultiblockMapData::new,
                MultiblockMapData::load
        );
    }

    //获取空岛保存数据
    public static MultiblockMapData get(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(MultiblockMapData.factory(), "multiblock_data");
    }
}
