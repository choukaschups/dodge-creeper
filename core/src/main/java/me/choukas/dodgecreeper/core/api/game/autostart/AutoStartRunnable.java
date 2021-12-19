package me.choukas.dodgecreeper.core.api.game.autostart;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.core.Configuration;
import me.choukas.dodgecreeper.core.api.utils.GameConstants;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

import javax.inject.Inject;

public class AutoStartRunnable extends BukkitRunnable {

    private final FileConfiguration configuration;
    private final Game game;

    private int timer;

    @Inject
    public AutoStartRunnable(@Configuration FileConfiguration configuration, Game game) {
        this.configuration = configuration;
        this.game = game;

        this.timer = configuration.getInt("timer.levels.min-players");
    }

    @Override
    public void run() {
        if (timer == 0) {
            this.cancel();

            return;
        }

        this.game.getConnected().forEach(player -> player.setLevel(this.timer));

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

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();

        this.timer = configuration.getInt("timer.levels.min-players");

        this.game.getPlayers().forEach(player -> {
            player.setLevel(0);
            player.resetTitle();
        });
    }

    public void update(int timer) {
        if (this.timer > timer) {
            this.timer = timer;
        }
    }
}
