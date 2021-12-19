package me.choukas.dodgecreeper.core.api.game;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.GameStart;
import me.choukas.dodgecreeper.core.ConfigurationKeys;
import me.choukas.dodgecreeper.core.Messages;
import me.choukas.dodgecreeper.core.api.utils.ConfigurationUtils;
import me.choukas.dodgecreeper.core.items.PusherItem;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;

import javax.inject.Inject;

public class GameStartImpl implements GameStart {

    private final Game game;
    private final BukkitAudiences audiences;
    private final ConfigurationUtils configurationUtils;
    private final PusherItem pusherItem;

    @Inject
    public GameStartImpl(Game game,
                         BukkitAudiences audiences,
                         ConfigurationUtils configurationUtils,
                         PusherItem pusherItem) {
        this.game = game;
        this.audiences = audiences;
        this.configurationUtils = configurationUtils;
        this.pusherItem = pusherItem;
    }

    @Override
    public void start() {
        this.game.getConnected().forEach(player -> {
            Audience audience = this.audiences.player(player);
            audience.sendMessage(Component.translatable(Messages.GAME_START_TITLE));
        });

        Location spawnPoint = this.configurationUtils.getLocation(ConfigurationKeys.GAME_START_SPAWN_POINT);
        this.game.getPlayers().forEach(player -> {
            player.teleport(spawnPoint);
            player.getInventory().setItem(1, this.pusherItem.asItemStack(player));
        });

        this.game.start();
    }
}
