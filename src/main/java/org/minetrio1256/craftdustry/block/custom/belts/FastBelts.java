package org.minetrio1256.craftdustry.block.custom.belts;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.Nullable;

import org.minetrio1256.craftdustry.block.entity.custom.belts.BeltsBlockEntity;
import org.minetrio1256.craftdustry.block.entity.custom.belts.FastBeltsBlockEntity;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;

import java.util.function.Function;

public class FastBelts extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<FastBelts> CODEC = BlockBehaviour.simpleCodec(FastBelts::new);
    public static final VoxelShape SHAPE = box(0, 0, 0, 16, 2, 16);

    // Removed the speed field here

    public FastBelts(final Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getCollisionShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return FastBelts.SHAPE;  // Return your defined shape or a custom shape
    }

    @Override
    protected VoxelShape getShape(final BlockState pState, final BlockGetter pLevel, final BlockPos pPos, final CollisionContext pContext) {
        return FastBelts.SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(final BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return FastBelts.CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final BlockPos blockPos, final BlockState blockState) {
        // Hardcoded speed value of 15
        return new FastBeltsBlockEntity(blockPos, blockState);
    }

    /* FACING */

    @Override
    protected BlockState rotate(final BlockState pState, final Rotation pRotation) {
        return pState.setValue(FastBelts.FACING, pRotation.rotate(pState.getValue(FastBelts.FACING)));
    }

    @Override
    protected BlockState mirror(final BlockState pState, final Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FastBelts.FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FastBelts.FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FastBelts.FACING);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(final Level pLevel, final BlockState pState, final BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return BaseEntityBlock.createTickerHelper(pBlockEntityType, modBlockEntities.FAST_BELTS_BE.get(),
                (level, blockPos, blockState, beltsBlockEntity) -> beltsBlockEntity.tick(level, blockPos, blockState));
    }

    @Override
    public void stepOn(final Level pLevel, final BlockPos pPos, final BlockState pState, final Entity pEntity) {
        if (pEntity instanceof final ItemEntity itemEntity) {
            // Get the item stack from the entity
            final ItemStack itemStack = itemEntity.getItem();

            // Get the block entity at the current position
            if (pLevel.getBlockEntity(pPos) instanceof final FastBeltsBlockEntity beltsBlockEntity) {
                // Get the belt's item handler
                final ItemStackHandler itemHandler = beltsBlockEntity.itemHandler;

                // Try to insert the item stack into the belt
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    if (itemHandler.getStackInSlot(i).isEmpty()) {
                        itemHandler.setStackInSlot(i, itemStack.copy()); // Insert the item stack into the belt
                        itemEntity.discard(); // Remove the item entity from the world
                        break; // Exit the loop after inserting the item
                    }
                }
            }
        }

        super.stepOn(pLevel, pPos, pState, pEntity);
    }
    @Override
    public void onRemove(final BlockState pState, final Level pLevel, final BlockPos pPos, final BlockState pNewState, final boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            final BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof final FastBeltsBlockEntity beltsBlockEntity) {
                beltsBlockEntity.drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
    @Override
    public void entityInside(final BlockState pState, final Level pLevel, final BlockPos pPos, final Entity pEntity) {
        if (pEntity instanceof final ItemEntity itemEntity) {
            if (pLevel.getBlockEntity(pPos) instanceof final BeltsBlockEntity beltsBlockEntity) {
                final ItemStackHandler itemHandler = beltsBlockEntity.itemHandler;

                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    if (itemHandler.getStackInSlot(i).isEmpty()) {
                        itemHandler.setStackInSlot(i, itemEntity.getItem().copy());
                        itemEntity.discard();
                        return;
                    }
                }
            }
        }
    }
}
