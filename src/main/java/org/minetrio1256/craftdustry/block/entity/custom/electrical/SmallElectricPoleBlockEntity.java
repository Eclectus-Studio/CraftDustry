package org.minetrio1256.craftdustry.block.entity.custom.electrical;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;
import org.minetrio1256.craftdustry.world.nbt.ElectricID;

import java.util.HashSet;

public class SmallElectricPoleBlockEntity extends BlockEntity {
    private int networkId = -1;

    public SmallElectricPoleBlockEntity(BlockPos pos, BlockState state) {
        super(modBlockEntities.SMALL_ELECTRIC_POLE_BE.get(), pos, state);
    }

    @Override
    protected void loadAdditional(final CompoundTag pTag, final HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        if (pTag.contains("NetworkId")) {
            networkId = pTag.getInt("NetworkId");
        }
    }

    @Override
    protected void saveAdditional(final CompoundTag pTag, final HolderLookup.Provider pRegistries) {
        pTag.putInt("NetworkId", networkId);
        super.saveAdditional(pTag, pRegistries);
    }

    public void tryConnect(Level level) {
        if (networkId != -1) return;

        BlockPos.betweenClosedStream(worldPosition.offset(-7, -2, -7), worldPosition.offset(7, 2, 7)).forEach(pos -> {
            if (level.getBlockEntity(pos) instanceof SmallElectricPoleBlockEntity other) {
                if (other.networkId != -1) {
                }
            }
        });
    }

    public int getNetworkId() {
        return networkId;
    }
}
