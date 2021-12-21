package me.choukas.dodgecreeper.core.api.game.join;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.api.game.join.JoinManager;

public class JoinModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JoinManager.class).to(JoinManagerImpl.class);
    }
}
