package dev.emilahmaboy.targeted;

import dev.emilahmaboy.targeted.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import org.joml.Matrix4f;

import static java.lang.Math.*;

public class TrajectoryDebugRenderer implements DebugRenderer.Renderer {
    private final MinecraftClient client;

    public TrajectoryDebugRenderer(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        if (this.client.player != null) {
            if (Config.trajectory_showed) {
                ItemStack itemStack = this.client.player.getMainHandStack();

                double pitch = this.client.player.getPitch();
                double yaw = this.client.player.getYaw();

                double gravity = 0.03;
                double speed = 1.5;

                double x = this.client.player.getX() - cameraX + MathHelper.sin((float) ((-(yaw + 90) / 180) * PI)) * 0.05;
                double y = (this.client.player.getEyeY() - 0.1) - cameraY;
                double z = this.client.player.getZ() - cameraZ + MathHelper.cos((float) ((-(yaw + 90) / 180) * PI)) * 0.05;

                if (itemStack.getItem() instanceof RangedWeaponItem) {
                    double pullProgress = BowItem.getPullProgress(this.client.player.getItemUseTime());
                    speed = 3.0 * (pullProgress == 0.0 ? 1.0 : pullProgress);
                    gravity = 0.05;
                } else if (itemStack.getItem() instanceof SnowballItem) {
                } else if (itemStack.getItem() instanceof TridentItem) {
                    speed = 2.5;
                    gravity = 0.05;
                } else {
                    return;
                }

                double xDelta = -MathHelper.sin((float) (yaw * (PI / 180))) * MathHelper.cos((float) (pitch * (PI / 180))) * speed;
                double yDelta = -MathHelper.sin((float) ((pitch) * (PI / 180))) * speed;
                double zDelta = MathHelper.cos((float) (yaw * (PI / 180))) * MathHelper.cos((float) (pitch * (PI / 180))) * speed;

                double brightness = 1.0;


                Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getDebugLineStrip(100.0));

                vertexConsumer.vertex(matrix4f, (float) x, (float) y, (float) z).color(0.0F, (float) (1.0F * brightness), 0.0F, 0.0F).next();
                for (int i = 0; i <= 24; i++) {
                    x += xDelta;
                    xDelta *= 0.99;
                    y += yDelta;
                    yDelta *= 0.99;
                    yDelta -= gravity;
                    z += zDelta;
                    zDelta *= 0.99;
                    vertexConsumer.vertex(matrix4f, (float) x, (float) y, (float) z).color(0.0F, (float) (1.0F * brightness), 0.0F, 1.0F).next();
                    brightness *= 0.95;
                }
                vertexConsumer.vertex(matrix4f, (float) x, (float) y, (float) z).color(0.0F, (float) (1.0F * brightness), 0.0F, 0.0F).next();
            }
        }
    }
}
