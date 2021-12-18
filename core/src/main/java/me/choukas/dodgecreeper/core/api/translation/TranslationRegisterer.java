package me.choukas.dodgecreeper.core.api.translation;

import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;

import javax.inject.Inject;
import java.io.IOException;

public class TranslationRegisterer {

    private final GlobalTranslator globalTranslator;
    private final TranslationRegistry translationRegistry;

    @Inject
    public TranslationRegisterer(GlobalTranslator globalTranslator,
                                 TranslationRegistryProvider translationRegistryProvider) {
        this.globalTranslator = globalTranslator;

        TranslationRegistry translationRegistry;

        try {
            translationRegistry = translationRegistryProvider.get();
        } catch (IOException e) {
            e.printStackTrace();

            translationRegistry = DefaultTranslationRegistryProvider.getDefaultTranslationRegistry();
        }

        this.translationRegistry = translationRegistry;
    }

    public void registerTranslations() {
        this.globalTranslator.addSource(this.translationRegistry);
    }
}
