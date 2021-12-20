package me.choukas.dodgecreeper.core;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import me.choukas.dodgecreeper.core.api.bootstrap.Bootstrap;
import me.choukas.dodgecreeper.core.api.shutdown.ShutdownHooks;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class DodgeCreeperPlugin extends JavaPlugin {

    private ShutdownHooks hooks;

    @Override
    public void onEnable() {
        try {
            Injector injector = new Bootstrap()
                    .bootstrap(this);

            this.hooks = injector.getInstance(ShutdownHooks.class);
        } catch (CreationException e) {
            this.getLogger().info("An error occurred during the plugin's boostrap. Please refer to the following exception for further details.");

            e.printStackTrace();
        }

        this.getLogger().info("Plugin successfully enabled");
    }

    @Override
    public void onDisable() {
        // TODO Fix exception when error on injector creation
        Optional.ofNullable(this.hooks).ifPresent(ShutdownHooks::shutdown);

        this.getLogger().info("Plugin successfully disabled");
    }
}
