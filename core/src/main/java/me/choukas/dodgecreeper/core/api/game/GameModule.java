package me.choukas.dodgecreeper.core.api.game;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.api.game.GameStart;
import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.core.api.game.autostart.AutoStartManagerImpl;
import me.choukas.dodgecreeper.core.api.game.autostart.AutoStartRunnable;

public class GameModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AutoStartManager.class).to(AutoStartManagerImpl.class);
        bind(AutoStartRunnable.class).asEagerSingleton();

        bind(Game.class).to(GameImpl.class).asEagerSingleton();
        bind(GameStart.class).to(GameStartImpl.class);
    }
}
