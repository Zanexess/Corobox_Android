package me.labs.corobox.corobox.presenter.main_screen;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.HashMap;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.view.main_screen.IMainActivityView;
import me.labs.corobox.corobox.view.main_screen.MainActivityView;
import me.labs.corobox.corobox.view.main_screen.address_fragment.AddressActivityView;
import me.labs.corobox.corobox.view.main_screen.address_fragment.AddressFragmentView;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.BoxesFragmentView;
import me.labs.corobox.corobox.view.main_screen.card_screen.CardActivityView;
import me.labs.corobox.corobox.view.main_screen.card_screen.CardFragmentView;
import me.labs.corobox.corobox.view.main_screen.categories_fragment.CategoryFragmentView;
import me.labs.corobox.corobox.view.main_screen.settings_fragment.SettingsFragmentView;
import me.labs.corobox.corobox.view.main_screen.terms_of_use_fragment.TermsFragmentView;

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivityView view;
    private FragmentType currentType;
    private int badgeCount = 0;
    private HashMap<String, Integer> hashMap;

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
                    changeTitle(view.getActivity().getString(R.string.my_boxes_title));
                    ft.replace(R.id.frame_layout, new BoxesFragmentView());
                    setVisibilityDeliveryMenu(false);
                    break;
                case NEW_BOX:
                    changeTitle(view.getActivity().getString(R.string.sent_to_stock_title));
                    ft.replace(R.id.frame_layout, new CategoryFragmentView());
                    setVisibilityDeliveryMenu(true);
                    break;
                case TERMS:
                    changeTitle(view.getActivity().getString(R.string.terms_title));
                    ft.replace(R.id.frame_layout, new TermsFragmentView());
                    setVisibilityDeliveryMenu(false);
                    break;
                case SETTINGS:
                    changeTitle(view.getActivity().getString(R.string.settings_title));
                    ft.replace(R.id.frame_layout, new SettingsFragmentView());
                    setVisibilityDeliveryMenu(false);
                    break;
                case ADDRESS:
                    Intent intent = new Intent(view.getActivity(), AddressActivityView.class);
                    view.getActivity().startActivity(intent);
                    break;
                case CARD:
                    Intent intent1 = new Intent(view.getActivity(), CardActivityView.class);
                    view.getActivity().startActivity(intent1);
                    break;
            }
            ft.commit();
        }
    }

    @Override
    public void changeTitle(String title) {
        ((MainActivityView) view).getSupportActionBar().setTitle(title);
    }

    @Override
    public void updateBadgeCounter(int i) {
        setDeliveryBadgeCount(badgeCount + i);
        view.updateMenu(i);
    }

    @Override
    public void setVisibilityDeliveryMenu(boolean isVisible) {
        view.setVisibilityDeliveryMenu(isVisible);
    }

    @Override
    public int getDeliveryBadgeCount() {
        return badgeCount;
    }

    @Override
    public void setDeliveryBadgeCount(int i) {
        this.badgeCount = i;
    }

    @Override
    public void setHashMap(HashMap<String, Integer> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public void openCart(HashMap<String, Integer> hashMap) {
        for (String key :hashMap.keySet()) {
            if (hashMap.get(key) != 0) {
                Toast.makeText(view.getActivity(), key + " " + hashMap.get(key), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public HashMap<String, Integer> getHashMap() {
        return hashMap;
    }

    @Override
    public FragmentType getCurrentType() {
        return currentType;
    }
}