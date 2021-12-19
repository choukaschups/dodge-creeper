package me.choukas.dodgecreeper.core.listeners.player;

import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import me.choukas.dodgecreeper.api.player.PlayerType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final Game game;
    private final AutoStartManager autoStartManager;

    @Inject
    public PlayerQuitListener(Game game, AutoStartManager autoStartManager) {
        this.game = game;
        this.autoStartManager = autoStartManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        DodgeCreeperPlayer dodgeCreeperPlayer = this.game.getPlayer(uuid);

        if (dodgeCreeperPlayer.getType() == PlayerType.PLAYER) {
            if (!this.game.hasStarted()) {
                this.autoStartManager.disconnect();
            }
        }

        this.game.removePlayer(uuid);

        event.setQuitMessage(null);
    }
}
