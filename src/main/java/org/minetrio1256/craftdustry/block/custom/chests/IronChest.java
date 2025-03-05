package org.minetrio1256.craftdustry.block.custom.chests;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.minetrio1256.craftdustry.block.entity.custom.chests.IronChestBlockEntity;

import java.util.function.Function;

public class IronChest extends BaseEntityBlock {
    public static final MapCodec<IronChest> CODEC = BlockBehaviour.simpleCodec(IronChest::new);

    public IronChest(final BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return IronChest.CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final BlockPos blockPos, final BlockState blockState) {
        return new IronChestBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(final BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(final BlockState pState, final Level pLevel, final BlockPos pPos, final BlockState pNewState, final boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            final BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof final IronChestBlockEntity ironChestBlockEntity) {
                ironChestBlockEntity.drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    protected ItemInteractionResult useItemOn(final ItemStack pStack, final BlockState pState, final Level pLevel, final BlockPos pPos,
                                              final Player pPlayer, final InteractionHand pHand, final BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof final IronChestBlockEntity ironChestBlockEntity) {
            if (pPlayer instanceof final ServerPlayer serverPlayer) { // Check before casting
                serverPlayer.openMenu(new SimpleMenuProvider(ironChestBlockEntity, Component.translatable("name.craftdustry.wooden_chest")), pPos);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.SUCCESS;
    }
}
