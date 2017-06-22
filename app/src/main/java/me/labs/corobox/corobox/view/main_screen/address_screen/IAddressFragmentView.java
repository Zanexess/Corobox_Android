package me.labs.corobox.corobox.view.main_screen.address_screen;

import android.app.Activity;

import java.util.List;

import me.labs.corobox.corobox.model.realm.AddressModel;

public interface IAddressFragmentView {
    Activity provideActivity();
    void setDefaultAddress(Integer id);
    void deleteAddress(Integer id);
    void showData(List<AddressModel> body);
    void showEmptyData();
    void showToast(String s);
    void putSuccess();
}
