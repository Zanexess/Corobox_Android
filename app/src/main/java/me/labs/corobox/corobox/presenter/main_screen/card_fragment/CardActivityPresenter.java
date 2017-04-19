package me.labs.corobox.corobox.presenter.main_screen.card_fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.HashMap;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.presenter.main_screen.IMainActivityPresenter;
import me.labs.corobox.corobox.view.main_screen.IMainActivityView;
import me.labs.corobox.corobox.view.main_screen.MainActivityView;
import me.labs.corobox.corobox.view.main_screen.address_fragment.AddressFragmentView;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.BoxesFragmentView;
import me.labs.corobox.corobox.view.main_screen.card_screen.CardFragmentView;
import me.labs.corobox.corobox.view.main_screen.card_screen.ICardActivityView;
import me.labs.corobox.corobox.view.main_screen.categories_fragment.CategoryFragmentView;
import me.labs.corobox.corobox.view.main_screen.settings_fragment.SettingsFragmentView;
import me.labs.corobox.corobox.view.main_screen.terms_of_use_fragment.TermsFragmentView;

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