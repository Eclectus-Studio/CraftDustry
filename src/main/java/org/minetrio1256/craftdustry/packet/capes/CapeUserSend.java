package org.minetrio1256.craftdustry.packet.capes;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import org.minetrio1256.craftdustry.client.ClientCapeCache;

import java.util.UUID;

public class CapeUserSend {
    private final UUID target;
    private final String texture;

    public CapeUserSend(UUID target, String texture) {
        this.target = target;
        this.texture = texture;
    }

    public CapeUserSend(FriendlyByteBuf buf) {
        this.target = buf.readUUID();
        this.texture = buf.readUtf();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(target);
        buf.writeUtf(texture);
    }

    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                ResourceLocation location = ResourceLocation.tryParse(texture);
                if (location != null) {
                    ClientCapeCache.set(target, location);
                }
            });
        });
        context.setPacketHandled(true);
    }
}
