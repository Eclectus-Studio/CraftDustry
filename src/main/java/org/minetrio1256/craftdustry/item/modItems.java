package org.minetrio1256.craftdustry.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.minetrio1256.craftdustry.Craftdustry;
import org.minetrio1256.craftdustry.item.custom.*;

public class modItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Craftdustry.MOD_ID);

    public static final RegistryObject<Item> COAL = ITEMS.register("coal",
            () -> new Coal(new Item.Properties()));

    public static final RegistryObject<Item> WOOD = ITEMS.register("wood",
            () -> new Wood(new Item.Properties()));

    public static final RegistryObject<Item> SOLID_FUEL = ITEMS.register("solid_fuel",
            () -> new SolidFuel(new Item.Properties()));

    public static final RegistryObject<Item> ROCKET_FUEL = ITEMS.register("rocket_fuel",
            () -> new RocketFuel(new Item.Properties()));

    public static final RegistryObject<Item> NUCLEAR_FUEL = ITEMS.register("nuclear_fuel",
            () -> new NuclearFuel(new Item.Properties()));

    public static void register(final IEventBus eventBus) {
        modItems.ITEMS.register(eventBus);
    }
}
