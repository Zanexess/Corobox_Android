package me.labs.corobox.corobox.view.main_screen.boxes_fragment;

import android.app.Activity;

import java.util.List;

import me.labs.corobox.corobox.model.realm.Box;

public interface IBoxesFragmentView {
    Activity provideActivity();
    void showData(List<Box> body);
    void showEmptyData();
    void deliveryClicked();
}
