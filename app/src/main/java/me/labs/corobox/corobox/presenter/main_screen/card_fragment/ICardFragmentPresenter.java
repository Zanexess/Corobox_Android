package me.labs.corobox.corobox.presenter.main_screen.card_fragment;

import me.labs.corobox.corobox.common.BaseFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.address_fragment.IAddressFragmentView;
import me.labs.corobox.corobox.view.main_screen.card_screen.ICardFragmentView;

public interface ICardFragmentPresenter extends BaseFragmentPresenter<ICardFragmentView> {
    void deleteCard(String uuid);
    void setDefaultCard(String uuid);
}
