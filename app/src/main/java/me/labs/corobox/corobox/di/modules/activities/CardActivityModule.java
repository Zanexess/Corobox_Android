package me.labs.corobox.corobox.di.modules.activities;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.di.scope.ActivityScope;
import me.labs.corobox.corobox.presenter.main_screen.IMainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.MainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.card_fragment.CardActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.card_fragment.CardFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.card_fragment.ICardActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.card_fragment.ICardFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.IMainActivityView;
import me.labs.corobox.corobox.view.main_screen.card_screen.ICardActivityView;

@Module
public class CardActivityModule {
    private static final String LOG_TAG = CardActivityModule.class.getCanonicalName();

    @Inject
    CoroboxApp coroboxApp;

    private ICardActivityView cardActivityView;

    public CardActivityModule(ICardActivityView cardActivityView) {
        this.cardActivityView = cardActivityView;
    }

    @Provides
    @ActivityScope
    public ICardActivityView provideView() {
        return cardActivityView;
    }


    @Provides
    @ActivityScope
    public ICardActivityPresenter provideCardActivityPresenter(ICardActivityView view) {
        return new CardActivityPresenter(view);
    }

    @Provides
    @ActivityScope
    public ICardFragmentPresenter provideCardFragmentPresenter() {
        return new CardFragmentPresenter();
    }
}
