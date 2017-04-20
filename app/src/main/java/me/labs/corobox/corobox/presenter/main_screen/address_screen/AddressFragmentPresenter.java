package me.labs.corobox.corobox.presenter.main_screen.address_screen;

import android.app.Activity;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.view.main_screen.address_screen.IAddressFragmentView;

public class AddressFragmentPresenter implements IAddressFragmentPresenter {

    private Activity activity;
    private IAddressFragmentView view;

    @Override
    public void init(IAddressFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void deleteAddress(String uuid) {
        view.deleteAddress(uuid);
    }

    @Override
    public void setDefaultAddress(String uuid) {
        view.setDefaultAddress(uuid);
    }
}