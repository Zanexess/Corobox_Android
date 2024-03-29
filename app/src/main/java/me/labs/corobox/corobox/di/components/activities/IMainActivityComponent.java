package me.labs.corobox.corobox.di.components.activities;

import dagger.Component;
import me.labs.corobox.corobox.di.components.ICoroboxAppComponent;
import me.labs.corobox.corobox.di.modules.activities.MainActivityModule;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.view.main_screen.MainActivityView;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.BoxesFragmentView;
import me.labs.corobox.corobox.view.main_screen.categories_fragment.CategoryFragmentView;
import me.labs.corobox.corobox.view.main_screen.orders_screen.OrderFragmentFrom;
import me.labs.corobox.corobox.view.main_screen.orders_screen.OrderFragmentTo;
import me.labs.corobox.corobox.view.main_screen.orders_screen.OrdersFragmentView;
import me.labs.corobox.corobox.view.main_screen.settings_fragment.SettingsFragmentView;
import me.labs.corobox.corobox.view.main_screen.terms_of_use_fragment.TermsFragmentView;

@ActivityScope
@Component(
        dependencies = ICoroboxAppComponent.class,
        modules = MainActivityModule.class
)
public interface IMainActivityComponent {
    void inject(MainActivityView mainActivityView);
    void inject(BoxesFragmentView boxesFragmentView);
    void inject(CategoryFragmentView categoryFragmentView);
    void inject(TermsFragmentView termsFragmentView);
    void inject(SettingsFragmentView settingsFragmentView);
    void inject(OrdersFragmentView ordersFragmentView);
    void inject(OrderFragmentTo orderFragmentTo);
    void inject(OrderFragmentFrom orderFragmentFrom);
}