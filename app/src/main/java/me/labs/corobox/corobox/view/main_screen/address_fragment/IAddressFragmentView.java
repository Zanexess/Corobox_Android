package me.labs.corobox.corobox.view.main_screen.address_fragment;

import android.app.Activity;

public interface IAddressFragmentView {
    Activity provideActivity();
    void setDefaultAddress(String uuid);
    void deleteAddress(String uuid);
}
