package me.labs.corobox.corobox.presenter.main_screen;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.view.main_screen.IMainActivityView;
import me.labs.corobox.corobox.view.main_screen.MainActivityView;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.BoxesFragmentView;
import me.labs.corobox.corobox.view.main_screen.categories_fragment.CategoryFragmentView;

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivityView view;
    private FragmentType currentType;

    @Inject
    public MainActivityPresenter(IMainActivityView view) {
        this.view = view;
    }

    @Override
    public void changeFragment(FragmentType type) {
        if (currentType != type) {
            currentType = type;
            FragmentManager fm = ((AppCompatActivity) view.getActivity()).getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            switch (type) {
                case BOXES:
                    changeTitle(view.getActivity().getString(R.string.my_boxes));
                    ft.replace(R.id.frame_layout, new BoxesFragmentView());
                    setVisibilityDeliveryMenu(false);
                    break;
                case NEW_BOX:
                    changeTitle(view.getActivity().getString(R.string.sent_to_stock));
                    ft.replace(R.id.frame_layout, new CategoryFragmentView());
                    setVisibilityDeliveryMenu(true);
                    break;
            }
            ft.commit();
        }
    }

    @Override
    public void changeTitle(String title) {
        ((MainActivityView)view).getSupportActionBar().setTitle(title);
    }

    @Override
    public void updateBadgeCounter(int i) {
        view.updateMenu(i);
    }

    @Override
    public void setVisibilityDeliveryMenu(boolean isVisible) {
        view.setVisibilityDeliveryMenu(isVisible);
    }
}