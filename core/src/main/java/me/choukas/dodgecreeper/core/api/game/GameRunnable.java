package me.choukas.dodgecreeper.core.api.game;

import me.choukas.dodgecreeper.api.world.WorldManager;
import me.choukas.dodgecreeper.core.bukkit.BukkitConfiguration;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationImpl;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationKeys;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationLocationProvider;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creeper;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class GameRunnable extends BukkitRunnable {

    private final WorldManager worldManager;
    private final GameRunnableConfiguration configuration;

    private long timer;

    @Inject
    public GameRunnable(WorldManager worldManager, GameRunnableConfiguration configuration) {
        this.worldManager = worldManager;
        this.configuration = configuration;

        this.timer = 0;
    }

    @Override
    public void run() {
        if (timer % this.configuration.getCreeperSpawnTimeInterval() == 0) {
            // Creeper spawn
            List<Location> creeperSpawnLocations = this.configuration.getCreeperSpawnLocations();
            int creeperSpawnLocationIndex = new Random().nextInt(creeperSpawnLocations.size());
            Location creeperSpawnLocation = creeperSpawnLocations.get(creeperSpawnLocationIndex);

            Creeper creeper = this.worldManager.getWorld().spawn(creeperSpawnLocation, Creeper.class);
            creeper.setPowered(true);
        }

        timer++;
    }

    private static class GameRunnableConfiguration extends ConfigurationImpl {

        private final ConfigurationLocationProvider configurationLocationProvider;

        @Inject
        public GameRunnableConfiguration(@BukkitConfiguration FileConfiguration configuration, ConfigurationLocationProvider configurationLocationProvider) {
            super(configuration);

            this.configurationLocationProvider = configurationLocationProvider;
        }

        @SuppressWarnings("unchecked")
        public List<Location> getCreeperSpawnLocations() {
            return this.configuration.getList(ConfigurationKeys.CREEPER_SPAWN_LOCATIONS.getKey()).stream()
                    .map(location ->
                            this.configurationLocationProvider.getLocation((Map<String, Object>) location)
                    ).collect(Collectors.toList());
        }
    }
}
