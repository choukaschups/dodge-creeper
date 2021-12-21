package me.choukas.dodgecreeper.core.api.game.quit;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.quit.QuitManager;
import me.choukas.dodgecreeper.core.api.game.quit.state.QuitState;
import me.choukas.dodgecreeper.core.api.game.quit.state.QuitStateProvider;
import me.choukas.dodgecreeper.core.api.translation.TranslationKeys;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class QuitManagerImpl implements QuitManager {

    private final Game game;
    private final QuitSaver quitSaver;
    private final QuitStateProvider quitStateProvider;
    private final Messages messages;

    @Inject
    public QuitManagerImpl(Game game,
                           QuitSaver quitSaver,
                           QuitStateProvider quitStateProvider,
                           Messages messages) {
        this.game = game;
        this.quitSaver = quitSaver;
        this.quitStateProvider = quitStateProvider;
        this.messages = messages;
    }

    @Override
    public void quit(Player player) {
        this.quitSaver.save(player);
        this.messages.broadcastLeave(player);

        /*if (dodgeLeaver.isSpectator()) {
            return;
        }*/

        QuitState quitState = this.quitStateProvider.provide(this.game.getState());
        quitState.quit(player);
    }

    private static class Messages {

        private final Game game;
        private final BukkitAudiences audiences;

        @Inject
        public Messages(Game game, BukkitAudiences audiences) {
            this.game = game;
            this.audiences = audiences;
        }

        public void broadcastLeave(Player leaver) {
            this.game.getConnected().forEach(player -> {
                Audience audience = this.audiences.player(player);
                audience.sendMessage(
                        Component.translatable(TranslationKeys.GAME_WAITING_PLAYER_LEAVE)
                                .args(Component.text()
                                        .color(NamedTextColor.YELLOW)
                                        .append(Component.text(leaver.getDisplayName()))
                                )
                );
            });
        }
    }
}
