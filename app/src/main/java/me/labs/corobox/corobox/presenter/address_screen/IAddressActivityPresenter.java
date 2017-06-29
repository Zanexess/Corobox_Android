package me.labs.corobox.corobox.presenter.address_screen;

import me.labs.corobox.corobox.common.FragmentType;

public interface IAddressActivityPresenter {
    void changeFragment(FragmentType boxes);
    void changeTitle(String title);
    FragmentType getCurrentType();
}