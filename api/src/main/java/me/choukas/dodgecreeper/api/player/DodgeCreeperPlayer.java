package me.choukas.dodgecreeper.api.player;

public interface DodgeCreeperPlayer {

    boolean isSpectator();

    boolean isPlayer();

    void spectate();

    boolean remainDoubleJumps();

    int getRemainingDoubleJumps();

    void consumeDoubleJump();
}
