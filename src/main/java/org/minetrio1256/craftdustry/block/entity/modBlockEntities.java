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
import org.minetrio1256.craftdustry.block.entity.custom.chests.WoodenChestBE;
import org.minetrio1256.craftdustry.block.modBlocks;

public class modBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID);

    // Register the BlockEntityType for the Belts block
    public static final RegistryObject<BlockEntityType<BeltsBlockEntity>> BELTS_BE =
            BLOCK_ENTITIES.register("belts", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new BeltsBlockEntity(pos, state), modBlocks.BELTS.get()).build(null));
    public static final RegistryObject<BlockEntityType<FastBeltsBlockEntity>> FAST_BELTS_BE =
            BLOCK_ENTITIES.register("fast_belts", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new FastBeltsBlockEntity(pos, state), modBlocks.FAST_BELTS.get()).build(null));
    public static final RegistryObject<BlockEntityType<ExpressBeltsBlockEntity>> EXPRESS_BELTS_BE =
            BLOCK_ENTITIES.register("express_belts", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new ExpressBeltsBlockEntity(pos, state), modBlocks.EXPRESS_BELTS.get()).build(null));
    public static final RegistryObject<BlockEntityType<WoodenChestBE>> WOODEN_CHEST_BE =
            BLOCK_ENTITIES.register("wooden_chest", () -> BlockEntityType.Builder.of(
                    (pos, state) -> new WoodenChestBE(pos, state), modBlocks.WOODEN_CHEST.get()).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}