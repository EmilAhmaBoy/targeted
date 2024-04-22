package tests;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;


@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private static final int headerHeight = 36;
    private final Screen parent;
    public ConfigScreen(Screen parent) {
        super(Text.literal("Targeted"));

        this.parent = parent;
    }

    public ConfigScreenScrollViewWidget scrollView;

    @Override
    protected void init() {
        scrollView = new ConfigScreenScrollViewWidget(this.client, (int)(this.width * 0.65), this.height, headerHeight, this.height - 10, 100);
        scrollView.setLeftPos((this.width - (int)(this.width * 0.65)) / 2);
        addSelectableChild(scrollView);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Targeted"), width / 2, headerHeight / 2 - textRenderer.fontHeight / 2, 0xFFFFFF);
    }

    @Override
    public void renderBackground(DrawContext context) {
        renderBackgroundTexture(context);
    }

    @Override
    public void renderBackgroundTexture(DrawContext context) {
        if (this.client != null) {
            context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
            if (this.client.world != null) {
                context.drawTexture(OPTIONS_BACKGROUND_TEXTURE, (this.width - (int)(this.width * 0.75)) / 2, 0, 0, 0.0F, 0.0F, (int)(this.width * 0.75), this.height, 32, 32);
            } else {
                context.drawTexture(OPTIONS_BACKGROUND_TEXTURE, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
            }
            context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            /*
            context.setShaderColor(0.12F, 0.12F, 0.12F, 1.0F);
            context.drawTexture(OPTIONS_BACKGROUND_TEXTURE, this.width / 4, 0, 0, 0.0F, 0.0F, this.width / 2, this.height, 32, 32);
            context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            */
        }
    }


    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
}
