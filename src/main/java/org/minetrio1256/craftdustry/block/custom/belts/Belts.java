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
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;

import java.util.function.Function;

public class Belts extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final MapCodec<Belts> CODEC = simpleCodec((Function<Properties, Belts>) Belts::new);
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 2, 16);

    // Removed the speed field here

    public Belts(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        // Hardcoded speed value of 15
        return new BeltsBlockEntity(blockPos, blockState);
    }

    /* FACING */

    @Override
    protected BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, modBlockEntities.BELTS_BE.get(),
                (level, blockPos, blockState, beltsBlockEntity) -> beltsBlockEntity.tick(level, blockPos, blockState));
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (pEntity instanceof ItemEntity itemEntity) {
            // Get the item stack from the entity
            ItemStack itemStack = itemEntity.getItem();

            // Get the block entity at the current position
            if (pLevel.getBlockEntity(pPos) instanceof BeltsBlockEntity beltsBlockEntity) {
                // Get the belt's item handler
                ItemStackHandler itemHandler = beltsBlockEntity.itemHandler;

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
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof BeltsBlockEntity beltsBlockEntity) {
                beltsBlockEntity.drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}
