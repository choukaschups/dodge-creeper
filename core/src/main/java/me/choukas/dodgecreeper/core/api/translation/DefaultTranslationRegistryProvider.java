package me.choukas.dodgecreeper.core.api.translation;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.TranslationRegistry;

public class DefaultTranslationRegistryProvider {

    private static final String DEFAULT_TRANSLATION_REGISTRY_NAME = "default-translation-registry";

    public static TranslationRegistry getDefaultTranslationRegistry() {
        return TranslationRegistry.create(Key.key(DEFAULT_TRANSLATION_REGISTRY_NAME));
    }
}
