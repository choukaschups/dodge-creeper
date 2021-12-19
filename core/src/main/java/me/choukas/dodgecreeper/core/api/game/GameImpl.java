package me.choukas.dodgecreeper.core.api.game;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.GameState;
import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import me.choukas.dodgecreeper.api.player.PlayerType;
import me.choukas.dodgecreeper.core.api.player.DodgeCreeperPlayerImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

public class GameImpl implements Game {

    private GameState state;

    private final Map<UUID, DodgeCreeperPlayer> players;

    @Inject
    public GameImpl() {
        this.state = GameState.WAITING;

        this.players = new HashMap<>();
    }

    @Override
    public boolean hasStarted() {
        return this.state == GameState.RUNNING;
    }

    @Override
    public void start() {
        this.state = GameState.RUNNING;
    }

    @Override
    public DodgeCreeperPlayer getPlayer(UUID uuid) {
        return this.players.get(uuid);
    }

    @Override
    public void addPlayer(Player player) {
        this.players.put(player.getUniqueId(), new DodgeCreeperPlayerImpl(PlayerType.PLAYER));
    }

    @Override
    public int getPlayerAmount() {
        return (int) this.players.values().stream()
                .filter(player -> player.getType() == PlayerType.PLAYER)
                .count();
    }

    @Override
    public Collection<Player> getPlayers() {
        return this.players.keySet().stream()
                .map(Bukkit::getPlayer)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Player> getConnected() {
        return this.players.keySet().stream()
                .map(Bukkit::getPlayer)
                .collect(Collectors.toList());
    }

    @Override
    public void addSpectator(Player player) {
        this.players.put(player.getUniqueId(), new DodgeCreeperPlayerImpl(PlayerType.SPECTATOR));
    }

    @Override
    public void remove(UUID uuid) {
        this.players.remove(uuid);
    }
}
