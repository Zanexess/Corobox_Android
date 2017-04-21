package me.labs.corobox.corobox.view.main_screen.make_order_screen;

import android.app.Activity;

public interface IMakeOrderActivityView {
    Activity getActivity();
    void showPrice(String count);
    void updateList();
    void finishActivity();
}

