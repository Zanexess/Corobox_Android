package me.labs.corobox.corobox.presenter.main_screen.settings;

import android.app.Activity;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.presenter.main_screen.terms_of_use.ITermsFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.settings_fragment.ISettingsFragmentView;
import me.labs.corobox.corobox.view.main_screen.terms_of_use_fragment.ITermsFragmentView;

public class SettingsFragmentPresenter implements ISettingsFragmentPresenter {

    private Activity activity;
    private ISettingsFragmentView view;

    @Override
    public void init(ISettingsFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

}