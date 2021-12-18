package me.choukas.dodgecreeper.api.game;

import org.bukkit.entity.Player;

public interface Game {

    /**
     * Checks if the game has started.
     *
     * @return {@code true} if the game has already started, {@code false} otherwise
     */
    boolean hasStarted();

    /**
     * Adds the specified player to the spectator's list.
     * Among others, sets his {@code GameMode} to {@link org.bukkit.GameMode#SPECTATOR}.
     *
     * @param player The spectator
     */
    void addSpectator(Player player);
}
