package me.choukas.dodgecreeper.core.listeners.player;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import javax.inject.Inject;
import java.util.UUID;

public class PlayerToggleFlightListener implements Listener {

    private final Game game;

    @Inject
    public PlayerToggleFlightListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        event.setCancelled(true);

        Location playerLocation = player.getLocation();
        Block block = player.getWorld().getBlockAt(playerLocation.subtract(0., 2., 0.));

        if (!block.getType().equals(Material.AIR)) {
            Vector velocity = playerLocation.getDirection().multiply(1).setY(1);
            player.setVelocity(velocity);

            UUID playerId = player.getUniqueId();
            DodgeCreeperPlayer dodgePlayer = this.game.getPlayer(playerId);
            dodgePlayer.consumeDoubleJump();

            if (!dodgePlayer.remainDoubleJumps()) {
                player.setAllowFlight(false);
            }
        }
    }
}
