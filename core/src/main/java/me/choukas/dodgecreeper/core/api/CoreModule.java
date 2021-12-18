package me.choukas.dodgecreeper.core.api;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import me.choukas.dodgecreeper.core.api.game.GameModule;
import me.choukas.dodgecreeper.core.api.server.ServerModule;
import me.choukas.dodgecreeper.core.api.translation.TranslationModule;
import me.choukas.dodgecreeper.core.api.item.ItemModule;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;

public class CoreModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new GameModule());
        install(new ItemModule());
        install(new ServerModule());
        install(new TranslationModule());
    }

    @Provides
    public BukkitAudiences provideAdventure(Plugin plugin) {
        return BukkitAudiences.create(plugin);
    }
}
