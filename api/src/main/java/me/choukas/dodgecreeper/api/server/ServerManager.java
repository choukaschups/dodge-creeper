package me.choukas.dodgecreeper.api.server;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ServerManager {

    CompletableFuture<List<String>> getServers();

    void connect(Player player, String server);
}
