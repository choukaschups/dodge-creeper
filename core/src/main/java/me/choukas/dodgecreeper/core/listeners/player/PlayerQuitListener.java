package me.choukas.dodgecreeper.core.listeners.player;

import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import me.choukas.dodgecreeper.api.player.PlayerType;
import me.choukas.dodgecreeper.core.Messages;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final Game game;
    private final AutoStartManager autoStartManager;
    private final BukkitAudiences audiences;

    @Inject
    public PlayerQuitListener(Game game,
                              AutoStartManager autoStartManager,
                              BukkitAudiences audiences) {
        this.game = game;
        this.autoStartManager = autoStartManager;
        this.audiences = audiences;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Player leaver = event.getPlayer();
        UUID uuid = leaver.getUniqueId();
        DodgeCreeperPlayer dodgeLeaver = this.game.getPlayer(uuid);

        if (dodgeLeaver.getType() == PlayerType.PLAYER) {
            if (!this.game.hasStarted()) {
                this.autoStartManager.disconnect();
            }
        }

        this.game.remove(uuid);

        this.game.getConnected().forEach(player -> {
            Audience audience = this.audiences.player(player);
            audience.sendMessage(
                    Component.translatable(Messages.GAME_WAITING_PLAYER_LEAVE)
                            .args(Component.text(leaver.getDisplayName()))
            );
        });
    }
}
