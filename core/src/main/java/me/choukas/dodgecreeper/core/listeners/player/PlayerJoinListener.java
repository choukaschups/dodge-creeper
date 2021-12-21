package me.choukas.dodgecreeper.core.listeners.player;

import fr.mrmicky.fastboard.FastBoard;
import me.choukas.dodgecreeper.api.configuration.Configuration;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;
import me.choukas.dodgecreeper.api.game.spectate.SpectateManager;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.bukkit.BukkitConfiguration;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationImpl;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationKeys;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationLocationProvider;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import me.choukas.dodgecreeper.core.api.translation.Messages;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;

public class PlayerJoinListener implements Listener {

    private static final int DEFAULT_FOOD_LEVEL = 20;

    private final Game game;
    private final AutoStartManager autoStartManager;
    private final SpectateManager spectateManager;
    private final PlayerJoinListenerConfiguration configuration;
    private final PlayerJoinListenerMessages messages;

    @Inject
    public PlayerJoinListener(Game game,
                              AutoStartManager autoStartManager,
                              SpectateManager spectateManager,
                              PlayerJoinListenerConfiguration configuration,
                              PlayerJoinListenerMessages messages) {
        this.game = game;
        this.autoStartManager = autoStartManager;
        this.spectateManager = spectateManager;
        this.configuration = configuration;
        this.messages = messages;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // We do not have any control over the player's locale when the PlayerJoinEvent is fired
        // We have to wait around 10 ticks for the server to update it, but fuck it, we'll display all in French
        event.setJoinMessage(null);

        Player joiner = event.getPlayer();

        this.clean(joiner);

        Location hubSpawnLocation = this.configuration.getHubSpawnLocation();
        joiner.teleport(hubSpawnLocation);

        this.messages.printScoreboard(joiner);

        if (this.game.isWaiting() && this.game.getPlayerAmount() == this.configuration.getMinimumPlayerAmount()) {
            this.messages.warnGameIsFull(joiner);
            this.spectate(joiner);

            return;
        }

        if (this.game.isRunning()) {
            // The game has already started
            // Adds him to the game as a spectator
            this.messages.warnGameHasStarted(joiner);
            this.spectate(joiner);

            return;
        }

        joiner.setGameMode(GameMode.SURVIVAL);

        this.game.addPlayer(joiner);

        this.autoStartManager.connect();

        this.messages.broadcastJoin(joiner);

        int remainPlayerAmount = this.configuration.getMinimumPlayerAmount() - this.game.getPlayerAmount();
        if (remainPlayerAmount > 0) {
            this.messages.broadcastRemainPlayerAmount(remainPlayerAmount);
        }
    }

    private void clean(Player player) {
        player.getInventory().clear();

        player.setLevel(0);

        player.setFoodLevel(DEFAULT_FOOD_LEVEL);

        player.resetMaxHealth();
        player.setHealth(player.getMaxHealth());
    }

    private void spectate(Player player) {
        this.spectateManager.spectate(player);
        this.game.addSpectator(player);
    }

    private static class PlayerJoinListenerConfiguration extends ConfigurationImpl {

        private final ConfigurationLocationProvider configurationLocationProvider;

        @Inject
        public PlayerJoinListenerConfiguration(@BukkitConfiguration FileConfiguration configuration, ConfigurationLocationProvider configurationLocationProvider) {
            super(configuration);

            this.configurationLocationProvider = configurationLocationProvider;
        }

        public Location getHubSpawnLocation() {
            return this.configurationLocationProvider.getLocation(
                    this.configuration.getConfigurationSection(ConfigurationKeys.HUB_SPAWN_POINT.getKey())
            );
        }
    }

    private static class PlayerJoinListenerMessages {

        private final Game game;
        private final ScoreboardManager scoreboardManager;
        private final BukkitAudiences audiences;
        private final Translator translator;
        private final Configuration configuration;

        @Inject
        public PlayerJoinListenerMessages(Game game,
                                          ScoreboardManager scoreboardManager,
                                          BukkitAudiences audiences,
                                          Translator translator,
                                          Configuration configuration) {
            this.game = game;
            this.scoreboardManager = scoreboardManager;
            this.audiences = audiences;
            this.translator = translator;
            this.configuration = configuration;
        }

        public void warnGameIsFull(Player joiner) {
            Audience joinerAudience = this.audiences.player(joiner);
            joinerAudience.sendMessage(Component.translatable(Messages.GAME_IS_FULL));
        }

        public void warnGameHasStarted(Player joiner) {
            Audience joinerAudience = this.audiences.player(joiner);
            joinerAudience.sendMessage(Component.translatable(Messages.GAME_ALREADY_STARTED));
        }

        public void printScoreboard(Player joiner) {
            FastBoard board = this.scoreboardManager.addScoreboard(joiner);
            board.updateTitle(
                    this.translator.translate(joiner,
                            Component.translatable(Messages.SCOREBOARD_TITLE)
                    )
            );
            board.updateLine(1,
                    this.translator.translate(joiner,
                            Component.translatable(Messages.SERVER_IP)
                    )
            );
        }

        public void broadcastJoin(Player joiner) {
            this.game.getConnected().forEach(player -> {
                Audience audience = this.audiences.player(player);
                audience.sendMessage(
                        Component.translatable(Messages.GAME_WAITING_PLAYER_JOIN)
                                .args(
                                        Component.text(joiner.getDisplayName()),
                                        Component.text(this.game.getPlayerAmount()),
                                        Component.text(this.configuration.getMaximumPlayerAmount())
                                )
                );
            });
        }

        public void broadcastRemainPlayerAmount(int remainPlayerAmount) {
            this.game.getPlayers().forEach(player -> {
                Audience audience = this.audiences.player(player);
                audience.sendMessage(
                        Component.translatable(Messages.GAME_WAITING_REMAIN_PLAYER_AMOUNT)
                                .args(Component.text(remainPlayerAmount))
                );
            });
        }
    }
}
