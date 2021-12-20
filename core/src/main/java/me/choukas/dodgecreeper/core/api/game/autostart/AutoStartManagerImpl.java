package me.choukas.dodgecreeper.core.api.game.autostart;

import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

public class AutoStartManagerImpl implements AutoStartManager {

    private static final int AUTO_START_TASK_PERIOD = 20;

    private final Game game;
    private final Configuration configuration;
    private final Plugin plugin;
    private final AutoStartRunnableFactory autoStartRunnableFactory;

    private AutoStartRunnable autoStartRunnable;

    @Inject
    public AutoStartManagerImpl(Configuration configuration,
                                Game game,
                                Plugin plugin,
                                AutoStartRunnableFactory autoStartRunnableFactory) {
        this.configuration = configuration;
        this.game = game;
        this.plugin = plugin;
        this.autoStartRunnableFactory = autoStartRunnableFactory;
    }

    @Override
    public void connect() {
        if (this.game.getPlayerAmount() == this.configuration.getMinimumPlayerAmount()) {
            this.autoStartRunnable = this.autoStartRunnableFactory.createAutoStartRunnable();
            this.autoStartRunnable.runTaskTimer(this.plugin, 0, AUTO_START_TASK_PERIOD);

            return;
        }

        int playerAmount = this.game.getPlayerAmount();

        this.configuration.getTimerLevel(playerAmount).ifPresent(timerLevel ->
                this.autoStartRunnable.update(timerLevel)
        );
    }

    @Override
    public void disconnect() {
        if (this.game.getPlayerAmount() == this.configuration.getMinimumPlayerAmount()) {
            this.autoStartRunnable.cancel();

            this.game.getPlayers().forEach(player -> {
                player.setLevel(0);
                player.resetTitle();

                // TODO Reset scoreboard
            });
        }
    }
}
