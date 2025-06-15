package org.minetrio1256.craftdustry.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.minetrio1256.craftdustry.packet.CraftdustryNetworking;
import org.minetrio1256.craftdustry.packet.capes.CapeReload;
import org.minetrio1256.craftdustry.packet.capes.CapeUserGet;

public class ClientReloadCape {
    public static void reload() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        // Iterate over all players that the client knows about
        for (Player player : mc.level.players()) {
            // Request the server to send cape info for each
            CraftdustryNetworking.sendToServer(new CapeUserGet(player.getUUID()));
        }
        AbstractClientPlayer clientPlayer = (AbstractClientPlayer) Minecraft.getInstance().player;
        clientPlayer.refreshDimensions();
    }
}
