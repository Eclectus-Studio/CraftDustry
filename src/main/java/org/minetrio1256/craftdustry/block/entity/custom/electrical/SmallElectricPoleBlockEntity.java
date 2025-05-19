package org.minetrio1256.craftdustry.block.entity.custom.electrical;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;
import org.minetrio1256.craftdustry.world.nbt.ElectricID;

import java.util.*;

public class SmallElectricPoleBlockEntity extends BlockEntity {
    private int networkId = -1;
    private final Set<BlockPos> connectedPoles = new HashSet<>();

    public SmallElectricPoleBlockEntity(BlockPos pos, BlockState state) {
        super(modBlockEntities.SMALL_ELECTRIC_POLE_BE.get(), pos, state);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (tag.contains("NetworkId")) {
            networkId = tag.getInt("NetworkId");
        }

        connectedPoles.clear();
        if (tag.contains("Connections", Tag.TAG_LIST)) {
            ListTag list = tag.getList("Connections", Tag.TAG_COMPOUND);
            for (Tag t : list) {
                CompoundTag entry = (CompoundTag) t;
                BlockPos pos = new BlockPos(
                        entry.getInt("X"),
                        entry.getInt("Y"),
                        entry.getInt("Z")
                );
                connectedPoles.add(pos);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt("NetworkId", networkId);

        ListTag list = new ListTag();
        for (BlockPos pos : connectedPoles) {
            CompoundTag entry = new CompoundTag();
            entry.putInt("X", pos.getX());
            entry.putInt("Y", pos.getY());
            entry.putInt("Z", pos.getZ());
            list.add(entry);
        }
        tag.put("Connections", list);

        super.saveAdditional(tag, provider);
    }

    public void tryConnect(Level level) {
        if (networkId != -1) return; // Already connected

        TreeMap<Integer, SmallElectricPoleBlockEntity> nearbyNetworks = new TreeMap<>();

        BlockPos.betweenClosedStream(worldPosition.offset(-7, -2, -7), worldPosition.offset(7, 2, 7)).forEach(pos -> {
            if (pos.equals(this.worldPosition)) return;

            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof SmallElectricPoleBlockEntity other && other.networkId != -1) {
                nearbyNetworks.put(other.networkId, other);
            }
        });

        if (!nearbyNetworks.isEmpty()) {
            // Connect to the lowest network ID
            Map.Entry<Integer, SmallElectricPoleBlockEntity> entry = nearbyNetworks.firstEntry();
            int chosenId = entry.getKey();
            setNetworkId(chosenId);
            ElectricID.get(level).setDirty();

            // Store all connections
            for (SmallElectricPoleBlockEntity other : nearbyNetworks.values()) {
                if (!connectedPoles.contains(other.getBlockPos())) {
                    connectedPoles.add(other.getBlockPos());
                    other.connectedPoles.add(this.worldPosition);
                    other.setChanged();
                }
            }

        } else {
            // No nearby networks, create new one
            int newId = ElectricID.get(level).next();
            setNetworkId(newId);
        }

        setChanged();
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int id) {
        this.networkId = id;
        setChanged();
    }

    public Set<BlockPos> getConnectedPoles() {
        return connectedPoles;
    }
}
