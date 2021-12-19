package me.choukas.dodgecreeper.core.api.listener;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.api.listener.ListenerRegisterer;

public class ListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ListenerRegisterer.class).to(ListenerRegistererImpl.class);
    }
}
