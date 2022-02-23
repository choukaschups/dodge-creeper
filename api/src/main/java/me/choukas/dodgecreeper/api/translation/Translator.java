package me.choukas.dodgecreeper.api.translation;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface Translator {

    String translate(Player player, Component component);
}
