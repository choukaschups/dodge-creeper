package me.choukas.dodgecreeper.core.api.configuration;

public enum ConfigurationKeys {

    MIN_PLAYERS("min-players"),
    MAX_PLAYERS("max-players"),
    WORLD_NAME("world-name"),
    HUB_SPAWN_POINT("hub-spawn-point"),
    GAME_START_SPAWN_POINT("game-start-spawn-point"),
    DEATH_SPAWN_POINT("death-spawn-point"),
    HEIGHT_LIMIT("height-limit"),
    CREEPER_SPAWN_INTERVAL("creeper-spawn-interval"),
    CREEPER_SPAWN_LOCATIONS("creeper-spawn-locations"),
    TIMER("timer"),
    TIMER_LEVELS("levels"),
    TIMER_PRINT("print"),
    TIMER_STAYING_TIME("staying-time");

    private final String key;

    ConfigurationKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
