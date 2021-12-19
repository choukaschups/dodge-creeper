package me.choukas.dodgecreeper.core.api.world;

import me.choukas.dodgecreeper.api.world.WorldManager;
import me.choukas.dodgecreeper.core.Configuration;
import me.choukas.dodgecreeper.core.ConfigurationKeys;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import javax.inject.Inject;

public class WorldManagerImpl implements WorldManager {

    private static final String DO_DAYLIGHT_CYCLE_GAME_RULE = "doDaylightCycle";

    private final World world;

    @Inject
    public WorldManagerImpl(@Configuration FileConfiguration configuration) {
        this.world = Bukkit.getWorld(configuration.getString(ConfigurationKeys.WORLD_NAME));
    }

    @Override
    public void init() {
        this.world.setGameRuleValue(DO_DAYLIGHT_CYCLE_GAME_RULE, String.valueOf(false));

        this.world.setStorm(false);
        this.world.setThundering(false);
    }

    @Override
    public World getWorld() {
        return this.world;
    }
}
