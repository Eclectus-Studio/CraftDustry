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

import org.minetrio1256.craftdustry.Craftdustry;
import org.minetrio1256.craftdustry.block.custom.belts.Belts;
import org.minetrio1256.craftdustry.block.custom.belts.ExpressBelts;
import org.minetrio1256.craftdustry.block.custom.belts.FastBelts;
import org.minetrio1256.craftdustry.block.custom.belts.TurboBelts;
import org.minetrio1256.craftdustry.block.custom.chests.IronChest;
import org.minetrio1256.craftdustry.block.custom.chests.WoodenChest;
import org.minetrio1256.craftdustry.block.custom.electrical.SmallElectricPole;
import org.minetrio1256.craftdustry.block.custom.underground.UnderGroundBeltInput;
import org.minetrio1256.craftdustry.block.custom.underground.UnderGroundBeltOutput;
import org.minetrio1256.craftdustry.item.modItems;

public class modBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Craftdustry.MOD_ID);

    public static final RegistryObject<Block> BELTS = modBlocks.registerBlock("belts",
            () -> new Belts(BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops().randomTicks()));
    public static final RegistryObject<Block> FAST_BELTS = modBlocks.registerBlock("fast_belts",
            () -> new FastBelts(BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops().randomTicks()));
    public static final RegistryObject<Block> EXPRESS_BELTS = modBlocks.registerBlock("express_belts",
            () -> new ExpressBelts(BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops().randomTicks()));
    public static final RegistryObject<Block> TURBO_BELTS = modBlocks.registerBlock("turbo_belts",
            () -> new TurboBelts(BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops().randomTicks()));

    public static final RegistryObject<Block> WOODEN_CHEST = modBlocks.registerBlock("wooden_chest",
            () -> new WoodenChest(BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> IRON_CHEST = modBlocks.registerBlock("iron_chest",
            () -> new IronChest(BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> UNDER_GROUND_BELT_INPUT = modBlocks.registerBlock("under_ground_input_belt",
            () -> new UnderGroundBeltInput(BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops().randomTicks()));
    public static final RegistryObject<Block> UNDER_GROUND_BELT_OUTPUT = modBlocks.registerBlock("under_ground_output_belt",
            () -> new UnderGroundBeltOutput(BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops().randomTicks()));
    public static final RegistryObject<Block> SMALL_ELECTRIC_POLE = modBlocks.registerBlock("small_electric_pole",
            () -> new SmallElectricPole(BlockBehaviour.Properties.of().strength(2.0f).requiresCorrectToolForDrops().randomTicks()));

    private static <T extends Block>RegistryObject<T> registerBlock(final String name, final Supplier<T> block) {
        final RegistryObject<T> toReturn = modBlocks.BLOCKS.register(name, block);
        modBlocks.registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(final String name, final RegistryObject<T> block) {
        modItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(final IEventBus eventBus) {
        modBlocks.BLOCKS.register(eventBus);
    }
}
