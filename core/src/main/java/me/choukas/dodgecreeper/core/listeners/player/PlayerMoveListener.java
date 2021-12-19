package me.choukas.dodgecreeper.core.listeners.player;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.core.Configuration;
import me.choukas.dodgecreeper.core.ConfigurationKeys;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.inject.Inject;

public class PlayerMoveListener implements Listener {

    private final FileConfiguration configuration;
    private final Game game;

    @Inject
    public PlayerMoveListener(@Configuration FileConfiguration configuration, Game game) {
        this.configuration = configuration;
        this.game = game;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getTo().getY() <= configuration.getInt(ConfigurationKeys.HEIGHT_LIMIT)) {
            event.setCancelled(true);

            if (this.game.hasStarted()) {
                // TODO : Death
            }
        }
    }
}
