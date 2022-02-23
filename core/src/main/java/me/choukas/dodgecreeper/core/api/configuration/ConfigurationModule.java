package me.choukas.dodgecreeper.core.api.configuration;

import com.google.inject.AbstractModule;
import me.choukas.dodgecreeper.api.configuration.Configuration;

public class ConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Configuration.class).to(ConfigurationImpl.class);
    }
}
