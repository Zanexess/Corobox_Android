package me.labs.corobox.corobox.di.components.activities;

import dagger.Component;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.modules.activities.MakeOrderActivityModule;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.view.make_order_screen.MakeOrderActivityView;

@ActivityScope
@Component(
        dependencies = ICoroboxAppComponent.class,
        modules = MakeOrderActivityModule.class
)
public interface IMakeOrderActivityComponent {
    void inject(MakeOrderActivityView makeOrderActivityView);
}

