package me.choukas.dodgecreeper.core.api.translation;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.throwingproviders.CheckedProvides;
import com.google.inject.throwingproviders.ThrowingProviderBinder;
import me.choukas.dodgecreeper.api.translation.TranslationRegisterer;
import me.choukas.dodgecreeper.api.translation.Translator;
import me.choukas.dodgecreeper.core.bukkit.BukkitDataFolder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslationModule extends AbstractModule {

    private static final String TRANSLATION_FOLDER = "translations";
    private static final String TRANSLATION_REGISTRY_KEY = "translation-registry";
    private static final String BUNDLE_NAME = "DodgeCreeper";

    private static final Locale[] SUPPORTED_LOCALES = {Locale.FRENCH};

    @Override
    protected void configure() {
        install(ThrowingProviderBinder.forModule(this));

        bind(TranslationRegisterer.class).to(TranslationRegistererImpl.class);
        bind(Translator.class).to(TranslatorImpl.class);
    }

    @Provides
    public GlobalTranslator provideGlobalTranslator() {
        return GlobalTranslator.get();
    }

    @CheckedProvides(TranslationRegistryProvider.class)
    public TranslationRegistry provideTranslationRegistry(@BukkitDataFolder Path dataFolder) throws IOException {
        TranslationRegistry translationRegistry = TranslationRegistry.create(Key.key(TRANSLATION_REGISTRY_KEY));
        translationRegistry.defaultLocale(Locale.FRENCH);

        Path translationFolderPath = dataFolder.resolve(TRANSLATION_FOLDER);

        URL[] urls = {translationFolderPath.toFile().toURI().toURL()};
        ClassLoader loader = new URLClassLoader(urls);

        for (Locale locale : SUPPORTED_LOCALES) {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale, loader, UTF8ResourceBundleControl.get());
            translationRegistry.registerAll(locale, bundle, true);
        }

        return translationRegistry;
    }
}
