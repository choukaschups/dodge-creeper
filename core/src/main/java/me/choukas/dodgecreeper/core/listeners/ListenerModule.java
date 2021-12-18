package me.choukas.dodgecreeper.core.listeners;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import org.bukkit.event.Listener;

public class ListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Listener> listenerBinder = Multibinder.newSetBinder(binder(), Listener.class);
        listenerBinder.addBinding().to(PlayerInteractListener.class);
        listenerBinder.addBinding().to(PlayerJoinListener.class);
    }
}
