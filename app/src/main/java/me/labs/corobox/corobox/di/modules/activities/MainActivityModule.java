package me.labs.corobox.corobox.di.modules.activities;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.presenter.main_screen.IMainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.MainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.boxes_fragment.BoxesFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.boxes_fragment.IBoxesFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.categories_fragment.CategoryFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.categories_fragment.ICategoryFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.IMainActivityView;

@Module
public class MainActivityModule {

    private static final String LOG_TAG = MainActivityModule.class.getCanonicalName();

    @Inject
    CoroboxApp coroboxApp;

    private IMainActivityView mainActivityView;

    public MainActivityModule(IMainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Provides
    @ActivityScope
    public IMainActivityView provideView() {
        return mainActivityView;
    }

    @Provides
    @ActivityScope
    public IMainActivityPresenter provideMainActivityPresenter(IMainActivityView view) {
        return new MainActivityPresenter(view);
    }

    @Provides
    @ActivityScope
    public IBoxesFragmentPresenter provideBoxesFragmentPresenter() {
        return new BoxesFragmentPresenter();
    }

    @Provides
    @ActivityScope
    public ICategoryFragmentPresenter provideCategoriesFragmentPresenter() {
        return new CategoryFragmentPresenter();
    }
}
