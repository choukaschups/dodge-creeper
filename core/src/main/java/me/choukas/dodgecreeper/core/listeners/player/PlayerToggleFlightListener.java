package me.choukas.dodgecreeper.core.listeners.player;

import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import me.choukas.dodgecreeper.core.api.translation.TranslationKeys;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import javax.inject.Inject;
import java.util.UUID;

public class PlayerToggleFlightListener implements Listener {

    private final Game game;
    private final Messages messages;

    @Inject
    public PlayerToggleFlightListener(Game game, Messages messages) {
        this.game = game;
        this.messages = messages;
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        event.setCancelled(true);

        Location playerLocation = player.getLocation();

        Vector velocity = playerLocation.getDirection().multiply(1).setY(1);
        player.setVelocity(velocity);

        UUID playerId = player.getUniqueId();
        DodgeCreeperPlayer dodgePlayer = this.game.getPlayer(playerId);
        dodgePlayer.consumeDoubleJump();

        if (!dodgePlayer.remainDoubleJumps()) {
            player.setAllowFlight(false);
        } else {
            this.messages.sendRemainingDoubleJumps(player, dodgePlayer.getRemainingDoubleJumps());
        }
    }

    private static class Messages {

        private final BukkitAudiences audiences;
        private final Configuration configuration;

        @Inject
        public Messages(BukkitAudiences audiences, Configuration configuration) {
            this.audiences = audiences;
            this.configuration = configuration;
        }

        public void sendRemainingDoubleJumps(Player player, int remainingDoubleJumps) {
            Audience audience = this.audiences.player(player);

            audience.sendActionBar(
                    Component.translatable(
                            TranslationKeys.REMAINING_DOUBLE_JUMPS_AMOUNT,
                            Component.text()
                                    .color(NamedTextColor.RED)
                                    .append(Component.text(remainingDoubleJumps)),
                            Component.text()
                                    .color(NamedTextColor.YELLOW)
                                    .append(Component.text(this.configuration.getDoubleJumpsAmount()))
                    )
            );
        }
    }
}
