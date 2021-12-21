package me.choukas.dodgecreeper.core.api.game.quit.state;

import fr.mrmicky.fastboard.FastBoard;
import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import me.choukas.dodgecreeper.core.api.translation.TranslationKeys;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.UUID;

public class QuitWaitingState implements QuitState {

    private final Game game;
    private final AutoStartManager autoStartManager;
    private final Configuration configuration;
    private final Display display;

    @Inject
    public QuitWaitingState(Game game,
                            AutoStartManager autoStartManager,
                            Configuration configuration,
                            Display display) {
        this.game = game;
        this.autoStartManager = autoStartManager;
        this.configuration = configuration;
        this.display = display;
    }

    @Override
    public void quit(Player leaver) {
        if (this.game.getPlayerAmount() > this.configuration.getMinimumPlayerAmount()) {
            return;
        }

        if (this.game.getPlayerAmount() + 1 == this.configuration.getMinimumPlayerAmount()) {
            // The game cannot start anymore
            this.game.getConnected().forEach(player ->
                    player.setLevel(0)
            );

            this.autoStartManager.stop();

            this.display.scoreboards().updateScoreboard();
            this.display.messages().clearTitle();
        }

        this.display.messages().broadcastRemainPlayerAmount(this.configuration.getMinimumPlayerAmount() - this.game.getPlayerAmount());
    }

    private static class Display {

        private final Messages messages;
        private final Scoreboards scoreboards;

        @Inject
        public Display(Messages messages, Scoreboards scoreboards) {
            this.messages = messages;
            this.scoreboards = scoreboards;
        }

        public Messages messages() {
            return messages;
        }

        public Scoreboards scoreboards() {
            return scoreboards;
        }

        private static class Messages {

            private final Game game;
            private final BukkitAudiences audiences;

            @Inject
            public Messages(Game game, BukkitAudiences audiences) {
                this.game = game;
                this.audiences = audiences;
            }

            public void clearTitle() {
                this.game.getConnected().forEach(player -> {
                    Audience audience = this.audiences.player(player);
                    audience.clearTitle();
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

        private static class Scoreboards {

            private final Game game;
            private final ScoreboardManager scoreboardManager;
            private final Translator translator;

            @Inject
            public Scoreboards(Game game, ScoreboardManager scoreboardManager, Translator translator) {
                this.game = game;
                this.scoreboardManager = scoreboardManager;
                this.translator = translator;
            }

            public void updateScoreboard() {
                this.game.getConnected().forEach(player -> {
                    UUID playerId = player.getUniqueId();

                    FastBoard board = this.scoreboardManager.getScoreboard(playerId);
                    board.removeLine(0);
                    board.removeLine(1);
                    board.removeLine(2);
                    board.removeLine(3);

                    board.updateLine(0, "");
                    board.updateLine(1,
                            this.translator.translate(player,
                                    Component.translatable(TranslationKeys.SERVER_IP)
                            )
                    );
                });
            }
        }
    }
}
