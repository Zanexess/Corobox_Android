package me.labs.corobox.corobox.di.modules.activity;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.presenter.SplashActivityPresenter;
import me.labs.corobox.corobox.view.ISplashActivityView;

@Module
public class SplashActivityModule {

    private static final String LOG_TAG = SplashActivityModule.class.getCanonicalName();

    @Inject
    CoroboxApp coroboxApp;

    private ISplashActivityView splashActivityView;

    public SplashActivityModule(ISplashActivityView splashActivityView) {
        this.splashActivityView = splashActivityView;
    }

    @Provides
    @ActivityScope
    public ISplashActivityView provideView() {
        return splashActivityView;
    }

    @Provides
    @ActivityScope
    public SplashActivityPresenter provideSplashActivityPresenter(ISplashActivityView view) {
        return new SplashActivityPresenter(view);
    }


}