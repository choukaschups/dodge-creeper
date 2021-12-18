package me.choukas.dodgecreeper.core.listeners;

import me.choukas.dodgecreeper.api.game.Game;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;

public class PlayerJoinListener implements Listener {

    private final BukkitAudiences audiences;
    private final Game game;

    @Inject
    public PlayerJoinListener(BukkitAudiences audiences, Game game) {
        this.audiences = audiences;
        this.game = game;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        // We do not have any control over the player's locale when the PlayerJoinEvent is fired
        // We have to wait around 10 ticks for the server to update it
        Player player = event.getPlayer();
        Audience audience = this.audiences.player(player.getUniqueId());

        if (this.game.hasStarted()) {
            // The game has already started
            // Adds him to the game as a spectator
            audience.sendMessage(Component.translatable("game-already-started"));
            this.game.addSpectator(player);
        }
    }
}
