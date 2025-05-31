package org.minetrio1256.craftdustry.packet.capes;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraft.server.level.ServerPlayer;
import org.minetrio1256.craftdustry.api.CapeRegistry;
import org.minetrio1256.craftdustry.data.cape.Cape;
import org.minetrio1256.craftdustry.data.cape.Capes;
import org.minetrio1256.craftdustry.packet.CraftdustryNetworking;

import java.util.UUID;

public class CapeUserGet {
    private final UUID target;

    public CapeUserGet(UUID target) {
        this.target = target;
    }

    public CapeUserGet(FriendlyByteBuf buf) {
        this.target = buf.readUUID();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(target);
    }

    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            if (sender == null) return;

            var capeId = CapeRegistry.getCape(target);
            if (capeId == null) return;

            Cape cape = Capes.INSTANCE.getCape(capeId);
            if (cape == null) return;

            CraftdustryNetworking.sendToPlayer(new CapeUserSend(target, cape.texture), sender);
        });
        context.setPacketHandled(true);
    }
}
