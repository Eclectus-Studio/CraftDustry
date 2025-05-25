package org.minetrio1256.craftdustry;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.minetrio1256.craftdustry.api.electric.ElectricalNetworkSerializer;
import org.minetrio1256.craftdustry.block.entity.modBlockEntities;
import org.minetrio1256.craftdustry.block.modBlocks;
import org.minetrio1256.craftdustry.command.GetCapesCommand;
import org.minetrio1256.craftdustry.data.cape.Capes;
import org.minetrio1256.craftdustry.item.modItems;
import org.minetrio1256.craftdustry.screen.ModMenuTypes;
import org.minetrio1256.craftdustry.screen.chests.iron_chest.IronChestScreen;
import org.minetrio1256.craftdustry.screen.chests.wooden_chest.WoodenChestScreen;
import org.slf4j.Logger;

import java.nio.file.Path;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Craftdustry.MOD_ID)
public class Craftdustry {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "craftdustry";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public Craftdustry(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for mod loading
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
        MinecraftForge.EVENT_BUS.addListener((AddReloadListenerEvent event) -> {
            event.addListener(Capes.INSTANCE);
        });
        MinecraftForge.EVENT_BUS.addListener((RegisterCommandsEvent event) -> {
            GetCapesCommand.register(event.getDispatcher());
        });

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Staring Craftdustry Server Side Stuff");
        Path worldSavePath = event.getServer().getWorldPath(LevelResource.ROOT);
        ElectricalNetworkSerializer.init(worldSavePath);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("Craftdustry Client Sided Code Is Starting!");
            MenuScreens.register(ModMenuTypes.IRON_CHEST_MENU.get(), IronChestScreen::new);
            MenuScreens.register(ModMenuTypes.WOODEN_CHEST_MENU.get(), WoodenChestScreen::new);
        }
    }

    @SubscribeEvent
    public void onWorldLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        Path worldSavePath = level.getServer().getWorldPath(LevelResource.ROOT);
        ElectricalNetworkSerializer.init(worldSavePath);

        ElectricalNetworkSerializer.loadFromSerializer();
    }


    @SubscribeEvent
    public void onWorldSave(LevelEvent.Save event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        ElectricalNetworkSerializer.saveToSerializer();
    }
}