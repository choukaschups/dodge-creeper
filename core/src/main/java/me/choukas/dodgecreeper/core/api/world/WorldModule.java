package me.choukas.dodgecreeper.core.api.world;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.api.world.WorldManager;

public class WorldModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(WorldManager.class).to(WorldManagerImpl.class);
    }
}
