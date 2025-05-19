package org.minetrio1256.craftdustry.block.custom.electrical;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.minetrio1256.craftdustry.block.entity.custom.electrical.SmallElectricPoleBlockEntity;

public class SmallElectricPole extends BaseEntityBlock {

    public SmallElectricPole(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof SmallElectricPoleBlockEntity poleEntity) {
                poleEntity.tryConnect(level);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SmallElectricPoleBlockEntity(blockPos, blockState);
    }
}
