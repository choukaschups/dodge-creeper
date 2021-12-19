package me.choukas.dodgecreeper.core.api;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import me.choukas.dodgecreeper.core.Configuration;
import me.choukas.dodgecreeper.core.DataFolder;
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
    @DataFolder
    public Path provideDataFolder() {
        return this.plugin.getDataFolder().toPath();
    }

    @Provides
    @Configuration
    public FileConfiguration provideConfiguration() {
        return this.plugin.getConfig();
    }
}
