package org.minetrio1256.craftdustry.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import org.minetrio1256.craftdustry.Main;
import org.minetrio1256.craftdustry.block.custom.belts.Belts;
import org.minetrio1256.craftdustry.block.custom.belts.ExpressBelts;
import org.minetrio1256.craftdustry.block.custom.belts.FastBelts;
import org.minetrio1256.craftdustry.item.modItems;

public class modBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    public static final RegistryObject<Block> BELTS = registerBlock("belts",
            () -> new Belts(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().randomTicks()));
    public static final RegistryObject<Block> FAST_BELTS = registerBlock("fast_belts",
            () -> new FastBelts(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().randomTicks()));
    public static final RegistryObject<Block> EXPRESS_BELTS = registerBlock("express_belts",
            () -> new ExpressBelts(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops().randomTicks()));


    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        modItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
