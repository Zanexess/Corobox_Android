package me.labs.corobox.corobox.presenter;

import javax.inject.Inject;

import me.labs.corobox.corobox.view.ISplashActivityView;

public class SplashActivityPresenter implements ISplashActivityPresenter {
    private ISplashActivityView view;

    @Inject
    public SplashActivityPresenter(ISplashActivityView view) {
        this.view = view;
    }
}
