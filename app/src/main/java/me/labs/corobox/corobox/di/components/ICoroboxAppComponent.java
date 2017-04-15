package me.labs.corobox.corobox.di.components;

import javax.inject.Singleton;

import dagger.Component;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.di.modules.CoroboxAppModule;

@Singleton
@Component(
        modules = {
                CoroboxAppModule.class
        }
)
public interface ICoroboxAppComponent {
    void inject(CoroboxApp app);
}
