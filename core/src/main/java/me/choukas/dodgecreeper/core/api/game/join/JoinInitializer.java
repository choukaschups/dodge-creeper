package me.choukas.dodgecreeper.core.api.game.join;

import me.choukas.dodgecreeper.core.api.configuration.ConfigurationImpl;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationKeys;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationLocationProvider;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import me.choukas.dodgecreeper.core.bukkit.BukkitConfiguration;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class JoinInitializer {

    private static final double DEFAULT_MAX_HEALTH = 20.;
    private static final int DEFAULT_FOOD_LEVEL = 20;

    private final ScoreboardManager scoreboardManager;
    private final Configuration configuration;

    @Inject
    public JoinInitializer(ScoreboardManager scoreboardManager,
                           Configuration configuration) {
        this.scoreboardManager = scoreboardManager;
        this.configuration = configuration;
    }

    public void init(Player player) {
        player.getInventory().clear();

        player.setLevel(0);
        player.setExp(0.f);

        player.setFoodLevel(DEFAULT_FOOD_LEVEL);

        player.setMaxHealth(DEFAULT_MAX_HEALTH);
        player.setHealth(player.getMaxHealth());

        Location hubSpawnLocation = this.configuration.getHubSpawnLocation();
        player.teleport(hubSpawnLocation);

        this.scoreboardManager.addScoreboard(player);
    }

    private static class Configuration extends ConfigurationImpl {

        private final ConfigurationLocationProvider configurationLocationProvider;

        @Inject
        public Configuration(@BukkitConfiguration FileConfiguration configuration, ConfigurationLocationProvider configurationLocationProvider) {
            super(configuration);

            this.configurationLocationProvider = configurationLocationProvider;
        }

        public Location getHubSpawnLocation() {
            return this.configurationLocationProvider.getLocation(
                    this.configuration.getConfigurationSection(ConfigurationKeys.HUB_SPAWN_POINT.getKey())
            );
        }
    }
}
