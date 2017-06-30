package me.labs.corobox.corobox.view.profile_screen;

import android.app.Activity;

import java.util.List;

import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.ProfileModel;

public interface IProfileFragmentView {
    Activity provideActivity();
    void showProfileModel(ProfileModel body);
}
