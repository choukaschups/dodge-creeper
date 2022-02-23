package me.choukas.dodgecreeper.core.api.game.autostart;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;

public class AutoStartModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .build(AutoStartRunnableFactory.class)
        );

        bind(AutoStartManager.class).to(AutoStartManagerImpl.class).asEagerSingleton();
    }
}
