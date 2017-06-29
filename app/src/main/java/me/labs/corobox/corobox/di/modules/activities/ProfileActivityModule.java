package me.labs.corobox.corobox.di.modules.activities;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.presenter.address_screen.AddressActivityPresenter;
import me.labs.corobox.corobox.presenter.address_screen.AddressFragmentPresenter;
import me.labs.corobox.corobox.presenter.address_screen.IAddressActivityPresenter;
import me.labs.corobox.corobox.presenter.address_screen.IAddressFragmentPresenter;
import me.labs.corobox.corobox.presenter.profile_screen.IProfileActivityPresenter;
import me.labs.corobox.corobox.presenter.profile_screen.IProfileFragmentPresenter;
import me.labs.corobox.corobox.presenter.profile_screen.ProfileActivityPresenter;
import me.labs.corobox.corobox.presenter.profile_screen.ProfileFragmentPresenter;
import me.labs.corobox.corobox.view.address_screen.IAddressActivityView;
import me.labs.corobox.corobox.view.profile_screen.IProfileActivityView;

@Module
public class ProfileActivityModule {
    private static final String LOG_TAG = ProfileActivityModule.class.getCanonicalName();

    @Inject
    CoroboxApp coroboxApp;

    private IProfileActivityView profileActivityViewctivityView;

    public ProfileActivityModule(IProfileActivityView profileActivityViewctivityView) {
        this.profileActivityViewctivityView = profileActivityViewctivityView;
    }

    @Provides
    @ActivityScope
    public IProfileActivityView provideView() {
        return profileActivityViewctivityView;
    }


    @Provides
    @ActivityScope
    public IProfileActivityPresenter provideProfileActivityPresenter(IProfileActivityView view) {
        return new ProfileActivityPresenter(view);
    }

    @Provides
    @ActivityScope
    public IProfileFragmentPresenter provideProfileFragmentPresenter() {
        return new ProfileFragmentPresenter();
    }
}
