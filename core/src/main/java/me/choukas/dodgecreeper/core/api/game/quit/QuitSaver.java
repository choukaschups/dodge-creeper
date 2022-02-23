package me.choukas.dodgecreeper.core.api.game.quit;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.UUID;

public class QuitSaver {

    private final Game game;
    private final ScoreboardManager scoreboardManager;

    @Inject
    public QuitSaver(Game game, ScoreboardManager scoreboardManager) {
        this.game = game;
        this.scoreboardManager = scoreboardManager;
    }

    public void save(Player player) {
        UUID leaverId = player.getUniqueId();

        this.game.remove(leaverId);

        this.scoreboardManager.removeScoreboard(leaverId);
    }
}
