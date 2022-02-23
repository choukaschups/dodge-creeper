package me.choukas.dodgecreeper.core.api.server;

import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;
import me.choukas.dodgecreeper.api.server.ServerManager;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServerManagerImpl implements ServerManager {

    private final BungeeChannelApi channelApi;

    @Inject
    public ServerManagerImpl(BungeeChannelApi channelApi) {
        this.channelApi = channelApi;
    }

    @Override
    public CompletableFuture<List<String>> getServers() {
        return this.channelApi.getServers();
    }

    @Override
    public void connect(Player player, String server) {
        this.channelApi.connect(player, server);
    }
}
