package me.labs.corobox.corobox.presenter.address_screen;

import me.labs.corobox.corobox.common.BaseFragmentPresenter;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.view.address_screen.IAddressFragmentView;

public interface IAddressFragmentPresenter extends BaseFragmentPresenter<IAddressFragmentView> {
    void deleteAddress(Integer id);
    void setDefaultAddress(Integer id);
    void fetchData();
    void putAddress(AddressModel addressModel);
}
