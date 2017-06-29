package me.labs.corobox.corobox.di.components.activities;

import dagger.Component;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.modules.activities.AddressActivityModule;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.view.address_screen.AddressActivityView;
import me.labs.corobox.corobox.view.address_screen.AddressFragmentView;

@ActivityScope
@Component(
        dependencies = ICoroboxAppComponent.class,
        modules = AddressActivityModule.class
)
public interface IAddressActivityComponent {
    void inject(AddressActivityView addressActivityView);
    void inject(AddressFragmentView addressFragmentView);
}

