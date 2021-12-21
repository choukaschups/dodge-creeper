package me.choukas.dodgecreeper.core.api.translation;

import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public enum TranslationKeys implements Translatable {

    GAME_ALREADY_STARTED("game-already-started"),
    GAME_IS_FULL("game-is-full"),
    GAME_WAITING_REMAIN_PLAYER_AMOUNT("game-waiting-remain-player-amount"),
    GAME_WAITING_PLAYER_JOIN("game-waiting-player-join"),
    GAME_WAITING_PLAYER_LEAVE("game-waiting-player-leave"),
    GAME_WAITING_START_TIMER("game-waiting-start-timer"),
    GAME_START_TITLE("game-start-title"),
    INSTANCE_MENU_ITEM_NAME("instance-menu-item-name"),
    INSTANCE_MENU_TITLE("instance-menu-title"),
    PUSHER_NAME("pusher-name"),
    SCOREBOARD_TITLE("scoreboard-title"),
    SERVER_IP("server-ip"),
    REMAINING_PLAYER_AMOUNT("remaining-player-amount"),
    DEATH_TITLE("death-title"),
    DEATH_BROADCAST("death-broadcast"),
    WIN("win"),
    GAME_WAITING_START_TIMER_MESSAGE("game-waiting-start-timer-message");

    private final String key;

    TranslationKeys(String key) {
        this.key = key;
    }

    @Override
    public @NotNull String translationKey() {
        return this.key;
    }
}
