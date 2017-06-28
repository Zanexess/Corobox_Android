package me.labs.corobox.corobox.view.make_order_screen;

import android.app.Activity;

public interface IMakeOrderActivityView {
    Activity getActivity();
    void showPrice(String count);
    void updateList();
    void finishActivity();
    void showToast(String s);
    void orderSuccessfullyAdded();
}

