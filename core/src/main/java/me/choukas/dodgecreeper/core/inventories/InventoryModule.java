package me.choukas.dodgecreeper.core.inventories;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    public InventoryManager provideInventoryManager(JavaPlugin plugin) {
        return new InventoryManager(plugin);
    }
}
