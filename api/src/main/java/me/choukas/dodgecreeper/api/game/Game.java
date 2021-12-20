package me.choukas.dodgecreeper.api.game;

import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface Game {

    boolean isRunning();

    boolean isWaiting();

    boolean isFinish();

    void start();

    Collection<Player> getConnected();

    void addPlayer(Player player);

    int getPlayerAmount();

    List<Player> getPlayers();

    DodgeCreeperPlayer getPlayer(UUID uuid);

    void addSpectator(Player player);

    void remove(UUID uuid);

    void finish();
}
