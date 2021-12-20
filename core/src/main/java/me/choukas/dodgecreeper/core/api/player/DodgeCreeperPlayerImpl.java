package me.choukas.dodgecreeper.core.api.player;

import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import me.choukas.dodgecreeper.api.player.PlayerType;

public class DodgeCreeperPlayerImpl implements DodgeCreeperPlayer {

    private PlayerType type;

    public DodgeCreeperPlayerImpl(PlayerType type) {
        this.type = type;
    }

    @Override
    public PlayerType getType() {
        return this.type;
    }

    @Override
    public void spectate() {
        this.type = PlayerType.SPECTATOR;
    }
}
