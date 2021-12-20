package me.choukas.dodgecreeper.core.api.scoreboard;

import com.google.inject.AbstractModule;

public class ScoreboardModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ScoreboardManager.class).asEagerSingleton();
    }
}
