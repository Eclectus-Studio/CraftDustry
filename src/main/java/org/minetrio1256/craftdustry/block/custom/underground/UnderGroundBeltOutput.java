package org.minetrio1256.craftdustry.block.custom.underground;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.minetrio1256.craftdustry.block.entity.custom.underground.UnderGroundBeltInputBlockEntity;
import org.minetrio1256.craftdustry.block.entity.custom.underground.UnderGroundBeltOutputBlockEntity;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;

public class UnderGroundBeltOutput extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<UnderGroundBeltOutput> CODEC = BlockBehaviour.simpleCodec(UnderGroundBeltOutput::new);

    // Removed the speed field here

    public UnderGroundBeltOutput(final Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected RenderShape getRenderShape(final BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return UnderGroundBeltOutput.CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final BlockPos blockPos, final BlockState blockState) {
        // Hardcoded speed value of 15
        return new UnderGroundBeltOutputBlockEntity(blockPos, blockState);
    }

    /* FACING */

    @Override
    protected BlockState rotate(final BlockState pState, final Rotation pRotation) {
        return pState.setValue(UnderGroundBeltOutput.FACING, pRotation.rotate(pState.getValue(UnderGroundBeltOutput.FACING)));
    }

    @Override
    protected BlockState mirror(final BlockState pState, final Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(UnderGroundBeltOutput.FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext pContext) {
        return defaultBlockState().setValue(UnderGroundBeltOutput.FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(UnderGroundBeltOutput.FACING);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(final Level pLevel, final BlockState pState, final BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }
        return BaseEntityBlock.createTickerHelper(pBlockEntityType, modBlockEntities.UNDER_GROUND_OUTPUT_BELT_BE.get(),
                (level, blockPos, blockState, UnderGroundBeltsBlockEntity) -> UnderGroundBeltsBlockEntity.tick(level, blockPos, blockState));
    }

    @Override
    public void stepOn(final Level pLevel, final BlockPos pPos, final BlockState pState, final Entity pEntity) {
        if (pEntity instanceof final ItemEntity itemEntity) {
            // Get the item stack from the entity
            final ItemStack itemStack = itemEntity.getItem();

            // Get the block entity at the current position
            if (pLevel.getBlockEntity(pPos) instanceof final UnderGroundBeltInputBlockEntity UnderGroundBeltsBlockEntity) {
                // Get the belt's item handler
                final ItemStackHandler itemHandler = UnderGroundBeltsBlockEntity.itemHandler;

                // Try to insert the item stack into the belt
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    if (itemHandler.getStackInSlot(i).isEmpty()) {
                        itemHandler.setStackInSlot(i, itemStack.copy()); // Insert the item stack into the belt
                        itemEntity.discard(); // Remove the item entity from the world
                        return; // Exit the loop after inserting the item
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
            if (blockEntity instanceof final UnderGroundBeltInputBlockEntity UnderGroundBeltsBlockEntity) {
                UnderGroundBeltsBlockEntity.drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
    @Override
    public void entityInside(final BlockState pState, final Level pLevel, final BlockPos pPos, final Entity pEntity) {
        if (pEntity instanceof final ItemEntity itemEntity) {
            if (pLevel.getBlockEntity(pPos) instanceof final UnderGroundBeltInputBlockEntity UnderGroundBeltsBlockEntity) {
                final ItemStackHandler itemHandler = UnderGroundBeltsBlockEntity.itemHandler;

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