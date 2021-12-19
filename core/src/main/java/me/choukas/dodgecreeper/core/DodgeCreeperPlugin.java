package me.choukas.dodgecreeper.core;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import me.choukas.dodgecreeper.core.api.bootstrap.Bootstrap;
import me.choukas.dodgecreeper.core.api.bootstrap.ProductionBootstrap;
import me.choukas.dodgecreeper.core.api.shutdown.ShutdownHooks;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.Optional;

@SuppressWarnings("unused")
public class DodgeCreeperPlugin extends JavaPlugin {

    private final Bootstrap bootstrap;

    private ShutdownHooks hooks;

    // Mocking
    public DodgeCreeperPlugin() {
        super();

        this.bootstrap = new ProductionBootstrap();
    }

    // Mocking
    public DodgeCreeperPlugin(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    // Mocking
    public DodgeCreeperPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);

        this.bootstrap = new ProductionBootstrap();
    }

    @Override
    public void onEnable() {
        try {
            Injector injector = this.bootstrap.bootstrap(this);

            this.hooks = injector.getInstance(ShutdownHooks.class);
        } catch (CreationException e) {
            this.getLogger().info("An error occurred during the plugin's boostrap. Please refer to the following exception for further details.");

            e.printStackTrace();
        }

        this.getLogger().info("Plugin successfully enabled");
    }

    @Override
    public void onDisable() {
        Optional.ofNullable(this.hooks).ifPresent(ShutdownHooks::shutdown);

        this.getLogger().info("Plugin successfully disabled");
    }
}
