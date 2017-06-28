package me.labs.corobox.corobox.view.main_screen;

import android.app.Activity;

public interface IMainActivityView {
    Activity getActivity();
    void updateMenu(int badgeCount);
    void setVisibilityDeliveryMenu(boolean isVisible);
}

