package me.choukas.dodgecreeper.core.api.utils;

import me.choukas.dodgecreeper.api.world.WorldManager;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.inject.Inject;

public class ConfigurationUtils {

    private final FileConfiguration configuration;
    private final WorldManager worldManager;

    @Inject
    public ConfigurationUtils(FileConfiguration configuration, WorldManager worldManager) {
        this.configuration = configuration;
        this.worldManager = worldManager;
    }

    public Location getLocation(String path) {
        ConfigurationSection section = this.configuration.getConfigurationSection(path);

        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        float yaw = section.getFloat("yaw");
        float pitch = section.getFloat("pitch");

        return new Location(this.worldManager.getWorld(), x, y, z, yaw, pitch);
    }
}
