package com.emilahmaboy.targeted;

import net.fabricmc.api.ModInitializer;

import com.emilahmaboy.targeted.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Targeted implements ModInitializer {
    public static final String MOD_ID = "targeted";
    public static final Logger LOGGER = LoggerFactory.getLogger("Targeted");
    public static final ConfigManager configurations = new ConfigManager();

    @Override
    public void onInitialize() {

    }
}
