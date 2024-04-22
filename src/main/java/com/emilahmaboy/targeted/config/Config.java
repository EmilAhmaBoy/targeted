package com.emilahmaboy.targeted.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.HashMap;

import static com.emilahmaboy.targeted.Targeted.LOGGER;

public class Config {
    private static final HashMap<String, BaseConfiguration> configurations = new HashMap<>();

    public Config() {
        configurations.put("showThrowingTrajectory", new ShowThrowingTrajectory());
        LOGGER.info("hashmap:");
        LOGGER.info(String.valueOf(configurations));
    }

    public HashMap<String, BaseConfiguration> getConfigurations() {
        return configurations;
    }

    public static abstract class BaseConfiguration {
        MutableText name = Text.translatable("text.targeted.no_name");
        MutableText description = Text.translatable("text.targeted.no_description");
        JsonElement value;
    }

    private static class ShowThrowingTrajectory extends BaseConfiguration {
        MutableText name = Text.translatable("text.targeted.show_throwing_trajectory");
        MutableText description = Text.translatable("text.targeted.show_throwing_trajectory_description");
        JsonElement value = (new Gson()).toJsonTree(true);
    }
}
