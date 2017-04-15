package me.labs.corobox.corobox.presenter.main_screen;

import javax.inject.Inject;

import me.labs.corobox.corobox.view.main_screen.IMainActivityView;

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivityView view;

    @Inject
    public MainActivityPresenter(IMainActivityView view) {
        this.view = view;
    }

}