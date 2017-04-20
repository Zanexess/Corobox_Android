package me.labs.corobox.corobox.presenter.main_screen.orders_screen;

import android.app.Activity;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.view.main_screen.orders_screen.IOrdersFragmentView;

public class OrdersFragmentPresenter implements IOrdersFragmentPresenter {

    private Activity activity;
    private IOrdersFragmentView view;

    @Override
    public void init(IOrdersFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

}