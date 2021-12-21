package me.choukas.dodgecreeper.core.api.game;

import fr.mrmicky.fastboard.FastBoard;
import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.GameStart;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.bukkit.BukkitConfiguration;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationImpl;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationKeys;
import me.choukas.dodgecreeper.core.api.configuration.ConfigurationLocationProvider;
import me.choukas.dodgecreeper.core.api.scoreboard.ScoreboardManager;
import me.choukas.dodgecreeper.core.api.translation.TranslationKeys;
import me.choukas.dodgecreeper.core.items.PusherItem;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import javax.inject.Inject;

public class GameStartImpl implements GameStart {

    private final Game game;
    private final PusherItem pusherItem;
    private final GameRunnableStart gameRunnableStart;
    private final GameStartConfiguration configuration;
    private final GameStartMessages messages;

    @Inject
    public GameStartImpl(Game game,
                         PusherItem pusherItem,
                         GameRunnableStart gameRunnableStart,
                         GameStartConfiguration configuration,
                         GameStartMessages messages) {
        this.game = game;
        this.pusherItem = pusherItem;
        this.gameRunnableStart = gameRunnableStart;
        this.configuration = configuration;
        this.messages = messages;
    }

    @Override
    public void start() {
        this.messages.broadcastStart();

        Location spawnPoint = this.configuration.getStartSpawnLocation();

        this.game.getPlayers().forEach(player -> {
            player.setLevel(0);
            player.teleport(spawnPoint);
            player.getInventory().setItem(0, this.pusherItem.asItemStack(player));
            player.setAllowFlight(true); // Double jumps
        });

        this.game.start();

        this.gameRunnableStart.startGameRunnable();
    }

    private static class GameStartConfiguration extends ConfigurationImpl {

        private final ConfigurationLocationProvider configurationLocationProvider;

        @Inject
        public GameStartConfiguration(@BukkitConfiguration FileConfiguration configuration, ConfigurationLocationProvider configurationLocationProvider) {
            super(configuration);

            this.configurationLocationProvider = configurationLocationProvider;
        }

        public Location getStartSpawnLocation() {
            return this.configurationLocationProvider.getLocation(
                    this.configuration.getConfigurationSection(ConfigurationKeys.GAME_START_SPAWN_POINT.getKey())
            );
        }
    }

    private static class GameStartMessages {

        private final Game game;
        private final ScoreboardManager scoreboardManager;
        private final BukkitAudiences audiences;
        private final Translator translator;

        @Inject
        public GameStartMessages(Game game,
                                 ScoreboardManager scoreboardManager,
                                 BukkitAudiences audiences,
                                 Translator translator) {
            this.game = game;
            this.scoreboardManager = scoreboardManager;
            this.audiences = audiences;
            this.translator = translator;
        }

        public void broadcastStart() {
            this.game.getConnected().forEach(player -> {
                Audience audience = this.audiences.player(player);
                audience.sendTitlePart(TitlePart.TITLE,
                        Component.text().color(NamedTextColor.YELLOW)
                                .append(Component.translatable(TranslationKeys.GAME_START_TITLE))
                                .build()
                );

                FastBoard board = scoreboardManager.getScoreboard(player.getUniqueId());
                board.updateLine(0, "");
                board.updateLine(1,
                        this.translator.translate(player,
                                Component.translatable(TranslationKeys.REMAINING_PLAYER_AMOUNT)
                                        .args(
                                                Component.text()
                                                        .color(NamedTextColor.RED)
                                                        .append(Component.text(this.game.getPlayerAmount()))
                                        )
                        )
                );
                board.updateLine(2, "");
                board.updateLine(3,
                        this.translator.translate(player,
                                Component.translatable(TranslationKeys.SERVER_IP)
                        )
                );
            });
        }
    }
}
