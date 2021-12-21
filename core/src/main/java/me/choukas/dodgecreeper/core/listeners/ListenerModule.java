package me.choukas.dodgecreeper.core.listeners;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import me.choukas.dodgecreeper.core.listeners.block.BlockBreakListener;
import me.choukas.dodgecreeper.core.listeners.block.BlockPlaceListener;
import me.choukas.dodgecreeper.core.listeners.entity.EntityDamageListener;
import me.choukas.dodgecreeper.core.listeners.entity.EntityDeathListener;
import me.choukas.dodgecreeper.core.listeners.entity.FoodLevelChangeListener;
import me.choukas.dodgecreeper.core.listeners.player.PlayerInteractListener;
import me.choukas.dodgecreeper.core.listeners.player.PlayerJoinListener;
import me.choukas.dodgecreeper.core.listeners.player.PlayerMoveListener;
import me.choukas.dodgecreeper.core.listeners.player.PlayerQuitListener;
import me.choukas.dodgecreeper.core.listeners.weather.ThunderChangeListener;
import me.choukas.dodgecreeper.core.listeners.weather.WeatherChangeListener;
import org.bukkit.event.Listener;

public class ListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Listener> listenerBinder = Multibinder.newSetBinder(binder(), Listener.class);

        listenerBinder.addBinding().to(BlockBreakListener.class);
        listenerBinder.addBinding().to(BlockPlaceListener.class);

        listenerBinder.addBinding().to(PlayerInteractListener.class);
        listenerBinder.addBinding().to(PlayerJoinListener.class);
        listenerBinder.addBinding().to(PlayerMoveListener.class);
        listenerBinder.addBinding().to(PlayerQuitListener.class);

        listenerBinder.addBinding().to(EntityDamageListener.class);
        listenerBinder.addBinding().to(EntityDeathListener.class);
        listenerBinder.addBinding().to(FoodLevelChangeListener.class);

        listenerBinder.addBinding().to(ThunderChangeListener.class);
        listenerBinder.addBinding().to(WeatherChangeListener.class);
    }
}
