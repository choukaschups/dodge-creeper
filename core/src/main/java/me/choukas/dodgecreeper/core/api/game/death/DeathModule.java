package me.choukas.dodgecreeper.core.api.game.death;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.api.game.death.DeathManager;

public class DeathModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DeathManager.class).to(DeathManagerImpl.class);
    }
}
