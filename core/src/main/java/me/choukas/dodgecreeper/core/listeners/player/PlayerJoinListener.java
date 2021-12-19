package me.choukas.dodgecreeper.core.listeners.player;

import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.core.api.Configuration;
import me.choukas.dodgecreeper.core.items.InstanceMenuItem;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;

public class PlayerJoinListener implements Listener {

    private static final int FOOD_LEVEL = 20;

    private final FileConfiguration configuration;
    private final Game game;
    private final BukkitAudiences audiences;
    private final InstanceMenuItem instanceMenuItem;
    private final AutoStartManager autoStartManager;

    @Inject
    public PlayerJoinListener(@Configuration FileConfiguration configuration,
                              Game game,
                              BukkitAudiences audiences,
                              InstanceMenuItem instanceMenuItem,
                              AutoStartManager autoStartManager) {
        this.configuration = configuration;
        this.game = game;
        this.audiences = audiences;
        this.instanceMenuItem = instanceMenuItem;
        this.autoStartManager = autoStartManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // We do not have any control over the player's locale when the PlayerJoinEvent is fired
        // We have to wait around 10 ticks for the server to update it
        Player joiner = event.getPlayer();
        Audience joinerAudience = this.audiences.player(joiner.getUniqueId());

        this.clean(joiner);

        event.setJoinMessage(null);

        if (!this.game.hasStarted() && this.game.getPlayerAmount() == this.configuration.getInt("max-players")) {
            joinerAudience.sendMessage(Component.translatable("game-is-full"));
            joiner.setGameMode(GameMode.SPECTATOR);
            joiner.getInventory().setItem(8, this.instanceMenuItem.asItemStack());

            this.game.addSpectator(joiner);

            return;
        }

        if (this.game.hasStarted()) {
            // The game has already started
            // Adds him to the game as a spectator
            joinerAudience.sendMessage(Component.translatable("game-already-started"));
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
                    Component.translatable("game-connection")
                            .args(
                                    Component.text(joiner.getDisplayName()),
                                    Component.text(this.game.getPlayerAmount()),
                                    Component.text(this.configuration.getInt("max-players"))
                            )
            );
        });

        int remainPlayerAmount = this.configuration.getInt("min-players") - this.game.getPlayerAmount();
        if (remainPlayerAmount > 0) {
            this.game.getPlayers().forEach(player -> {
                Audience audience = this.audiences.player(player);
                audience.sendMessage(
                        Component.translatable("game-remain-amount")
                                .args(Component.text(remainPlayerAmount))
                );
            });
        }
    }

    private void clean(Player player) {
        player.getInventory().clear();

        player.setLevel(0);

        player.setFoodLevel(FOOD_LEVEL);

        player.resetMaxHealth();
        player.setHealth(player.getMaxHealth());
    }
}
