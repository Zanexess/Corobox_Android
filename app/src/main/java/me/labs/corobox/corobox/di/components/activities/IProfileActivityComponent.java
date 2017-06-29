package me.labs.corobox.corobox.di.components.activities;

import dagger.Component;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.modules.activities.AddressActivityModule;
import me.labs.corobox.corobox.di.modules.activities.ProfileActivityModule;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.view.address_screen.AddressActivityView;
import me.labs.corobox.corobox.view.address_screen.AddressFragmentView;
import me.labs.corobox.corobox.view.profile_screen.ProfileActivityView;

@ActivityScope
@Component(
        dependencies = ICoroboxAppComponent.class,
        modules = ProfileActivityModule.class
)
public interface IProfileActivityComponent {
    void inject(ProfileActivityView profileActivityView);
}

