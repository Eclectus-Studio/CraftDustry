package org.minetrio1256.craftdustry.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.minetrio1256.craftdustry.Main;

public class modItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    public static void register(final IEventBus eventBus) {
        modItems.ITEMS.register(eventBus);
    }
}
