package me.choukas.dodgecreeper.core.api.game.autostart;

import me.choukas.dodgecreeper.api.game.Game;
import me.choukas.dodgecreeper.api.game.autostart.AutoStartManager;
import me.choukas.dodgecreeper.core.api.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

public class AutoStartManagerImpl implements AutoStartManager {

    private final FileConfiguration configuration;
    private final Game game;
    private final Plugin plugin;
    private final AutoStartRunnable runnable;

    @Inject
    public AutoStartManagerImpl(@Configuration FileConfiguration configuration,
                                Game game,
                                Plugin plugin,
                                AutoStartRunnable runnable) {
        this.configuration = configuration;
        this.game = game;
        this.plugin = plugin;
        this.runnable = runnable;
    }

    @Override
    public void connect() {
        // Précond : Partie pas encore lancée, partie pas encore pleine, joueur déjà ajouté
        // 1) Si le nombre de joueurs a atteint le minimum, on lance le timer
        // 2) Si le nombre de joueurs est supérieur au minimum, on regarde si on a atteint un pallier
            // - Si non : On fait rien
            // - Si oui : On passe au temps du pallier (seulement si le timer est supérieur à ce temps)

        if (this.game.getPlayerAmount() == this.configuration.getInt("min-players")) {
            this.runnable.runTaskTimer(this.plugin, 0, 20);

            return;
        }

        if (this.configuration.contains(String.format("timer.levels.%d", this.game.getPlayerAmount()))) {
            int playerAmount = this.game.getPlayerAmount();

            String format;

            if (playerAmount == this.configuration.getInt("min-players")) {
                format = "min-players";
            } else if (playerAmount == this.configuration.getInt("max-players")) {
                format = "max-players";
            } else {
                format = String.valueOf(playerAmount);
            }

            int timer = this.configuration.getInt(String.format("timer.levels.%s", format));
            this.runnable.update(timer);
        }
    }

    @Override
    public void disconnect() {
        // Prédonc : Partie pas encore commencée, joueur pas encore retiré
        // 1) Si le nombre de joueurs est égal au nombre de joueur minimal, on stoppe le timer
        // 2)

        if (this.game.getPlayerAmount() == this.configuration.getInt("min-players")) {
            this.runnable.cancel();
        }
    }
}
