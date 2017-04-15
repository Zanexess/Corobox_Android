package me.labs.corobox.corobox.di.components.activities;

import dagger.Component;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.modules.activities.MainActivityModule;
import me.labs.corobox.corobox.di.scope.ActivityScope;

@ActivityScope
@Component(
        dependencies = ICoroboxAppComponent.class,
        modules = MainActivityModule.class
)
public interface IMainActivityComponent {

}