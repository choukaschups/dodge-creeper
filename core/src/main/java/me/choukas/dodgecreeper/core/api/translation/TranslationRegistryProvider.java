package me.choukas.dodgecreeper.core.api.translation;

import com.google.inject.throwingproviders.CheckedProvider;
import net.kyori.adventure.translation.TranslationRegistry;

import java.io.IOException;

public interface TranslationRegistryProvider extends CheckedProvider<TranslationRegistry> {

    @Override
    TranslationRegistry get() throws IOException;
}
