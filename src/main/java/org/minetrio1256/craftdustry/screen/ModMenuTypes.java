package org.minetrio1256.craftdustry.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.minetrio1256.craftdustry.Main;
import org.minetrio1256.craftdustry.screen.chests.iron_chest.IronChestMenu;
import org.minetrio1256.craftdustry.screen.chests.wooden_chest.WoodenChestMenu;


public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MOD_ID);

    public static final RegistryObject<MenuType<WoodenChestMenu>> WOODEN_CHEST_MENU =
            registerMenuType("wooden_chest_menu", WoodenChestMenu::new);
    public static final RegistryObject<MenuType<IronChestMenu>> IRON_CHEST_MENU =
            registerMenuType("iron_chest_menu", IronChestMenu::new);

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name,
                                                                                                 IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
