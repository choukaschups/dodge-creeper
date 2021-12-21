package me.choukas.dodgecreeper.core.api.game.death;

import fr.mrmicky.fastboard.FastBoard;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.death.DeathManager;
import me.choukas.dodgecreeper.api.game.spectate.SpectateManager;
import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationKeys;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationLocationProvider;
import me.choukas.dodgecreeper.core.api.game.GameRunnable;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import me.choukas.dodgecreeper.core.api.translation.Messages;
import me.choukas.dodgecreeper.core.bukkit.BukkitConfiguration;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.UUID;

public class DeathManagerImpl implements DeathManager {

    private final Game game;
    private final SpectateManager spectateManager;
    private final DeathManagerConfiguration configuration;
    private final DeathManagerMessages messages;
    private final GameRunnable gameRunnable;

    @Inject
    public DeathManagerImpl(Game game,
                            SpectateManager spectateManager,
                            DeathManagerConfiguration configuration,
                            DeathManagerMessages messages,
                            GameRunnable gameRunnable) {
        this.game = game;
        this.messages = messages;
        this.configuration = configuration;
        this.spectateManager = spectateManager;
        this.gameRunnable = gameRunnable;
    }

    @Override
    public void death(Player deadPlayer) {
        UUID deadPlayerId = deadPlayer.getUniqueId();
        DodgeCreeperPlayer deadDodgePlayer = this.game.getPlayer(deadPlayerId);
        deadDodgePlayer.spectate();

        this.spectateManager.spectate(deadPlayer);

        deadPlayer.setHealth(deadPlayer.getMaxHealth());

        Location spawnPoint = this.configuration.getDeathSpawnLocation();
        deadPlayer.teleport(spawnPoint);

        this.messages.sendDeathMessage(deadPlayer);
        this.messages.broadcastDeath(deadPlayer);

        if (this.game.getPlayerAmount() > 1) {
            // Some players remaining
            this.messages.updateScoreboard();

            return;
        }

        Player winner = this.game.getPlayers().get(0);
        this.messages.sendWinMessage(winner);

        this.messages.clearScoreboard();

        this.gameRunnable.cancel();

        this.game.finish();
    }

    private static class DeathManagerConfiguration {

        private final FileConfiguration configuration;
        private final ConfigurationLocationProvider configurationLocationProvider;

        @Inject
        public DeathManagerConfiguration(@BukkitConfiguration FileConfiguration configuration, ConfigurationLocationProvider configurationLocationProvider) {
            this.configuration = configuration;
            this.configurationLocationProvider = configurationLocationProvider;
        }

        public Location getDeathSpawnLocation() {
            return this.configurationLocationProvider.getLocation(
                    this.configuration.getConfigurationSection(ConfigurationKeys.DEATH_SPAWN_POINT.getKey())
            );
        }
    }

    private static class DeathManagerMessages {

        private final Game game;
        private final BukkitAudiences audiences;
        private final Translator translator;
        private final ScoreboardManager scoreboardManager;

        @Inject
        public DeathManagerMessages(Game game,
                                    BukkitAudiences audiences,
                                    Translator translator,
                                    ScoreboardManager scoreboardManager) {
            this.game = game;
            this.audiences = audiences;
            this.translator = translator;
            this.scoreboardManager = scoreboardManager;
        }

        public void sendDeathMessage(Player player) {
            Audience deadAudience = this.audiences.player(player);
            deadAudience.sendTitlePart(TitlePart.TITLE,
                    Component.translatable(Messages.DEATH_TITLE)
            );
        }

        public void broadcastDeath(Player deadPlayer) {
            this.game.getConnected().forEach(player -> {
                Audience audience = this.audiences.player(player);
                audience.sendMessage(
                        Component.translatable(Messages.DEATH_BROADCAST)
                                .args(Component.text(deadPlayer.getDisplayName()))
                );
            });
        }

        public void updateScoreboard() {
            this.game.getConnected().forEach(player -> {
                FastBoard board = this.scoreboardManager.getScoreboard(player.getUniqueId());
                board.updateLine(0,
                        this.translator.translate(player,
                                Component.translatable(Messages.REMAINING_PLAYER_AMOUNT)
                                        .args(Component.text(this.game.getPlayerAmount()))
                        )
                );
            });
        }

        public void clearScoreboard() {
            this.game.getConnected().forEach(player -> {
                FastBoard board = this.scoreboardManager.getScoreboard(player.getUniqueId());
                board.removeLine(0);
            });
        }

        public void sendWinMessage(Player winner) {
            Audience winnerAudience = this.audiences.player(winner);
            winnerAudience.sendMessage(
                    Component.translatable(Messages.WIN)
            );
        }
    }
}
