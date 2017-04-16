package me.labs.corobox.corobox.presenter.main_screen.categories_fragment;

import android.app.Activity;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.view.main_screen.categories_fragment.ICategoryFragmentView;

public class CategoryFragmentPresenter implements ICategoryFragmentPresenter {

    private Activity activity;
    private ICategoryFragmentView view;

    @Override
    public void init(ICategoryFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

}