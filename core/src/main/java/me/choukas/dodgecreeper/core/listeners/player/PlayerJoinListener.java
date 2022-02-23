package me.choukas.dodgecreeper.core.listeners.player;

import me.choukas.dodgecreeper.api.game.join.JoinManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;

public class PlayerJoinListener implements Listener {

    private final JoinManager joinManager;

    @Inject
    public PlayerJoinListener(JoinManager joinManager) {
        this.joinManager = joinManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        this.joinManager.join(player);
    }
}
