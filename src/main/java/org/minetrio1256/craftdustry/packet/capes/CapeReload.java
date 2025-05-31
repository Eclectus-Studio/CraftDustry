package org.minetrio1256.craftdustry.packet.capes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class CapeReload {

    public CapeReload() {}

    public CapeReload(FriendlyByteBuf buf) {}

    public void encode(FriendlyByteBuf buf) {}

    public void handle(CustomPayloadEvent.Context context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            context.enqueueWork(() -> {
                // Client action: reload visible capes
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null && player.clientLevel != null) {
                    player.clientLevel.players().forEach(other -> {
                        // Ask for cape data again
                        CapeUserGet request = new CapeUserGet(other.getUUID());
                        org.minetrio1256.craftdustry.packet.CraftdustryNetworking.sendToServer(request);
                    });
                }
            });
            context.setPacketHandled(true);
        });
    }
}
