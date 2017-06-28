package me.labs.corobox.corobox.di.modules.activities;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.presenter.make_order_screen.IMakeOrderActivityPresenter;
import me.labs.corobox.corobox.presenter.make_order_screen.MakeOrderActivityPresenter;
import me.labs.corobox.corobox.view.make_order_screen.IMakeOrderActivityView;

@Module
public class MakeOrderActivityModule {
    private static final String LOG_TAG = MakeOrderActivityModule.class.getCanonicalName();

    @Inject
    CoroboxApp coroboxApp;

    private IMakeOrderActivityView makeOrderActivityViewctivityView;

    public MakeOrderActivityModule(IMakeOrderActivityView makeOrderActivityViewctivityView) {
        this.makeOrderActivityViewctivityView = makeOrderActivityViewctivityView;
    }

    @Provides
    @ActivityScope
    public IMakeOrderActivityView provideView() {
        return makeOrderActivityViewctivityView;
    }


    @Provides
    @ActivityScope
    public IMakeOrderActivityPresenter provideMakeOrderActivityPresenter(IMakeOrderActivityView view) {
        return new MakeOrderActivityPresenter(view);
    }
}
