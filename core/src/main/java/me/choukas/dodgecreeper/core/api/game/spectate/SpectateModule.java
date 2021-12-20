package me.choukas.dodgecreeper.core.api.game.spectate;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.api.game.spectate.SpectateManager;

public class SpectateModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SpectateManager.class).to(SpectateManagerImpl.class);
    }
}
