package me.choukas.dodgecreeper.core.api.server;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;
import me.choukas.dodgecreeper.api.server.ServerManager;
import org.bukkit.plugin.Plugin;

public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ServerManager.class).to(ServerManagerImpl.class);
    }

    @Provides
    public BungeeChannelApi provideBungeeChannelApi(Plugin plugin) {
        return new BungeeChannelApi(plugin);
    }
}
