package me.choukas.dodgecreeper.core.listeners.player;

import me.choukas.dodgecreeper.api.game.quit.QuitManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;

public class PlayerQuitListener implements Listener {

    private final QuitManager quitManager;

    @Inject
    public PlayerQuitListener(QuitManager quitManager) {
        this.quitManager = quitManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Player player = event.getPlayer();
        this.quitManager.quit(player);
    }
}
