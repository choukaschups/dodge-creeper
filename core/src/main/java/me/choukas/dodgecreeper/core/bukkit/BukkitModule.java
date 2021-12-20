package me.choukas.dodgecreeper.core.bukkit;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public class BukkitModule extends AbstractModule {

    private final JavaPlugin plugin;

    public BukkitModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {

    }

    @Provides
    public Plugin providePlugin() {
        return this.plugin;
    }

    @Provides
    public JavaPlugin provideJavaPlugin() {
        return this.plugin;
    }

    @Provides
    public PluginManager providePluginManager() {
        return Bukkit.getPluginManager();
    }

    @Provides
    @BukkitDataFolder
    public Path provideBukkitDataFolder() {
        return this.plugin.getDataFolder().toPath();
    }

    @Provides
    @BukkitConfiguration
    public FileConfiguration provideBukkitConfiguration() {
        return this.plugin.getConfig();
    }
}
