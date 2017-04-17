package me.labs.corobox.corobox.presenter.main_screen.boxes_fragment;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.util.HashSet;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.IBoxesFragmentView;

public class BoxesFragmentPresenter implements IBoxesFragmentPresenter {

    private Activity activity;
    private IBoxesFragmentView view;

    @Override
    public void init(IBoxesFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void readyForOrder(HashSet<Integer> selected) {
        if (selected.size() != 0) {
            view.setReadyButtonVisibility(View.VISIBLE);
        } else {
            view.setReadyButtonVisibility(View.GONE);
        }
    }

}