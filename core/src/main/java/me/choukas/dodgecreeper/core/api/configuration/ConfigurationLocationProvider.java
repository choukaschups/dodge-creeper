package me.choukas.dodgecreeper.core.api.configuration;

import me.choukas.dodgecreeper.api.world.WorldManager;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import javax.inject.Inject;
import java.util.Map;

public class ConfigurationLocationProvider {

    private final WorldManager worldManager;

    @Inject
    public ConfigurationLocationProvider(WorldManager worldManager) {
        this.worldManager = worldManager;
    }

    public Location getLocation(ConfigurationSection section) {
        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");
        float yaw = section.getFloat("yaw");
        float pitch = section.getFloat("pitch");

        return new Location(this.worldManager.getWorld(), x, y, z, yaw, pitch);
    }

    public Location getLocation(Map<String, Object> location) {
        double x = (double) location.get("x");
        double y = (double) location.get("y");
        double z = (double) location.get("z");
        float yaw = (float) location.get("yaw");
        float pitch = (float) location.get("pitch");

        return new Location(this.worldManager.getWorld(), x, y, z, yaw, pitch);
    }
}
