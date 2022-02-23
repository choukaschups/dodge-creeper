package me.choukas.dodgecreeper.core.api.translation;

import me.choukas.dodgecreeper.api.translation.Translator;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class TranslatorImpl implements Translator {

    @Override
    public String translate(@SuppressWarnings("unused") Player player, Component component) {
        return BukkitComponentSerializer.legacy().serialize(component);

        /*String json = GsonComponentSerializer.gson().serialize(GlobalTranslator.render(component, Locale.FRENCH));
        IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a(json);

        return chatBaseComponent.c();*/
    }
}
