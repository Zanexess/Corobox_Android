package me.labs.corobox.corobox.presenter.main_screen.card_fragment;

import android.app.Activity;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.presenter.main_screen.address_fragment.IAddressFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.address_fragment.IAddressFragmentView;
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

}