package me.labs.corobox.corobox.presenter.main_screen.boxes_fragment;

import android.app.Activity;
import android.widget.Toast;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.IBoxesFragmentView;

public class BoxesFragmentPresenter implements IBoxesFragmentPresenter {

    private Activity activity;

    @Override
    public void init(IBoxesFragmentView view) {
        activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void onResume() {
        Toast.makeText(activity, "onResume!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        Toast.makeText(activity, "onPause!", Toast.LENGTH_SHORT).show();
    }
}