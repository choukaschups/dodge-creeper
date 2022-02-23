package me.choukas.dodgecreeper.core.api.game.quit.state;

import me.choukas.dodgecreeper.api.game.death.DeathCause;
import me.choukas.dodgecreeper.api.game.death.DeathManager;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class QuitRunningState implements QuitState {

    private final DeathManager deathManager;

    @Inject
    public QuitRunningState(DeathManager deathManager) {
        this.deathManager = deathManager;
    }

    @Override
    public void quit(Player leaver) {
        this.deathManager.death(leaver, DeathCause.LEAVE);
    }
}
