package tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.emilahmaboy.targeted.Targeted;

import java.util.List;
import java.util.Map;

public class ConfigScreenScrollViewWidget extends ElementListWidget<ConfigScreenScrollViewWidget.Entry> {
    public ConfigScreenScrollViewWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int entryHeight) {
        super(minecraftClient, width, height, top, bottom, entryHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        clearEntries();
        for (Map.Entry<String, JsonElement> entry : Targeted.configDefault.entrySet()) {
            String key = entry.getKey();
            JsonObject value = entry.getValue().getAsJsonObject();
            children().add(new ConfigScreenScrollViewWidget.Entry(value, Targeted.config.get(key)));
        }
        this.enableScissor(context);
        this.renderList(context, mouseX, mouseY, delta);
        context.disableScissor();
    }

    protected static class Entry extends ElementListWidget.Entry<Entry> {
        protected MutableText name;
        protected String type;
        protected JsonElement value;

        public Entry(JsonObject entry, JsonElement value) {
            this.name = Text.literal(String.valueOf(entry.get("name")));
            this.type = String.valueOf(entry.get("type"));
            this.value = value;
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return null;
        }

        @Override
        public List<? extends Element> children() {
            return null;
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {

        }
    }
}
