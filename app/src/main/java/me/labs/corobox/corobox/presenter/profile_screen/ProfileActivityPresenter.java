package me.labs.corobox.corobox.presenter.profile_screen;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.view.profile_screen.IProfileActivityView;
import me.labs.corobox.corobox.view.profile_screen.ProfileFragmentView;

public class ProfileActivityPresenter implements IProfileActivityPresenter {

    private IProfileActivityView view;
    private FragmentType currentType;

    public ProfileActivityPresenter(IProfileActivityView view) {
        this.view = view;
    }

    @Override
    public void changeFragment(FragmentType type) {
        if (currentType != type) {
            currentType = type;
            FragmentManager fm = ((AppCompatActivity) view.provideActivity()).getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            switch (type) {
                case PROFILE:
                    changeTitle(view.provideActivity().getString(R.string.profile_title));
                    ft.replace(R.id.frame_layout, new ProfileFragmentView());
                    break;
            }
            ft.commit();
        }
    }

    private void changeTitle(String string) {
        ((AppCompatActivity) view).getSupportActionBar().setTitle(string);
    }
}
