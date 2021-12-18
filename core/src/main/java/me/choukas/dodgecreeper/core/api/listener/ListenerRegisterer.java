package me.choukas.dodgecreeper.core.api.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import javax.inject.Inject;
import java.util.Set;

public class ListenerRegisterer {

    private final PluginManager pluginManager;
    private final Plugin plugin;
    private final Set<Listener> listeners;

    @Inject
    public ListenerRegisterer(PluginManager pluginManager,
                              Plugin plugin,
                              Set<Listener> listeners) {
        this.pluginManager = pluginManager;
        this.plugin = plugin;
        this.listeners = listeners;
    }

    public void registerListeners() {
        this.listeners.forEach(listener -> this.pluginManager.registerEvents(listener, this.plugin));
    }
}
