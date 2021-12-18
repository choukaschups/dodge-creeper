package me.choukas.dodgecreeper.core.api.item;

import me.choukas.dodgecreeper.api.item.ItemListener;
import me.choukas.dodgecreeper.api.item.ItemListenerManager;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class ItemListenerManagerImpl implements ItemListenerManager {

    private final Map<UUID, ItemListener> listeners;

    @Inject
    public ItemListenerManagerImpl(Map<UUID, ItemListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public ItemListener getItemListener(UUID uuid) {
        return this.listeners.get(uuid);
    }
}
