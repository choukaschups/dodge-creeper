package me.choukas.dodgecreeper.api.server;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ServerManager {

    /**
     * Gets a list of the servers running on the proxy.
     *
     * @return A list of the servers running on the proxy.
     */
    CompletableFuture<List<String>> getServers();

    /**
     * Connects the specified player to the specified server.
     *
     * @param player The player
     * @param server The server's name
     */
    void connect(Player player, String server);
}
