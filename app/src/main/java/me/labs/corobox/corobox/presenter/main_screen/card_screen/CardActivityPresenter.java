package me.labs.corobox.corobox.presenter.main_screen.card_screen;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.view.main_screen.card_screen.CardFragmentView;
import me.labs.corobox.corobox.view.main_screen.card_screen.ICardActivityView;

public class CardActivityPresenter implements ICardActivityPresenter {

    private ICardActivityView view;
    private FragmentType currentType;

    @Inject
    public CardActivityPresenter(ICardActivityView view) {
        this.view = view;
    }

    @Override
    public void changeFragment(FragmentType type) {
        if (currentType != type) {
            currentType = type;
            FragmentManager fm = ((AppCompatActivity) view.getActivity()).getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            switch (type) {
                case CARD:
                    changeTitle(view.getActivity().getString(R.string.add_card_title));
                    ft.replace(R.id.frame_layout, new CardFragmentView());
                    break;
            }
            ft.commit();
        }
    }

    @Override
    public void changeTitle(String title) {
        ((AppCompatActivity) view).getSupportActionBar().setTitle(title);
    }

    @Override
    public FragmentType getCurrentType() {
        return currentType;
    }
}