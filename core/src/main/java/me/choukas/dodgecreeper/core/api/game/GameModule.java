package me.choukas.dodgecreeper.core.api.game;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.api.game.Game;

public class GameModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Game.class).to(GameImpl.class).asEagerSingleton();
    }
}
