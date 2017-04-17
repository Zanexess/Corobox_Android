package me.labs.corobox.corobox.presenter.main_screen;

import java.util.HashMap;

import me.labs.corobox.corobox.common.FragmentType;

public interface IMainActivityPresenter {
    void changeFragment(FragmentType boxes);
    void changeTitle(String title);
    void updateBadgeCounter(int i);
    void setVisibilityDeliveryMenu(boolean isVisible);
    int getDeliveryBadgeCount();
    void setDeliveryBadgeCount(int i);
    HashMap<String, Integer> getHashMap();
    void setHashMap(HashMap<String, Integer> hashMap);
    void openCart(HashMap<String, Integer> hashMap);
}