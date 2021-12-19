package me.choukas.dodgecreeper.core.api.translation;

import me.choukas.dodgecreeper.api.translation.TranslationRegisterer;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;

import javax.inject.Inject;
import java.io.IOException;

public class TranslationRegistererImpl implements TranslationRegisterer {

    private final GlobalTranslator globalTranslator;
    private final TranslationRegistry translationRegistry;

    @Inject
    public TranslationRegistererImpl(GlobalTranslator globalTranslator,
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

    @Override
    public void registerTranslations() {
        this.globalTranslator.addSource(this.translationRegistry);
    }
}
