package me.choukas.dodgecreeper.core.api.game;

import me.choukas.dodgecreeper.core.api.utils.GameConstants;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

public class GameRunnableStart {

    private final GameRunnable gameRunnable;
    private final Plugin plugin;

    @Inject
    public GameRunnableStart(GameRunnable gameRunnable, Plugin plugin) {
        this.gameRunnable = gameRunnable;
        this.plugin = plugin;
    }

    public void startGameRunnable() {
        this.gameRunnable.runTaskTimer(plugin, 0, GameConstants.TICKS_PER_SECOND);
    }
}
