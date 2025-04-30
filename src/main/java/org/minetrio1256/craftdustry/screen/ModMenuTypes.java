package org.minetrio1256.craftdustry.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.minetrio1256.craftdustry.Craftdustry;
import org.minetrio1256.craftdustry.screen.chests.iron_chest.IronChestMenu;
import org.minetrio1256.craftdustry.screen.chests.wooden_chest.WoodenChestMenu;


public enum ModMenuTypes {
    ;
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Craftdustry.MOD_ID);

    public static final RegistryObject<MenuType<WoodenChestMenu>> WOODEN_CHEST_MENU =
            ModMenuTypes.registerMenuType("wooden_chest_menu", WoodenChestMenu::new);
    public static final RegistryObject<MenuType<IronChestMenu>> IRON_CHEST_MENU =
            ModMenuTypes.registerMenuType("iron_chest_menu", IronChestMenu::new);

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(final String name,
                                                                                                 final IContainerFactory<T> factory) {
        return ModMenuTypes.MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(final IEventBus eventBus) {
        ModMenuTypes.MENUS.register(eventBus);
    }
}
