package me.choukas.dodgecreeper.core.api.game.join.state;

import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;
import me.choukas.dodgecreeper.api.game.spectate.SpectateManager;
import me.choukas.dodgecreeper.core.api.translation.TranslationKeys;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class JoinWaitingState implements JoinState {

    private final Game game;
    private final AutoStartManager autoStartManager;
    private final SpectateManager spectateManager;
    private final Configuration configuration;
    private final Messages messages;

    @Inject
    public JoinWaitingState(Game game,
                            AutoStartManager autoStartManager,
                            SpectateManager spectateManager,
                            Configuration configuration,
                            Messages messages) {
        this.game = game;
        this.autoStartManager = autoStartManager;
        this.spectateManager = spectateManager;
        this.configuration = configuration;
        this.messages = messages;
    }

    @Override
    public void join(Player player) {
        if (this.game.getPlayerAmount() == this.configuration.getMaximumPlayerAmount()) {
            // The game is full
            // Add him to the game as spectator
            this.messages.warnGameIsFull(player);
            this.spectateManager.spectate(player);
            this.game.addSpectator(player);

            return;
        }

        player.setGameMode(GameMode.SURVIVAL);

        this.game.addPlayer(player, this.configuration.getDoubleJumpsAmount());

        this.messages.broadcastJoin(player);

        if (this.game.getPlayerAmount() < this.configuration.getMinimumPlayerAmount()) {
            int remainPlayerAmount = this.configuration.getMinimumPlayerAmount() - this.game.getPlayerAmount();
            this.messages.broadcastRemainPlayerAmount(remainPlayerAmount);

            return;
        }

        this.autoStartManager.update();
    }

    private static class Messages {

        private final Game game;
        private final BukkitAudiences audiences;
        private final Configuration configuration;

        @Inject
        public Messages(Game game, BukkitAudiences audiences, Configuration configuration) {
            this.game = game;
            this.audiences = audiences;
            this.configuration = configuration;
        }

        public void warnGameIsFull(Player joiner) {
            Audience joinerAudience = this.audiences.player(joiner);
            joinerAudience.sendMessage(Component.translatable(TranslationKeys.GAME_IS_FULL));
        }

        public void broadcastJoin(Player joiner) {
            this.game.getConnected().forEach(player -> {
                Audience audience = this.audiences.player(player);
                audience.sendMessage(
                        Component.translatable(TranslationKeys.GAME_WAITING_PLAYER_JOIN)
                                .args(
                                        Component.text()
                                                .color(NamedTextColor.YELLOW)
                                                .append(Component.text(joiner.getDisplayName())),
                                        Component.text()
                                                .color(NamedTextColor.YELLOW)
                                                .append(Component.text(this.game.getPlayerAmount())),
                                        Component.text()
                                                .color(NamedTextColor.YELLOW)
                                                .append(Component.text(this.configuration.getMaximumPlayerAmount()))
                                )
                );
            });
        }

        public void broadcastRemainPlayerAmount(int remainPlayerAmount) {
            this.game.getPlayers().forEach(player -> {
                Audience audience = this.audiences.player(player);
                audience.sendActionBar(
                        Component.translatable(TranslationKeys.GAME_WAITING_REMAIN_PLAYER_AMOUNT)
                                .args(
                                        Component.text()
                                                .color(NamedTextColor.RED)
                                                .append(Component.text(remainPlayerAmount))
                                )
                );
            });
        }
    }
}
