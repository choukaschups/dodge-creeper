package me.choukas.dodgecreeper.core.listeners.player;

import fr.mrmicky.fastboard.FastBoard;
import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import me.choukas.dodgecreeper.api.player.PlayerType;
import me.choukas.dodgecreeper.core.api.translation.Messages;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final Game game;
    private final AutoStartManager autoStartManager;
    private final ScoreboardManager scoreboardManager;
    private final Configuration configuration;
    private final PlayerQuitListenerMessages messages;

    @Inject
    public PlayerQuitListener(Game game,
                              AutoStartManager autoStartManager,
                              ScoreboardManager scoreboardManager,
                              Configuration configuration,
                              PlayerQuitListenerMessages messages) {
        this.game = game;
        this.autoStartManager = autoStartManager;
        this.configuration = configuration;
        this.messages = messages;
        this.scoreboardManager = scoreboardManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Player leaver = event.getPlayer();
        UUID leaverId = leaver.getUniqueId();
        DodgeCreeperPlayer dodgeLeaver = this.game.getPlayer(leaverId);

        if (dodgeLeaver.getType() == PlayerType.PLAYER) {
            if (this.game.isWaiting()) {
                this.autoStartManager.disconnect();

                if (this.game.getPlayerAmount() == this.configuration.getMinimumPlayerAmount()) {
                    FastBoard board = this.scoreboardManager.getScoreboard(leaverId);
                    board.removeLine(0);
                }
            }
        }

        this.game.remove(leaverId);

        this.scoreboardManager.removeScoreboard(leaverId);

        this.messages.broadcastLeave(leaver);
    }

    private static class PlayerQuitListenerMessages {

        private final Game game;
        private final BukkitAudiences audiences;

        @Inject
        public PlayerQuitListenerMessages(Game game, BukkitAudiences audiences) {
            this.game = game;
            this.audiences = audiences;
        }

        public void broadcastLeave(Player leaver) {
            this.game.getConnected().forEach(player -> {
                Audience audience = this.audiences.player(player);
                audience.sendMessage(
                        Component.translatable(Messages.GAME_WAITING_PLAYER_LEAVE)
                                .args(Component.text(leaver.getDisplayName()))
                );
            });
        }
    }
}
