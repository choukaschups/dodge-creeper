package me.choukas.dodgecreeper.core.api.player;

import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import me.choukas.dodgecreeper.api.player.PlayerType;

public class DodgeCreeperPlayerImpl implements DodgeCreeperPlayer {

    private PlayerType type;
    private int doubleJumps;

    public DodgeCreeperPlayerImpl(PlayerType type, int doubleJumps) {
        this.type = type;
        this.doubleJumps = doubleJumps;
    }

    @Override
    public boolean isSpectator() {
        return this.type == PlayerType.SPECTATOR;
    }

    @Override
    public boolean isPlayer() {
        return this.type == PlayerType.PLAYER;
    }

    @Override
    public void spectate() {
        this.type = PlayerType.SPECTATOR;
    }

    @Override
    public boolean remainDoubleJumps() {
        return this.doubleJumps > 0;
    }

    @Override
    public int getRemainingDoubleJumps() {
        return this.doubleJumps;
    }

    @Override
    public void consumeDoubleJump() {
        this.doubleJumps--;
    }
}
