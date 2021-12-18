package me.choukas.dodgecreeper.api.game;

/**
 * Defines the current state of the game.
 */
public enum GameState {

    /**
     * The game is running. Players can only join the game as spectators.
     */
    RUNNING,

    /**
     * The game is waiting for players. Any player can join the game and play.
     */
    WAITING
}
