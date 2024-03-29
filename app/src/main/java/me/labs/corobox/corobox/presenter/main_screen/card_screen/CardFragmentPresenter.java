package me.labs.corobox.corobox.presenter.main_screen.card_screen;

import android.app.Activity;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.view.main_screen.card_screen.ICardFragmentView;

public class CardFragmentPresenter implements ICardFragmentPresenter {

    private Activity activity;
    private ICardFragmentView view;

    @Override
    public void init(ICardFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void deleteCard(String uuid) {
        view.deleteCard(uuid);
    }

    @Override
    public void setDefaultCard(String uuid) {
        view.setDefaultCard(uuid);
    }
}