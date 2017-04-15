package me.labs.corobox.corobox.presenter.splash_screen;

import javax.inject.Inject;

import me.labs.corobox.corobox.view.splash_screen.ISplashActivityView;

public class SplashActivityPresenter implements ISplashActivityPresenter {
    private ISplashActivityView view;

    @Inject
    public SplashActivityPresenter(ISplashActivityView view) {
        this.view = view;
    }
}
