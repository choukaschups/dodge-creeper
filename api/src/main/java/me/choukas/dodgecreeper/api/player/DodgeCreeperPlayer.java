package me.choukas.dodgecreeper.api.player;

import java.util.UUID;

public interface DodgeCreeperPlayer {

    UUID getUniqueId();

    boolean isSpectator();

    boolean isPlayer();

    void spectate();

    boolean remainDoubleJumps();

    int getRemainingDoubleJumps();

    void consumeDoubleJump();
}
