package me.choukas.dodgecreeper.core.api.game.death;

import fr.mrmicky.fastboard.FastBoard;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.death.DeathManager;
import me.choukas.dodgecreeper.api.player.DodgeCreeperPlayer;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.bukkit.BukkitConfiguration;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationKeys;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationLocationProvider;
import me.choukas.dodgecreeper.core.api.game.spectate.SpectateManagerImpl;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import me.choukas.dodgecreeper.core.api.translation.Messages;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class DeathManagerImpl implements DeathManager {

    private final Game game;
    private final SpectateManagerImpl spectateManager;
    private final DeathManagerConfiguration configuration;
    private final DeathManagerMessages messages;

    @Inject
    public DeathManagerImpl(Game game,
                            SpectateManagerImpl spectateManager,
                            DeathManagerConfiguration configuration,
                            DeathManagerMessages messages) {
        this.game = game;
        this.messages = messages;
        this.configuration = configuration;
        this.spectateManager = spectateManager;
    }

    @Override
    public void death(Player deadPlayer) {
        this.messages.sendDeathMessage(deadPlayer);

        this.messages.broadcastDeath(deadPlayer);

        DodgeCreeperPlayer deadDodgePlayer = this.game.getPlayer(deadPlayer.getUniqueId());
        deadDodgePlayer.spectate();

        deadPlayer.setHealth(deadPlayer.getMaxHealth());

        this.spectateManager.spectate(deadPlayer);

        Location spawnPoint = this.configuration.getDeathSpawnLocation();
        deadPlayer.teleport(spawnPoint);

        if (this.game.getPlayerAmount() == 1) {
            // Win
            Player winner = this.game.getPlayers().get(0);
            this.messages.sendWinMessage(winner);

            this.game.finish();
        }
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

                FastBoard board = this.scoreboardManager.getScoreboard(player.getUniqueId());
                board.updateLine(0,
                        this.translator.translate(player,
                                Component.translatable(Messages.REMAINING_PLAYER_AMOUNT)
                                        .args(Component.text(this.game.getPlayerAmount()))
                        )
                );
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
