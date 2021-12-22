package me.choukas.dodgecreeper.core.listeners.entity;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.death.DeathCause;
import me.choukas.dodgecreeper.api.game.death.DeathManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.inject.Inject;

public class EntityDamageListener implements Listener {

    private final Game game;
    private final DeathManager deathManager;

    @Inject
    public EntityDamageListener(Game game, DeathManager deathManager) {
        this.game = game;
        this.deathManager = deathManager;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        EntityType entityType = event.getEntityType();

        if (entityType == EntityType.PLAYER) {
            if (!this.game.isRunning()) {
                event.setCancelled(true);

                return;
            }

            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);

                return;
            }

            Player damaged = (Player) event.getEntity();
            if (damaged.getHealth() - event.getDamage() <= 0) {
                this.deathManager.death(damaged, DeathCause.EXPLOSION);

                event.setCancelled(true);
            }
        }
    }
}
