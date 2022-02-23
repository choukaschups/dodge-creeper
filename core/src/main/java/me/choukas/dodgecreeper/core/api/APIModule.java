package me.choukas.dodgecreeper.core.api;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationModule;
import me.choukas.dodgecreeper.core.api.game.GameModule;
import me.choukas.dodgecreeper.core.api.listener.ListenerModule;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardModule;
import me.choukas.dodgecreeper.core.api.server.ServerModule;
import me.choukas.dodgecreeper.core.api.translation.TranslationModule;
import me.choukas.dodgecreeper.core.api.item.ItemModule;
import me.choukas.dodgecreeper.core.api.world.WorldModule;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;

public class APIModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ConfigurationModule());
        install(new GameModule());
        install(new ItemModule());
        install(new ListenerModule());
        install(new ScoreboardModule());
        install(new ServerModule());
        install(new TranslationModule());
        install(new WorldModule());
    }

    @Provides
    public BukkitAudiences provideAdventure(Plugin plugin) {
        return BukkitAudiences.create(plugin);
    }
}
