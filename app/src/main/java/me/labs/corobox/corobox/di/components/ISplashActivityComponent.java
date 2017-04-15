package me.labs.corobox.corobox.di.components;

import dagger.Component;
import me.labs.corobox.corobox.di.modules.activity.SplashActivityModule;
import me.labs.corobox.corobox.di.scope.ActivityScope;

@ActivityScope
@Component(
        dependencies = ICoroboxAppComponent.class,
        modules = SplashActivityModule.class
)
public interface ISplashActivityComponent {

}
