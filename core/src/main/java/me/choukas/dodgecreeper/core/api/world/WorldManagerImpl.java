package me.choukas.dodgecreeper.core.api.world;

import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.api.world.WorldManager;
import org.bukkit.World;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class WorldManagerImpl implements WorldManager {

    private static final Map<String, String> GAME_RULES;

    static {
        GAME_RULES = new HashMap<>();
        GAME_RULES.put("doDaylightCycle", String.valueOf(false));
    }

    private final World world;

    @Inject
    public WorldManagerImpl(Configuration configuration) {
        this.world = configuration.getWorld();
    }

    @Override
    public void init() {
        GAME_RULES.forEach(this.world::setGameRuleValue);

        this.world.setStorm(false);
        this.world.setThundering(false);
    }

    @Override
    public World getWorld() {
        return this.world;
    }
}
