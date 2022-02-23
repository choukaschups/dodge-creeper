package me.choukas.dodgecreeper.core.api.game.quit.state;

import org.bukkit.entity.Player;

public interface QuitState {

    void quit(Player leaver);
}
