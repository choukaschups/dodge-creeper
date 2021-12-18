package me.choukas.dodgecreeper.core.items;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import me.choukas.dodgecreeper.api.item.ItemListener;

import java.util.UUID;

public class ItemModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder<UUID, ItemListener> itemBinder = MapBinder.newMapBinder(binder(), UUID.class, ItemListener.class);
        itemBinder.addBinding(InstanceMenuItem.Listener.UUID).to(InstanceMenuItem.Listener.class);
    }
}
