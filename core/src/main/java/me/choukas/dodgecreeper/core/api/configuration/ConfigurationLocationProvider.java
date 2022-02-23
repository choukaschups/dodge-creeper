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

    public Location getLocation(Map<String, Number> location) {
        double x = location.get("x").doubleValue();
        double y = location.get("y").doubleValue();
        double z = location.get("z").doubleValue();
        float yaw = location.get("yaw").floatValue();
        float pitch = location.get("pitch").floatValue();

        return new Location(this.worldManager.getWorld(), x, y, z, yaw, pitch);
    }
}
