package me.choukas.dodgecreeper.core.listeners.player;

import fr.mrmicky.fastboard.FastBoard;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;
import me.choukas.dodgecreeper.core.Configuration;
import me.choukas.dodgecreeper.core.ConfigurationKeys;
import me.choukas.dodgecreeper.core.Messages;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import me.choukas.dodgecreeper.core.api.utils.AdventureUtils;
import me.choukas.dodgecreeper.core.api.utils.ConfigurationUtils;
import me.choukas.dodgecreeper.core.items.InstanceMenuItem;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;

public class PlayerJoinListener implements Listener {

    private static final int DEFAULT_FOOD_LEVEL = 20;

    private final FileConfiguration configuration;
    private final Game game;
    private final AutoStartManager autoStartManager;
    private final BukkitAudiences audiences;
    private final InstanceMenuItem instanceMenuItem;
    private final ConfigurationUtils configurationUtils;
    private final ScoreboardManager scoreboardManager;

    @Inject
    public PlayerJoinListener(@Configuration FileConfiguration configuration,
                              Game game,
                              AutoStartManager autoStartManager,
                              BukkitAudiences audiences,
                              InstanceMenuItem instanceMenuItem,
                              ConfigurationUtils configurationUtils,
                              ScoreboardManager scoreboardManager) {
        this.configuration = configuration;
        this.game = game;
        this.autoStartManager = autoStartManager;
        this.audiences = audiences;
        this.instanceMenuItem = instanceMenuItem;
        this.configurationUtils = configurationUtils;
        this.scoreboardManager = scoreboardManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // We do not have any control over the player's locale when the PlayerJoinEvent is fired
        // We have to wait around 10 ticks for the server to update it
        event.setJoinMessage(null);

        Player joiner = event.getPlayer();
        Audience joinerAudience = this.audiences.player(joiner.getUniqueId());

        this.clean(joiner);

        Location spawnLocation = this.configurationUtils.getLocation(ConfigurationKeys.HUB_SPAWN_POINT);
        joiner.teleport(spawnLocation);

        FastBoard board = this.scoreboardManager.addScoreboard(joiner);
        board.updateTitle(AdventureUtils.fromAdventureToVanilla(Component.translatable(Messages.SCOREBOARD_TITLE)));
        board.updateLine(1, AdventureUtils.fromAdventureToVanilla(Component.translatable(Messages.SERVER_IP)));

        if (!this.game.hasStarted() && this.game.getPlayerAmount() == this.configuration.getInt(ConfigurationKeys.MAX_PLAYERS)) {
            joinerAudience.sendMessage(Component.translatable(Messages.GAME_IS_FULL));
            joiner.setGameMode(GameMode.SPECTATOR);
            joiner.getInventory().setItem(8, this.instanceMenuItem.asItemStack());

            this.game.addSpectator(joiner);

            return;
        }

        if (this.game.hasStarted()) {
            // The game has already started
            // Adds him to the game as a spectator
            joinerAudience.sendMessage(Component.translatable(Messages.GAME_ALREADY_STARTED));
            joiner.setGameMode(GameMode.SPECTATOR);
            joiner.getInventory().setItem(8, this.instanceMenuItem.asItemStack());

            this.game.addSpectator(joiner);

            return;
        }

        joiner.setGameMode(GameMode.SURVIVAL);

        this.game.addPlayer(joiner);
        this.autoStartManager.connect();

        this.game.getConnected().forEach(player -> {
            Audience audience = this.audiences.player(player);
            audience.sendMessage(
                    Component.translatable(Messages.GAME_WAITING_PLAYER_JOIN)
                            .args(
                                    Component.text(joiner.getDisplayName()),
                                    Component.text(this.game.getPlayerAmount()),
                                    Component.text(this.configuration.getInt(ConfigurationKeys.MAX_PLAYERS))
                            )
            );
        });

        int remainPlayerAmount = this.configuration.getInt(ConfigurationKeys.MIN_PLAYERS) - this.game.getPlayerAmount();
        if (remainPlayerAmount > 0) {
            this.game.getPlayers().forEach(player -> {
                Audience audience = this.audiences.player(player);
                audience.sendMessage(
                        Component.translatable(Messages.GAME_WAITING_REMAIN_PLAYER_AMOUNT)
                                .args(Component.text(remainPlayerAmount))
                );
            });
        }
    }

    private void clean(Player player) {
        player.getInventory().clear();

        player.setLevel(0);

        player.setFoodLevel(DEFAULT_FOOD_LEVEL);

        player.resetMaxHealth();
        player.setHealth(player.getMaxHealth());
    }
}
