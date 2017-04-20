package me.labs.corobox.corobox.presenter.main_screen.address_screen;

import me.labs.corobox.corobox.common.BaseFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.address_screen.IAddressFragmentView;

public interface IAddressFragmentPresenter extends BaseFragmentPresenter<IAddressFragmentView> {
    void deleteAddress(String uuid);
    void setDefaultAddress(String uuid);
}
