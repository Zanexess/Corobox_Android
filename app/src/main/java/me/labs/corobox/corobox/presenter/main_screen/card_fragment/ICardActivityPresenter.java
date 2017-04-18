package me.labs.corobox.corobox.presenter.main_screen.card_fragment;

import java.util.HashMap;

import me.labs.corobox.corobox.common.FragmentType;

public interface ICardActivityPresenter {
    void changeFragment(FragmentType boxes);
    void changeTitle(String title);
    FragmentType getCurrentType();
}