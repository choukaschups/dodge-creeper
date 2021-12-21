package me.choukas.dodgecreeper.core.api.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {

    private final Map<UUID, FastBoard> boards;

    public ScoreboardManager() {
        this.boards = new HashMap<>();
    }

    public void addScoreboard(Player player) {
        FastBoard board = new FastBoard(player);
        this.boards.put(player.getUniqueId(), board);
    }

    public FastBoard getScoreboard(UUID uuid) {
        return this.boards.get(uuid);
    }

    public void removeScoreboard(UUID player) {
        FastBoard board = this.boards.remove(player);
        board.delete();
    }
}
