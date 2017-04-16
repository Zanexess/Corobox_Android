package me.labs.corobox.corobox.view.main_screen.boxes_fragment;

import android.app.Activity;

public interface IBoxesFragmentView {
    Activity provideActivity();
    void setReadyButtonVisibility(int visible);
}
