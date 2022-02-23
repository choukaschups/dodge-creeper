package me.choukas.dodgecreeper.core.api.game.autostart;

import fr.mrmicky.fastboard.FastBoard;
import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.GameStart;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import me.choukas.dodgecreeper.core.api.translation.TranslationKeys;
import me.choukas.dodgecreeper.core.api.utils.GameConstants;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;

public class AutoStartRunnable extends BukkitRunnable {

    private final Game game;
    private final Configuration configuration;
    private final GameStart gameStart;
    private final AutoStartMessages messages;

    private int timer;

    @Inject
    public AutoStartRunnable(Game game,
                             Configuration configuration,
                             GameStart gameStart,
                             AutoStartMessages messages) {
        this.game = game;
        this.configuration = configuration;
        this.messages = messages;
        this.gameStart = gameStart;

        this.timer = configuration.getMinimumPlayerAmountTimerLevel();
    }

    @Override
    public void run() {
        this.game.getConnected().forEach(player -> {
                    player.setLevel(this.timer);
                    player.setExp((float) this.timer / (float) configuration.getMinimumTimerLevel());
                }
        );

        if (this.timer == 0) {
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
        private final BukkitAudiences audiences;
        private final Translator translator;
        private final Configuration configuration;

        @Inject
        private AutoStartMessages(Game game,
                                  ScoreboardManager scoreboardManager,
                                  BukkitAudiences audiences,
                                  Translator translator,
                                  Configuration configuration) {
            this.game = game;
            this.scoreboardManager = scoreboardManager;
            this.audiences = audiences;
            this.translator = translator;
            this.configuration = configuration;
        }

        public void broadcastTimer(int timer) {
            this.game.getConnected().forEach(player -> {
                FastBoard board = this.scoreboardManager.getScoreboard(player.getUniqueId());
                board.updateLine(0, "");
                board.updateLine(1,
                        this.translator.translate(player,
                                Component.translatable(TranslationKeys.GAME_WAITING_START_TIMER)
                                        .args(
                                                Component.text()
                                                        .color(NamedTextColor.GREEN)
                                                        .append(Component.text(timer))
                                        )
                        )
                );
                board.updateLine(2, "");
                board.updateLine(3,
                        this.translator.translate(player,
                                Component.translatable(TranslationKeys.SERVER_IP))
                );
            });
        }

        public void broadcastTimerTile(int timer) {
            this.game.getConnected().forEach(player -> {
                Audience audience = this.audiences.player(player);

                Title.Times times = Title.Times.of(Ticks.duration(10), Ticks.duration((long) this.configuration.getTimerStayingTime() * GameConstants.TICKS_PER_SECOND), Ticks.duration(20));

                audience.showTitle(
                        Title.title(
                                Component.text()
                                        .color(NamedTextColor.GREEN)
                                        .append(Component.text(timer))
                                        .build(),
                                Component.empty(),
                                times
                        )
                );

                audience.sendMessage(
                        Component.translatable(TranslationKeys.GAME_WAITING_START_TIMER_MESSAGE)
                                .args(
                                        Component.text()
                                                .color(NamedTextColor.GREEN)
                                                .append(Component.text(timer))
                                )
                );

                // TODO Fix this
                audience.playSound(Sound.sound(Key.key("entity.experience_orb.pickup"), Sound.Source.AMBIENT, 10f, 10f));
            });
        }
    }
}
