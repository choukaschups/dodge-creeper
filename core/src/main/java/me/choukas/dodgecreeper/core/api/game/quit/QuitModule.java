package me.choukas.dodgecreeper.core.api.game.quit;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.api.game.quit.QuitManager;

public class QuitModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(QuitManager.class).to(QuitManagerImpl.class);
    }
}
