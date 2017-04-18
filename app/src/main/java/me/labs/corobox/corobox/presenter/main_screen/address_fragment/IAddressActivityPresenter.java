package me.labs.corobox.corobox.presenter.main_screen.address_fragment;

import me.labs.corobox.corobox.common.FragmentType;

public interface IAddressActivityPresenter {
    void changeFragment(FragmentType boxes);
    void changeTitle(String title);
    FragmentType getCurrentType();
}