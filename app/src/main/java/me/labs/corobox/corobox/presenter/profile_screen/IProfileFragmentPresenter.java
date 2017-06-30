package me.labs.corobox.corobox.presenter.profile_screen;

import me.labs.corobox.corobox.common.BaseFragmentPresenter;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.ProfileModel;
import me.labs.corobox.corobox.view.address_screen.IAddressFragmentView;
import me.labs.corobox.corobox.view.profile_screen.IProfileFragmentView;

public interface IProfileFragmentPresenter extends BaseFragmentPresenter<IProfileFragmentView> {
    void fetchData();
    void putProfile(ProfileModel profileModel);
}
