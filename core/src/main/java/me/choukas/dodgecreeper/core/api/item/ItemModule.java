package me.choukas.dodgecreeper.core.api.item;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.api.item.ItemListenerManager;

public class ItemModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ItemListenerManager.class).to(ItemListenerManagerImpl.class);
    }
}
