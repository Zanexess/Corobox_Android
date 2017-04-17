package me.labs.corobox.corobox.presenter.main_screen.terms_of_use;

import android.app.Activity;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.view.main_screen.terms_of_use_fragment.ITermsFragmentView;

public class TermsFragmentPresenter implements ITermsFragmentPresenter {

    private Activity activity;
    private ITermsFragmentView view;

    @Override
    public void init(ITermsFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void openTerms() {
        view.openTerms();
    }
}