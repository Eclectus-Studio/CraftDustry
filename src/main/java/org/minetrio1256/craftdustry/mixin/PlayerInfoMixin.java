package org.minetrio1256.craftdustry.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.minetrio1256.craftdustry.client.ClientCapeCache;
import org.minetrio1256.craftdustry.packet.CraftdustryNetworking;
import org.minetrio1256.craftdustry.packet.capes.CapeUserGet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerInfo.class)
public abstract class PlayerInfoMixin {

    @Shadow public abstract GameProfile getProfile();

    @Inject(method = "getSkin", at = @At("RETURN"), cancellable = true)
    public void overrideCape(CallbackInfoReturnable<PlayerSkin> cir) {
        UUID uuid = this.getProfile().getId();
        ResourceLocation capeId = ClientCapeCache.get(uuid);

        if (capeId == null && !ClientCapeCache.has(uuid)) {
            // Send request once
            CraftdustryNetworking.sendToServer(new CapeUserGet(uuid));
            ClientCapeCache.set(uuid, null); // Avoid spamming
        }

        PlayerSkin skin = cir.getReturnValue();
        cir.setReturnValue(new PlayerSkin(
                skin.texture(),
                skin.textureUrl(),
                capeId != null ? capeId : skin.capeTexture(), // fallback
                capeId != null ? capeId : skin.elytraTexture(),
                skin.model(),
                skin.secure()
        ));
    }
}