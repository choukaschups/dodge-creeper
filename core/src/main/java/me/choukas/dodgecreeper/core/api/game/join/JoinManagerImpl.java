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
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.UUID;

public class JoinManagerImpl implements JoinManager {

    private final Game game;
    private final JoinInitializer joinInitializer;
    private final JoinStateProvider joinStateProvider;
    private final Configuration configuration;
    private final Scoreboards scoreboards;

    @Inject
    public JoinManagerImpl(Game game,
                           JoinInitializer joinInitializer,
                           JoinStateProvider joinStateProvider,
                           Configuration configuration,
                           Scoreboards scoreboards) {
        this.game = game;
        this.joinStateProvider = joinStateProvider;
        this.joinInitializer = joinInitializer;
        this.configuration = configuration;
        this.scoreboards = scoreboards;
    }

    @Override
    public void join(Player player) {
        this.joinInitializer.init(player);

        if (this.game.getPlayerAmount() < this.configuration.getMinimumPlayerAmount()) {
            this.scoreboards.displayDefaultScoreboard(player);
        }

        JoinState joinState = this.joinStateProvider.provide(this.game.getState());
        joinState.join(player);
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
