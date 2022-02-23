package me.choukas.dodgecreeper.core.api.game.join.state;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.spectate.SpectateManager;
import me.choukas.dodgecreeper.core.api.translation.TranslationKeys;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class JoinRunningState implements JoinState {

    private final Game game;
    private final SpectateManager spectateManager;
    private final Messages messages;

    @Inject
    public JoinRunningState(Game game,
                            SpectateManager spectateManager,
                            Messages messages) {
        this.game = game;
        this.spectateManager = spectateManager;
        this.messages = messages;
    }

    @Override
    public void join(Player player) {
        this.messages.warnGameHasStarted(player);
        this.spectateManager.spectate(player);
        this.game.addSpectator(player);
    }

    private static class Messages {

        private final BukkitAudiences audiences;

        @Inject
        public Messages(BukkitAudiences audiences) {
            this.audiences = audiences;
        }

        public void warnGameHasStarted(Player joiner) {
            Audience joinerAudience = this.audiences.player(joiner);
            joinerAudience.sendMessage(Component.translatable(TranslationKeys.GAME_ALREADY_STARTED));
        }
    }
}
