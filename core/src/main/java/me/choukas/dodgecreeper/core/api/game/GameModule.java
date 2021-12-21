package me.choukas.dodgecreeper.core.api.game;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.GameStart;
import me.choukas.dodgecreeper.core.api.game.autostart.AutoStartModule;
import me.choukas.dodgecreeper.core.api.game.death.DeathModule;
import me.choukas.dodgecreeper.core.api.game.join.JoinModule;
import me.choukas.dodgecreeper.core.api.game.quit.QuitModule;
import me.choukas.dodgecreeper.core.api.game.spectate.SpectateModule;

public class GameModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new AutoStartModule());
        install(new DeathModule());
        install(new JoinModule());
        install(new QuitModule());
        install(new SpectateModule());

        bind(Game.class).to(GameImpl.class).asEagerSingleton();
        bind(GameRunnable.class).asEagerSingleton();
        bind(GameStart.class).to(GameStartImpl.class);
    }
}
