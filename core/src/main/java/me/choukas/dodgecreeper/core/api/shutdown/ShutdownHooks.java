package me.choukas.dodgecreeper.core.api.shutdown;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;

import javax.inject.Inject;

public class ShutdownHooks {

    private final BukkitAudiences audiences;

    @Inject
    public ShutdownHooks(BukkitAudiences audiences) {
        this.audiences = audiences;
    }

    public void shutdown() {
        this.audiences.close();
    }
}
