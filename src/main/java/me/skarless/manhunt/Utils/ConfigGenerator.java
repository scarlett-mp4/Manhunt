package me.skarless.manhunt.Utils;

import me.skarless.manhunt.Manhunt;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ConfigGenerator {

    private final String configName;
    private FileConfiguration config = null;
    private File file = null;

    public ConfigGenerator(String configName) {
        this.configName = configName;
    }

    // Method to reload the config.
    public void reloadConfig() {
        if (this.file == null) {
            this.file = new File(Manhunt.getInstance().getDataFolder(), configName);
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);

        InputStream doorStream = Manhunt.getInstance().getResource(configName);
        if (doorStream != null) {
            YamlConfiguration defaultWarpConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(doorStream));
            this.config.setDefaults(defaultWarpConfig);
        }
    }

    // Method to grab the config.
    public FileConfiguration getConfig() {
        if (this.config == null) {
            reloadConfig();
        }
        return this.config;
    }

    // Method to save the config file.
    public void saveConfig() {
        if (this.config == null || this.file == null) {
            return;
        }
        try {
            this.getConfig().save(this.file);
        } catch (IOException e) {
            Manhunt.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + file, e);
        }
    }

    // Method to load the default config.
    // Plugin will grab from the set defaults if the user does not specify said value.
    public void saveDefaultConfig() {
        if (this.file == null) {
            this.file = new File(Manhunt.getInstance().getDataFolder(), configName);
        }
        if (!this.file.exists()) {
            Manhunt.getInstance().saveResource(configName, false);
        }
    }
}