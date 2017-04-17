package me.labs.corobox.corobox.view.main_screen.settings_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.presenter.main_screen.settings.ISettingsFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.terms_of_use.ITermsFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.terms_of_use_fragment.ITermsFragmentView;

public class SettingsFragmentView extends BaseFragment implements ISettingsFragmentView {

    @Inject
    ISettingsFragmentPresenter presenter;

    private View view;
    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_settings, container, false);
            initComponents();
        }
        return view;
    }

    private void initComponents() {
        webView = (WebView) view.findViewById(R.id.webView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
        presenter.init(this);
    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }
}