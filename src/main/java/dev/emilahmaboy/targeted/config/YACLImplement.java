package dev.emilahmaboy.targeted.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class YACLImplement {
    public static Screen createScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.literal("Targeted"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("dev.emilahmaboy.targeted.config.general"))
                        .tooltip(Text.translatable("dev.emilahmaboy.targeted.config.general_description"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("dev.emilahmaboy.targeted.config.trajectory_settings"))
                                .description(OptionDescription.createBuilder()
                                        .text(Text.translatable("dev.emilahmaboy.targeted.config.trajectory_settings_description"))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("dev.emilahmaboy.targeted.config.show_throwing_trajectory"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("dev.emilahmaboy.targeted.config.show_throwing_trajectory_description"))
                                                .build())
                                        .controller(TickBoxControllerBuilder::create)
                                        .binding(
                                                true,
                                                () -> Config.trajectory_showed,
                                                newVal -> Config.trajectory_showed = newVal
                                        )
                                        .build())
                                .build())
                        .build())
                .build()
                .generateScreen(parent);
    }
}
