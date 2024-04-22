package com.emilahmaboy.targeted.config;

import com.emilahmaboy.targeted.Targeted;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.MutableText;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static com.emilahmaboy.targeted.Targeted.LOGGER;

public class ConfigManager {
    private final Config configurations;
    private JsonObject savedConfigurationsContents;

    public ConfigManager() {
        configurations = new Config();
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path savedConfigurations = configDir.resolve("targeted.json");
        try {
            Files.createFile(savedConfigurations);
            String contents = "{}";
            Files.write(savedConfigurations, contents.getBytes());
            LOGGER.info("Successfully created the configurations file!");
        } catch (FileAlreadyExistsException e) {
            LOGGER.info("Configuration file is already exists! Skipping...");
        } catch (IOException e) {
            LOGGER.warn("Something went wrong while creating configuration file!");
        }

        try {
            String contents = Files.readString(savedConfigurations);
            savedConfigurationsContents = new Gson().fromJson(contents, JsonObject.class);
            for (HashMap.Entry<String, Config.BaseConfiguration> entry : configurations.getConfigurations().entrySet()) {
                String key = entry.getKey();
                Config.BaseConfiguration value = entry.getValue();

                LOGGER.info("{} >> {}", key, value.value);

                if (!savedConfigurationsContents.has(key)) {
                    savedConfigurationsContents.add(key, value.value);
                    System.out.print(value.value);
                }
            }
            System.out.print(savedConfigurationsContents);
            contents = new GsonBuilder().setPrettyPrinting().create().toJson(savedConfigurationsContents);
            LOGGER.info(contents);
            Files.write(savedConfigurations, contents.getBytes());
            LOGGER.info("Successfully regenerated the configurations file contents!");
        } catch (IOException e) {
            LOGGER.error("Couldn't read the configurations file!");
        }
    }

    public Config.BaseConfiguration getConfiguration(String key) {
        return configurations.getConfigurations().get(key);
    }

    public MutableText getConfigurationName(String key) {
        return getConfiguration(key).name;
    }

    public MutableText getConfigurationDescription(String key) {
        return getConfiguration(key).description;
    }

    public JsonElement getConfigurationDefaultValue(String key) {
        return getConfiguration(key).value;
    }

    public JsonElement getConfigurationValue(String key) {
        if (savedConfigurationsContents != null) {
            if (savedConfigurationsContents.has(key)) {
                return savedConfigurationsContents.get(key);
            }
        }
        return getConfiguration(key).value;
    }
}
