package org.minetrio1256.craftdustry.world.nbt;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ElectricID extends SavedData {

    private int nextId = 0;

    // Required by SavedData
    public ElectricID() {}

    public static ElectricID get(Level level) {
        if (!(level instanceof ServerLevel serverLevel)) {
            throw new IllegalStateException("ElectricID can only be accessed on the server!");
        }

        return serverLevel.getDataStorage().computeIfAbsent(FACTORY, "electric_id");
    }

    public int next() {
        setDirty();
        return nextId++;
    }

    // Serialization
    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt("NextId", nextId);
        return tag;
    }

    // Deserialization
    public static ElectricID load(CompoundTag tag, HolderLookup.Provider provider) {
        ElectricID data = new ElectricID();
        data.nextId = tag.getInt("NextId");
        return data;
    }

    // Factory required for 1.20.x SavedData system
    private static final SavedData.Factory<ElectricID> FACTORY = new SavedData.Factory<>(
            new Supplier<>() {
                @Override
                public ElectricID get() {
                    return new ElectricID();
                }
            },
            new BiFunction<>() {
                @Override
                public ElectricID apply(CompoundTag tag, HolderLookup.Provider provider) {
                    return ElectricID.load(tag, provider);
                }
            },
            DataFixTypes.LEVEL
    );
}
