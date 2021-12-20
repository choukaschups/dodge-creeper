package me.choukas.dodgecreeper.core.api.configuration;

import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.core.bukkit.BukkitConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.inject.Inject;
import java.util.Optional;

// TODO Clean up method order
public class ConfigurationImpl implements Configuration {

    protected final FileConfiguration configuration;

    @Inject
    public ConfigurationImpl(@BukkitConfiguration FileConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public int getMinimumPlayerAmount() {
        return this.configuration.getInt(ConfigurationKeys.MIN_PLAYERS.getKey());
    }

    @Override
    public int getMaximumPlayerAmount() {
        return this.configuration.getInt(ConfigurationKeys.MAX_PLAYERS.getKey());
    }

    @Override
    public Optional<Integer> getTimerLevel(int playerAmount) {
        String formattedPlayerAmount;

        if (playerAmount == this.getMinimumPlayerAmount()) {
            formattedPlayerAmount = ConfigurationKeys.MIN_PLAYERS.getKey();
        } else if (playerAmount == this.getMaximumPlayerAmount()) {
            formattedPlayerAmount = ConfigurationKeys.MAX_PLAYERS.getKey();
        } else {
            formattedPlayerAmount = String.valueOf(playerAmount);
        }

        ConfigurationSection section = this.getTimerLevelsSection();

        if (!section.contains(formattedPlayerAmount)) {
            return Optional.empty();
        } else {
            return Optional.of(section.getInt(formattedPlayerAmount));
        }
    }

    @Override
    public int getMinimumPlayerAmountTimerLevel() {
        return this.getTimerLevelsSection().getInt(ConfigurationKeys.MIN_PLAYERS.getKey());
    }

    // TODO Change name
    @Override
    public boolean shouldPrintTimer(int timer) {
        return this.getTimerSection().getIntegerList(ConfigurationKeys.TIMER_PRINT.getKey()).contains(timer);
    }

    @Override
    public int getTimerStayingTime() {
        return this.getTimerSection().getInt(ConfigurationKeys.TIMER_STAYING_TIME.getKey());
    }

    @Override
    public int getCreeperSpawnTimeInterval() {
        return this.configuration.getInt(ConfigurationKeys.CREEPER_SPAWN_INTERVAL.getKey());
    }

    @Override
    public World getWorld() {
        return Bukkit.getWorld(this.configuration.getString(ConfigurationKeys.WORLD_NAME.getKey()));
    }

    @Override
    public int getHeightLimit() {
        return this.configuration.getInt(ConfigurationKeys.HEIGHT_LIMIT.getKey());
    }

    private ConfigurationSection getTimerSection() {
        return this.configuration.getConfigurationSection(ConfigurationKeys.TIMER.getKey());
    }

    private ConfigurationSection getTimerLevelsSection() {
        return this.getTimerSection().getConfigurationSection(ConfigurationKeys.TIMER_LEVELS.getKey());
    }
}
