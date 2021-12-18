package me.choukas.dodgecreeper.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.minuskube.inv.InventoryManager;
import me.choukas.dodgecreeper.core.api.CoreModule;
import me.choukas.dodgecreeper.core.api.translation.TranslationRegisterer;
import me.choukas.dodgecreeper.core.api.BukkitModule;
import me.choukas.dodgecreeper.core.api.listener.ListenerRegisterer;
import me.choukas.dodgecreeper.core.inventories.InventoryModule;
import me.choukas.dodgecreeper.core.items.ItemModule;
import me.choukas.dodgecreeper.core.listeners.ListenerModule;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.Optional;

@SuppressWarnings("unused")
public class DodgeCreeperPlugin extends JavaPlugin {

    private BukkitAudiences adventure;

    // Internal
    public DodgeCreeperPlugin() {

    }

    // Mocking
    public DodgeCreeperPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {
        Injector injector = Guice.createInjector(
                new BukkitModule(this),
                new CoreModule(),
                new InventoryModule(),
                new ItemModule(),
                new ListenerModule()
        );

        this.adventure = injector.getInstance(BukkitAudiences.class);

        injector.getInstance(InventoryManager.class).init();

        injector.getInstance(ListenerRegisterer.class).registerListeners();
        injector.getInstance(TranslationRegisterer.class).registerTranslations();

        this.getLogger().info("Plugin successfully enabled");
    }

    @Override
    public void onDisable() {
        Optional.ofNullable(this.adventure).ifPresent(BukkitAudiences::close);

        this.getLogger().info("Plugin successfully disabled");
    }
}
