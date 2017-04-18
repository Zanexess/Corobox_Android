package me.labs.corobox.corobox.di.components.activities;

import dagger.Component;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.modules.activities.CardActivityModule;
import me.labs.corobox.corobox.di.modules.activities.MainActivityModule;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.view.main_screen.MainActivityView;
import me.labs.corobox.corobox.view.main_screen.address_fragment.AddressFragmentView;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.BoxesFragmentView;
import me.labs.corobox.corobox.view.main_screen.card_screen.CardActivityView;
import me.labs.corobox.corobox.view.main_screen.card_screen.CardFragmentView;
import me.labs.corobox.corobox.view.main_screen.categories_fragment.CategoryFragmentView;
import me.labs.corobox.corobox.view.main_screen.settings_fragment.SettingsFragmentView;
import me.labs.corobox.corobox.view.main_screen.terms_of_use_fragment.TermsFragmentView;

@ActivityScope
@Component(
        dependencies = ICoroboxAppComponent.class,
        modules = CardActivityModule.class
)
public interface ICardActivityComponent {
    void inject(CardActivityView cardActivityView);
    void inject(CardFragmentView cardFragmentView);
}