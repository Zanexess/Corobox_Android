package me.labs.corobox.corobox.presenter.main_screen.boxes_fragment;

import java.util.HashSet;

import me.labs.corobox.corobox.common.BaseFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.IBoxesFragmentView;

public interface IBoxesFragmentPresenter extends BaseFragmentPresenter<IBoxesFragmentView> {
    void onResume();
    void onPause();
    void readyForOrder(HashSet<Integer> selected);
    void fetchData();
}
