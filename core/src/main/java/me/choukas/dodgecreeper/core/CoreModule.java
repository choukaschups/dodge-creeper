package me.choukas.dodgecreeper.core;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.core.inventories.InventoryModule;
import me.choukas.dodgecreeper.core.items.ItemModule;
import me.choukas.dodgecreeper.core.listeners.ListenerModule;

public class CoreModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new InventoryModule());
        install(new ItemModule());
        install(new ListenerModule());
    }
}
