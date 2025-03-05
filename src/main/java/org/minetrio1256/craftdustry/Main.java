package org.minetrio1256.craftdustry;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;
import org.minetrio1256.craftdustry.block.modBlocks;
import org.minetrio1256.craftdustry.item.modItems;
import org.minetrio1256.craftdustry.screen.ModMenuTypes;
import org.minetrio1256.craftdustry.screen.chests.iron_chest.IronChestScreen;
import org.minetrio1256.craftdustry.screen.chests.wooden_chest.WoodenChestScreen;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MOD_ID)
public class Main {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "craftdustry";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public Main(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        //register mod stuff
        modBlocks.register(modEventBus);
        modBlockEntities.register(modEventBus);
        modItems.register(modEventBus);
        ModMenuTypes.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            MenuScreens.register(ModMenuTypes.IRON_CHEST_MENU.get(), IronChestScreen::new);
            MenuScreens.register(ModMenuTypes.WOODEN_CHEST_MENU.get(), WoodenChestScreen::new);
        }
    }
}