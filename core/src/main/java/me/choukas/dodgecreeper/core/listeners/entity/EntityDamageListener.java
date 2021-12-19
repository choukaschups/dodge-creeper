package me.choukas.dodgecreeper.core.listeners.entity;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.core.ConfigurationKeys;
import me.choukas.dodgecreeper.core.api.utils.ConfigurationUtils;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.inject.Inject;

public class EntityDamageListener implements Listener {

    private final Game game;
    private final ConfigurationUtils configurationUtils;

    @Inject
    public EntityDamageListener(Game game, ConfigurationUtils configurationUtils) {
        this.game = game;
        this.configurationUtils = configurationUtils;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        EntityType entityType = event.getEntityType();

        if (entityType == EntityType.PLAYER) {
            if (!this.game.hasStarted()) {
                event.setCancelled(true);

                return;
            }

            Player damaged = (Player) event.getEntity();
            if (damaged.getHealth() - event.getDamage() <= 0) {
                // TODO : Death
                damaged.setHealth(damaged.getMaxHealth());

                Location deathPoint = this.configurationUtils.getLocation(ConfigurationKeys.DEATH_SPAWN_POINT);
                damaged.teleport(deathPoint);

                event.setCancelled(true);
            }
        }
    }
}
