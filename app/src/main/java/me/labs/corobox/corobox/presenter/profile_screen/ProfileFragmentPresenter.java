package me.labs.corobox.corobox.presenter.profile_screen;

import android.app.Activity;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.view.address_screen.IAddressFragmentView;
import me.labs.corobox.corobox.view.profile_screen.IProfileFragmentView;


public class ProfileFragmentPresenter implements IProfileFragmentPresenter {

    private Activity activity;
    private IProfileFragmentView view;

    @Override
    public void init(IProfileFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }
}
