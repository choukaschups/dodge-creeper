package me.choukas.dodgecreeper.api.game;

import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public interface Game {

    boolean hasStarted();

    void start();

    Collection<Player> getConnected();

    void addPlayer(Player player);

    int getPlayerAmount();

    Collection<Player> getPlayers();

    DodgeCreeperPlayer getPlayer(UUID uuid);

    void addSpectator(Player player);

    void remove(UUID uuid);
}
