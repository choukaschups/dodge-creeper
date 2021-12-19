package me.choukas.dodgecreeper.core.listeners;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import me.choukas.dodgecreeper.core.listeners.player.PlayerInteractListener;
import me.choukas.dodgecreeper.core.listeners.player.PlayerJoinListener;
import me.choukas.dodgecreeper.core.listeners.player.PlayerQuitListener;
import org.bukkit.event.Listener;

public class ListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Listener> listenerBinder = Multibinder.newSetBinder(binder(), Listener.class);
        listenerBinder.addBinding().to(PlayerInteractListener.class);
        listenerBinder.addBinding().to(PlayerJoinListener.class);
        listenerBinder.addBinding().to(PlayerQuitListener.class);
        listenerBinder.addBinding().to(WeatherChangeListener.class);
        listenerBinder.addBinding().to(FoodLevelChangeListener.class);
    }
}
