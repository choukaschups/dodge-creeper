package me.choukas.dodgecreeper.core.listeners.player;

import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.death.DeathCause;
import me.choukas.dodgecreeper.api.game.death.DeathManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class PlayerMoveListener implements Listener {

    private final Game game;
    private final DeathManager deathManager;
    private final Configuration configuration;

    @Inject
    public PlayerMoveListener(Game game,
                              DeathManager deathManager,
                              Configuration configuration) {
        this.game = game;
        this.deathManager = deathManager;
        this.configuration = configuration;
    }

    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent event) {
        if (event.getTo().getY() <= this.configuration.getHeightLimit()) {
            event.setCancelled(true);

            if (this.game.isRunning()) {
                this.deathManager.death(event.getPlayer(), DeathCause.FALL);
            }
        }
    }
}
