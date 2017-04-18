package me.labs.corobox.corobox.di.components.activities;

import dagger.Component;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.modules.activities.AddressActivityModule;
import me.labs.corobox.corobox.di.modules.activities.CardActivityModule;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.view.main_screen.address_fragment.AddressActivityView;
import me.labs.corobox.corobox.view.main_screen.address_fragment.AddressFragmentView;
import me.labs.corobox.corobox.view.main_screen.card_screen.CardActivityView;
import me.labs.corobox.corobox.view.main_screen.card_screen.CardFragmentView;

@ActivityScope
@Component(
        dependencies = ICoroboxAppComponent.class,
        modules = AddressActivityModule.class
)
public interface IAddressActivityComponent {
    void inject(AddressActivityView addressActivityView);
    void inject(AddressFragmentView addressFragmentView);
}

