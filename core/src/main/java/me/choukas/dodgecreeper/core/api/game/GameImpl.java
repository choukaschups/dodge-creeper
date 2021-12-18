package me.choukas.dodgecreeper.core.api.game;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.GameState;
import me.choukas.dodgecreeper.core.items.InstanceMenuItem;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class GameImpl implements Game {

    private final InstanceMenuItem instanceSwitcherItem;

    private GameState state;

    private final Collection<UUID> players;
    private final Collection<UUID> spectators;

    @Inject
    public GameImpl(InstanceMenuItem instanceSwitcherItem) {
        this.instanceSwitcherItem = instanceSwitcherItem;

        this.state = GameState.WAITING;

        this.players = new ArrayList<>();
        this.spectators = new ArrayList<>();
    }

    @Override
    public boolean hasStarted() {
        return this.state == GameState.RUNNING;
    }

    @Override
    public void addSpectator(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        player.getInventory().setItem(8, this.instanceSwitcherItem.asItemStack());

        this.spectators.add(player.getUniqueId());
    }
}
