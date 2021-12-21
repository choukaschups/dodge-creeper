package me.choukas.dodgecreeper.api.configuration;

import org.bukkit.World;

import java.util.Optional;

public interface Configuration {

    int getMinimumPlayerAmount();

    int getMaximumPlayerAmount();

    Optional<Integer> getTimerLevel(int playerAmount);

    int getMinimumPlayerAmountTimerLevel();

    // TODO Change name
    boolean shouldPrintTimer(int timer);

    int getTimerStayingTime();

    int getCreeperSpawnTimeInterval();

    World getWorld();

    int getHeightLimit();
}
