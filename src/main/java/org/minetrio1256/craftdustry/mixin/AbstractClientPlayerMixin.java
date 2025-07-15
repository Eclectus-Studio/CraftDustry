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

    @Inject(method = "getSkin", at = @At("TAIL"), cancellable = true)
    public void getSkinMixin(CallbackInfoReturnable<PlayerSkin> cir) {
        System.out.println("Skin mixin");
        PlayerInfo info = this.getPlayerInfo();
        if (info == null) return;
        System.out.println("Loading client player mixin for " + info.getProfile().getName());

        UUID uuid = this.getUUID();
        ResourceLocation xmas = ResourceLocation.fromNamespaceAndPath("craftdustry","textures/cape/xmas.png");
        PlayerSkin skin = cir.getReturnValue();
        cir.setReturnValue(new PlayerSkin(
                skin.texture(),
                skin.textureUrl(),
                xmas,
                xmas,
                skin.model(),
                true
        ));
    }

}
