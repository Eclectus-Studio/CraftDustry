package org.minetrio1256.craftdustry.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class CapeLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation xmas = ResourceLocation.fromNamespaceAndPath("craftdustry","textures/cape/xmas.png");

    public CapeLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            AbstractClientPlayer player,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        System.out.println("cape!");
        if (player.isInvisible()) return;

        // Push pose stack to isolate cape movement
        poseStack.pushPose();

        // Add slight offset to position the cape correctly
        poseStack.translate(0.0D, 0.0D, 0.125D);

        // Get the player model
        PlayerModel<AbstractClientPlayer> model = this.getParentModel();

        // Prepare render type and vertex buffer
        var vertexConsumer = bufferSource.getBuffer(model.renderType(xmas));

        // Render the cape layer
        model.renderCloak(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }
}