package me.labs.corobox.corobox.view.address_screen;

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
