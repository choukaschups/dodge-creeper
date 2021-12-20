package me.choukas.dodgecreeper.core.api.game.autostart;

import fr.mrmicky.fastboard.FastBoard;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.GameStart;
import me.choukas.dodgecreeper.core.Configuration;
import me.choukas.dodgecreeper.core.Messages;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import me.choukas.dodgecreeper.core.api.utils.AdventureUtils;
import me.choukas.dodgecreeper.core.api.utils.GameConstants;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

import javax.inject.Inject;

public class AutoStartRunnable extends BukkitRunnable {

    private final FileConfiguration configuration;
    private final Game game;
    private final ScoreboardManager scoreboardManager;
    private final GameStart gameStart;

    private int timer;

    @Inject
    public AutoStartRunnable(@Configuration FileConfiguration configuration,
                             Game game,
                             ScoreboardManager scoreboardManager,
                             GameStart gameStart) {
        this.configuration = configuration;
        this.game = game;
        this.scoreboardManager = scoreboardManager;
        this.gameStart = gameStart;

        this.timer = configuration.getInt("timer.levels.min-players");
    }

    @Override
    public void run() {
        if (timer == 0) {
            this.cancel();

            this.gameStart.start();

            return;
        }

        this.game.getConnected().forEach(player -> {
            player.setLevel(this.timer);

            FastBoard board = this.scoreboardManager.getScoreboard(player.getUniqueId());
            board.updateLine(0,
                    AdventureUtils.fromAdventureToVanilla(
                            Component.translatable(Messages.GAME_WAITING_START_TIMER)
                                    .args(Component.text(this.timer))
                    )
            );
        });

        if (this.configuration.getIntegerList("timer.print").contains(this.timer)) {
            this.game.getConnected().forEach(player ->
                    player.sendTitle(
                            Title.builder()
                                    .title(String.valueOf(this.timer))
                                    .stay(this.configuration.getInt("timer.time") * GameConstants.TICKS_PER_SECOND)
                                    .build()
                    )
            );
        }

        this.timer--;
    }

    public void update(int timer) {
        if (this.timer > timer) {
            this.timer = timer;
        }
    }
}
