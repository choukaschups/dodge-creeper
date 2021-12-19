package me.choukas.dodgecreeper.core.api.world;

import me.choukas.dodgecreeper.api.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldManagerImpl implements WorldManager {

    public void init() {
        World world = Bukkit.getWorld("world");

        world.setGameRuleValue("doDaylightCycle", "false");

        world.setStorm(false);
        world.setThundering(false);
    }
}
