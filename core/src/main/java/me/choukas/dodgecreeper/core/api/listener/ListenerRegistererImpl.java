package me.choukas.dodgecreeper.core.api.listener;

import me.choukas.dodgecreeper.api.listener.ListenerRegisterer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import javax.inject.Inject;
import java.util.Set;

public class ListenerRegistererImpl implements ListenerRegisterer {

    private final PluginManager pluginManager;
    private final Plugin plugin;
    private final Set<Listener> listeners;

    @Inject
    public ListenerRegistererImpl(PluginManager pluginManager,
                                  Plugin plugin,
                                  Set<Listener> listeners) {
        this.pluginManager = pluginManager;
        this.plugin = plugin;
        this.listeners = listeners;
    }

    @Override
    public void registerListeners() {
        this.listeners.forEach(listener -> this.pluginManager.registerEvents(listener, this.plugin));
    }
}
