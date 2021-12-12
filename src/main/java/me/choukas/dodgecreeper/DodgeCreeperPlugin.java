package me.choukas.dodgecreeper;

import org.bukkit.plugin.java.JavaPlugin;

public class DodgeCreeperPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("DodgeCreeper enabled successfully");
    }

    @Override
    public void onDisable() {
        getLogger().info("DodgeCreeper disabled successfully");
    }
}
