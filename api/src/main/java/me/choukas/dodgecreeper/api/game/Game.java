package me.choukas.dodgecreeper.api.game;

import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public interface Game {

    /**
     * Checks if the game has started.
     *
     * @return {@code true} if the game has already started, {@code false} otherwise
     */
    boolean hasStarted();

    DodgeCreeperPlayer getPlayer(UUID uuid);

    void addPlayer(Player player);

    int getPlayerAmount();

    Collection<Player> getPlayers();

    Collection<Player> getConnected();

    /**
     * Adds the specified player to the spectator's list.
     * Among others, sets his {@code GameMode} to {@link org.bukkit.GameMode#SPECTATOR}.
     *
     * @param player The spectator
     */
    void addSpectator(Player player);

    void removePlayer(UUID uuid);
}
