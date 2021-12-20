package me.choukas.dodgecreeper.core.api.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;

import java.util.Locale;

public class AdventureUtils {

    public static String fromAdventureToVanilla(Component component) {
        String json = GsonComponentSerializer.gson().serialize(GlobalTranslator.render(component, Locale.FRENCH));
        IChatBaseComponent chatBaseComponent = IChatBaseComponent.ChatSerializer.a(json);

        return chatBaseComponent.c();
    }
}
