package org.minetrio1256.craftdustry.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.minetrio1256.craftdustry.Main;
import org.minetrio1256.craftdustry.block.entity.custom.belts.BeltsBlockEntity;
import org.minetrio1256.craftdustry.block.entity.custom.belts.ExpressBeltsBlockEntity;
import org.minetrio1256.craftdustry.block.entity.custom.belts.FastBeltsBlockEntity;
import org.minetrio1256.craftdustry.block.entity.custom.belts.TurboBeltsBlockEntity;
import org.minetrio1256.craftdustry.block.entity.custom.chests.IronChestBlockEntity;
import org.minetrio1256.craftdustry.block.entity.custom.chests.WoodenChestBE;
import org.minetrio1256.craftdustry.block.entity.custom.underground.UnderGroundBeltInputBlockEntity;
import org.minetrio1256.craftdustry.block.entity.custom.underground.UnderGroundBeltOutputBlockEntity;
import org.minetrio1256.craftdustry.block.modBlocks;

public class modBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID);

    // Register the BlockEntityType for the Belts block
    public static final RegistryObject<BlockEntityType<BeltsBlockEntity>> BELTS_BE =
            modBlockEntities.BLOCK_ENTITIES.register("belts", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new BeltsBlockEntity(pos, state), modBlocks.BELTS.get()).build(null));
    public static final RegistryObject<BlockEntityType<FastBeltsBlockEntity>> FAST_BELTS_BE =
            modBlockEntities.BLOCK_ENTITIES.register("fast_belts", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new FastBeltsBlockEntity(pos, state), modBlocks.FAST_BELTS.get()).build(null));
    public static final RegistryObject<BlockEntityType<ExpressBeltsBlockEntity>> EXPRESS_BELTS_BE =
            modBlockEntities.BLOCK_ENTITIES.register("express_belts", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new ExpressBeltsBlockEntity(pos, state), modBlocks.EXPRESS_BELTS.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurboBeltsBlockEntity>> TURBO_BELTS_BE =
            modBlockEntities.BLOCK_ENTITIES.register("turbo_belts", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new TurboBeltsBlockEntity(pos, state), modBlocks.TURBO_BELTS.get()).build(null));

    public static final RegistryObject<BlockEntityType<WoodenChestBE>> WOODEN_CHEST_BE =
            modBlockEntities.BLOCK_ENTITIES.register("wooden_chest", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new WoodenChestBE(pos, state), modBlocks.WOODEN_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<IronChestBlockEntity>> IRON_CHEST_BE =
            modBlockEntities.BLOCK_ENTITIES.register("iron_chest", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new IronChestBlockEntity(pos, state), modBlocks.IRON_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<UnderGroundBeltInputBlockEntity>> UNDER_GROUND_INPUT_BELT_BE =
            modBlockEntities.BLOCK_ENTITIES.register("under_ground_input_belt", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new UnderGroundBeltInputBlockEntity(pos, state), modBlocks.UNDER_GROUND_BELT_INPUT.get()).build(null));
    public static final RegistryObject<BlockEntityType<UnderGroundBeltOutputBlockEntity>> UNDER_GROUND_OUTPUT_BELT_BE =
            modBlockEntities.BLOCK_ENTITIES.register("under_ground_output_belt", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new UnderGroundBeltOutputBlockEntity(pos, state), modBlocks.UNDER_GROUND_BELT_OUTPUT.get()).build(null));

    public static void register(final IEventBus eventBus) {
        modBlockEntities.BLOCK_ENTITIES.register(eventBus);
    }
}