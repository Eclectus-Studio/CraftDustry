package org.minetrio1256.craftdustry.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.minetrio1256.craftdustry.Craftdustry;
import org.minetrio1256.craftdustry.api.CapeRegistry;
import org.minetrio1256.craftdustry.client.ClientCapeCache;
import org.minetrio1256.craftdustry.data.player.UnlockedCape;
import org.minetrio1256.craftdustry.packet.CraftdustryNetworking;
import org.minetrio1256.craftdustry.packet.capes.CapeUserGet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.UUID;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {
    @Shadow
    @Nullable
    protected abstract net.minecraft.client.multiplayer.PlayerInfo getPlayerInfo();

    @Shadow @Nullable private PlayerInfo playerInfo;

    public AbstractClientPlayerMixin(Level pLevel, BlockPos pPos, float pYRot, GameProfile pGameProfile) {
        super(pLevel, pPos, pYRot, pGameProfile);
    }

    @Inject(method = "getSkin", at = @At("RETURN"), cancellable = false)
    public void overrideCape(CallbackInfoReturnable<PlayerSkin> cir) {
        UUID uuid = playerInfo.getProfile().getId();
        ResourceLocation customCape = ClientCapeCache.get(uuid);

        if (customCape == null && !ClientCapeCache.has(uuid)) {
            // Request once
            CraftdustryNetworking.sendToServer(new CapeUserGet(uuid));
            ClientCapeCache.set(uuid, null); // avoid spamming
        }

        if (customCape != null) {
            PlayerSkin old = cir.getReturnValue();
            // Replace only the cape/elytra
            cir.setReturnValue(new PlayerSkin(
                    old.texture(),
                    old.textureUrl(),
                    customCape, // <- Custom cape
                    customCape, // <- Elytra uses same
                    old.model(),
                    old.secure()
            ));
        }
    }
}
