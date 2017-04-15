package me.labs.corobox.corobox.presenter.main_screen;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.view.main_screen.IMainActivityView;
import me.labs.corobox.corobox.view.main_screen.MainActivityView;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.BoxesFragmentView;

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivityView view;

    @Inject
    public MainActivityPresenter(IMainActivityView view) {
        this.view = view;
    }

    @Override
    public void changeFragment(FragmentType boxes) {
        FragmentManager fm = ((AppCompatActivity)view.getActivity()).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (boxes) {
            case BOXES:
                changeTitle(view.getActivity().getString(R.string.my_boxes));
                ft.replace(R.id.frame_layout, new BoxesFragmentView());
                break;
        }
        ft.commit();
    }

    @Override
    public void changeTitle(String title) {
        ((MainActivityView)view).getSupportActionBar().setTitle(title);
    }
}