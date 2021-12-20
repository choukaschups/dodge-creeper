package me.choukas.dodgecreeper.core.api.game.autostart;

import fr.mrmicky.fastboard.FastBoard;
import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.GameStart;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.api.translation.Messages;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import me.choukas.dodgecreeper.core.api.utils.GameConstants;
import net.kyori.adventure.text.Component;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

import javax.inject.Inject;

public class AutoStartRunnable extends BukkitRunnable {

    private final Configuration configuration;
    private final GameStart gameStart;
    private final AutoStartMessages messages;

    private int timer;

    @Inject
    public AutoStartRunnable(Configuration configuration,
                             GameStart gameStart,
                             AutoStartMessages messages) {
        this.configuration = configuration;
        this.messages = messages;
        this.gameStart = gameStart;

        this.timer = configuration.getMinimumPlayerAmountTimerLevel();
    }

    @Override
    public void run() {
        if (timer == 0) {
            this.cancel();

            this.gameStart.start();

            return;
        }

        this.messages.broadcastTimer(timer);

        if (this.configuration.shouldPrintTimer(timer)) {
            this.messages.broadcastTimerTile(timer);
        }

        this.timer--;
    }

    public void update(int timer) {
        if (this.timer > timer) {
            this.timer = timer;
        }
    }

    private static class AutoStartMessages {

        private final Game game;
        private final ScoreboardManager scoreboardManager;
        private final Translator translator;
        private final Configuration configuration;

        @Inject
        private AutoStartMessages(Game game, ScoreboardManager scoreboardManager, Translator translator, Configuration configuration) {
            this.game = game;
            this.scoreboardManager = scoreboardManager;
            this.translator = translator;
            this.configuration = configuration;
        }

        public void broadcastTimer(int timer) {
            this.game.getConnected().forEach(player -> {
                player.setLevel(timer);

                FastBoard board = this.scoreboardManager.getScoreboard(player.getUniqueId());
                board.updateLine(0,
                        this.translator.translate(player,
                                Component.translatable(Messages.GAME_WAITING_START_TIMER)
                                        .args(Component.text(timer))
                        )
                );
            });
        }

        public void broadcastTimerTile(int timer) {
            this.game.getConnected().forEach(player ->
                    player.sendTitle(
                            Title.builder()
                                    .title(String.valueOf(timer))
                                    .stay(this.configuration.getTimerStayingTime() * GameConstants.TICKS_PER_SECOND)
                                    .build()
                    )
            );
        }
    }
}
