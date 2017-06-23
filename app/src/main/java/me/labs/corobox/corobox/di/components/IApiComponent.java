package me.labs.corobox.corobox.di.components;

import dagger.Component;
import me.labs.corobox.corobox.di.modules.ApiModule;
import me.labs.corobox.corobox.di.scope.ApiScope;
import me.labs.corobox.corobox.presenter.main_screen.address_screen.AddressFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.boxes_fragment.BoxesFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.card_screen.CardFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.categories_fragment.CategoryFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.orders_screen.OrdersFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.settings.SettingsFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.terms_of_use.TermsFragmentPresenter;
import me.labs.corobox.corobox.presenter.make_order_screen.MakeOrderActivityPresenter;

@ApiScope
@Component(
        dependencies = INetworkComponent.class,
        modules = ApiModule.class
)
public interface IApiComponent {
    void inject(BoxesFragmentPresenter boxesFragmentPresenter);
    void inject(CategoryFragmentPresenter categoryFragmentPresenter);
    void inject(TermsFragmentPresenter termsFragmentPresenter);
    void inject(SettingsFragmentPresenter settingsFragmentPresenter);
    void inject(AddressFragmentPresenter addressFragmentPresenter);
    void inject(CardFragmentPresenter cardFragmentPresenter);
    void inject(OrdersFragmentPresenter ordersFragmentPresenter);
    void inject(MakeOrderActivityPresenter makeOrderActivityPresenter);
}

