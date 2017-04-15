package me.labs.corobox.corobox.presenter.main_screen;

import me.labs.corobox.corobox.common.FragmentType;

public interface IMainActivityPresenter {
    void changeFragment(FragmentType boxes);
    void changeTitle(String title);
}