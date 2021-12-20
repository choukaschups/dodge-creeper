package me.choukas.dodgecreeper.core.api.game.spectate;

import me.choukas.dodgecreeper.api.game.spectate.SpectateManager;
import me.choukas.dodgecreeper.core.items.InstanceMenuItem;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class SpectateManagerImpl implements SpectateManager {

    private final InstanceMenuItem instanceMenuItem;

    @Inject
    public SpectateManagerImpl(InstanceMenuItem instanceMenuItem) {
        this.instanceMenuItem = instanceMenuItem;
    }

    @Override
    public void spectate(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        player.getInventory().setItem(8, this.instanceMenuItem.asItemStack(player));
    }
}
