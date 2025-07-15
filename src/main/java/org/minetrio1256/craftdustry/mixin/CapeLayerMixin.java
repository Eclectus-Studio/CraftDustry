package org.minetrio1256.craftdustry.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.PlayerModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public class CapeLayerMixin {
    private static final ResourceLocation XMAS_CAPE = ResourceLocation.fromNamespaceAndPath("craftdustry", "textures/cape/xmas.png");

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void injectCapeRender(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            AbstractClientPlayer player,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch,
            CallbackInfo ci
    ) {
        if (!player.isInvisible() && player.isModelPartShown(PlayerModelPart.CAPE)) {
            ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
            if (!chestItem.is(Items.ELYTRA)) {
                poseStack.pushPose();
                poseStack.translate(0.0F, 0.0F, 0.125F);

                double dX = Mth.lerp((double) partialTicks, player.xCloakO, player.xCloak) - Mth.lerp((double) partialTicks, player.xo, player.getX());
                double dY = Mth.lerp((double) partialTicks, player.yCloakO, player.yCloak) - Mth.lerp((double) partialTicks, player.yo, player.getY());
                double dZ = Mth.lerp((double) partialTicks, player.zCloakO, player.zCloak) - Mth.lerp((double) partialTicks, player.zo, player.getZ());

                float bodyRot = Mth.rotLerp(partialTicks, player.yBodyRotO, player.yBodyRot);
                double sin = Mth.sin(bodyRot * ((float)Math.PI / 180F));
                double cos = -Mth.cos(bodyRot * ((float)Math.PI / 180F));

                float verticalOffset = (float) dY * 10.0F;
                verticalOffset = Mth.clamp(verticalOffset, -6.0F, 32.0F);

                float swingZ = (float) (dX * sin + dZ * cos) * 100.0F;
                swingZ = Mth.clamp(swingZ, 0.0F, 150.0F);

                float swingY = (float) (dX * cos - dZ * sin) * 100.0F;
                swingY = Mth.clamp(swingY, -20.0F, 20.0F);

                if (swingZ < 0.0F) swingZ = 0.0F;

                float bob = Mth.lerp(partialTicks, player.oBob, player.bob);
                verticalOffset += Mth.sin(Mth.lerp(partialTicks, player.walkDistO, player.walkDist) * 6.0F) * 32.0F * bob;

                if (player.isCrouching()) {
                    verticalOffset += 25.0F;
                }

                poseStack.mulPose(Axis.XP.rotationDegrees(6.0F + swingZ / 2.0F + verticalOffset));
                poseStack.mulPose(Axis.ZP.rotationDegrees(swingY / 2.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - swingY / 2.0F));

                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(XMAS_CAPE));
                PlayerModel<AbstractClientPlayer> model = (PlayerModel<AbstractClientPlayer>) ((RenderLayer<AbstractClientPlayer, ?>) (Object) this).getParentModel();
                model.renderCloak(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

                poseStack.popPose();
                ci.cancel();
            }
        }
    }
}
