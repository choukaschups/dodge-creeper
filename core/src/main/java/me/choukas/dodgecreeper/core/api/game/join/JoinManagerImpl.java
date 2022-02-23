package me.choukas.dodgecreeper.core.api.game.join;

import fr.mrmicky.fastboard.FastBoard;
import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.join.JoinManager;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.api.game.join.state.JoinState;
import me.choukas.dodgecreeper.core.api.game.join.state.JoinStateProvider;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import me.choukas.dodgecreeper.core.api.translation.TranslationKeys;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.UUID;

public class JoinManagerImpl implements JoinManager {

    private final Game game;
    private final JoinInitializer joinInitializer;
    private final JoinStateProvider joinStateProvider;
    private final Configuration configuration;
    private final Display display;

    @Inject
    public JoinManagerImpl(Game game,
                           JoinInitializer joinInitializer,
                           JoinStateProvider joinStateProvider,
                           Configuration configuration,
                           Display display) {
        this.game = game;
        this.joinStateProvider = joinStateProvider;
        this.joinInitializer = joinInitializer;
        this.configuration = configuration;
        this.display = display;
    }

    @Override
    public void join(Player player) {
        this.joinInitializer.init(player);

        if (this.game.getPlayerAmount() < this.configuration.getMinimumPlayerAmount()) {
            this.display.scoreboards().displayDefaultScoreboard(player);
        }

        this.display.messages().displayTabList(player);

        JoinState joinState = this.joinStateProvider.provide(this.game.getState());
        joinState.join(player);
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
    }

    private static class Messages {

        private final BukkitAudiences audiences;

        @Inject
        public Messages(BukkitAudiences audiences) {
            this.audiences = audiences;
        }

        public void displayTabList(Player player) {
            Audience audience = this.audiences.player(player);
            audience.sendPlayerListHeaderAndFooter(
                    Component.translatable(TranslationKeys.TAB_LIST_HEADER),
                    Component.translatable(TranslationKeys.TAB_LIST_FOOTER)
            );
        }
    }

    private static class Scoreboards {

        private final ScoreboardManager scoreboardManager;
        private final Translator translator;

        @Inject
        public Scoreboards(ScoreboardManager scoreboardManager, Translator translator) {
            this.scoreboardManager = scoreboardManager;
            this.translator = translator;
        }

        public void displayDefaultScoreboard(Player player) {
            UUID joinerId = player.getUniqueId();

            FastBoard board = this.scoreboardManager.getScoreboard(joinerId);
            board.updateTitle(
                    this.translator.translate(player,
                            Component.translatable(TranslationKeys.SCOREBOARD_TITLE)
                    )
            );

            board.updateLine(1,
                    this.translator.translate(player,
                            Component.translatable(TranslationKeys.SERVER_IP)
                    )
            );
        }
    }
}
