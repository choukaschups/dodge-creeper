package me.choukas.dodgecreeper.core;

import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public enum Messages implements Translatable {

    GAME_ALREADY_STARTED("game-already-started"),
    GAME_IS_FULL("game-is-full"),
    GAME_WAITING_REMAIN_PLAYER_AMOUNT("game-waiting-remain-player-amount"),
    GAME_WAITING_PLAYER_JOIN("game-waiting-player-join"),
    GAME_WAITING_PLAYER_LEAVE("game-waiting-player-leave"),
    GAME_START_TITLE("game-start-tile"),
    INSTANCE_MENU_ITEM_NAME("instance-menu-item-name"),
    INSTANCE_MENU_TITLE("instance-menu-title"),
    PUSHER_NAME("pusher-name");

    private final String key;

    Messages(String key) {
        this.key = key;
    }

    @Override
    public @NotNull String translationKey() {
        return this.key;
    }
}
