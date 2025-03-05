package org.minetrio1256.craftdustry.screen.chests.iron_chest;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.minetrio1256.craftdustry.Main;

public class IronChestScreen extends AbstractContainerScreen<IronChestMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "textures/gui/iron_chest/iron_chest.png");

    public IronChestScreen(final IronChestMenu pMenu, final Inventory pPlayerInventory, final Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        inventoryLabelY = 89;
        imageWidth = 176;
        imageHeight = 183;
    }

    @Override
    protected void renderBg(final GuiGraphics guiGraphics, final float partialTicks, final int pMouseX, final int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, IronChestScreen.GUI_TEXTURE);
        final int x = (this.width - this.imageWidth) / 2;
        final int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(IronChestScreen.GUI_TEXTURE, x, y + 9, 0, 0, this.imageWidth, this.imageHeight);

    }

    @Override
    public void render(final GuiGraphics guiGraphics, final int mouseX, final int mouseY, final float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
