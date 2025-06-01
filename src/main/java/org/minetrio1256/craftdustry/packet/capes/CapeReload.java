package org.minetrio1256.craftdustry.packet.capes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.minetrio1256.craftdustry.client.ClientCapeCache;
import org.minetrio1256.craftdustry.packet.CraftdustryNetworking;

import java.util.UUID;

public class CapeReload {

    public CapeReload() {}

    public CapeReload(FriendlyByteBuf buf) {}

    public void encode(FriendlyByteBuf buf) {}

    public void handle(CustomPayloadEvent.Context context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            context.enqueueWork(() -> {
                // Client action: reload visible capes
                ClientCapeCache.clear(); // or selectively clear per-player if needed

                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null && player.clientLevel != null) {
                    player.clientLevel.players().forEach(other -> {
                        // Ask for cape data again
                        CapeUserGet request = new CapeUserGet(other.getUUID());
                        CraftdustryNetworking.sendToServer(request);
                    });
                }
                Minecraft.getInstance().getConnection().getOnlinePlayers().forEach(info -> {
                    UUID uuid = info.getProfile().getId();
                    // Force skin/cape re-render by "touching" the display name
                    info.setTabListDisplayName(info.getTabListDisplayName());
                });
            });
            context.setPacketHandled(true);
        });
    }
}
