package dev.emilahmaboy.targeted.mixin;


import dev.emilahmaboy.targeted.TrajectoryDebugRenderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.render.debug.DebugRenderer")
public class TrajectoryDebugRendererMixin {
    @Unique
    private DebugRenderer.Renderer targeted$trajectoryDebugRenderer;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(MinecraftClient client, CallbackInfo ci) {
        this.targeted$trajectoryDebugRenderer = new TrajectoryDebugRenderer(client);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void render(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        this.targeted$trajectoryDebugRenderer.render(matrices, vertexConsumers, cameraX, cameraY, cameraZ);
    }
}
