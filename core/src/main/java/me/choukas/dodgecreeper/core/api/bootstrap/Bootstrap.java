package me.choukas.dodgecreeper.core.api.bootstrap;

import com.google.inject.CreationException;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

public interface Bootstrap {

    Injector bootstrap(JavaPlugin plugin) throws CreationException;
}
