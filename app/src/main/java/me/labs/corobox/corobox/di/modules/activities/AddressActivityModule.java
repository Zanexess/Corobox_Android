package me.labs.corobox.corobox.di.modules.activities;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.presenter.main_screen.address_screen.AddressActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.address_screen.AddressFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.address_screen.IAddressActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.address_screen.IAddressFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.address_screen.IAddressActivityView;

@Module
public class AddressActivityModule {
    private static final String LOG_TAG = AddressActivityModule.class.getCanonicalName();

    @Inject
    CoroboxApp coroboxApp;

    private IAddressActivityView addressActivityViewctivityView;

    public AddressActivityModule(IAddressActivityView addressActivityViewctivityView) {
        this.addressActivityViewctivityView = addressActivityViewctivityView;
    }

    @Provides
    @ActivityScope
    public IAddressActivityView provideView() {
        return addressActivityViewctivityView;
    }


    @Provides
    @ActivityScope
    public IAddressActivityPresenter provideAddressActivityPresenter(IAddressActivityView view) {
        return new AddressActivityPresenter(view);
    }

    @Provides
    @ActivityScope
    public IAddressFragmentPresenter provideAddressFragmentPresenter() {
        return new AddressFragmentPresenter();
    }
}
