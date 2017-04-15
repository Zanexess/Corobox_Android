package me.labs.corobox.corobox.di.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.labs.corobox.corobox.app.CoroboxApp;

@Module
public class CoroboxAppModule {
    private final CoroboxApp app;

    public CoroboxAppModule(CoroboxApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }
}