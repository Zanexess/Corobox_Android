package me.labs.corobox.corobox.view.main_screen.orders_screen;

import android.app.Activity;

import java.util.List;

import me.labs.corobox.corobox.model.realm.OrderModelFrom;
import me.labs.corobox.corobox.model.realm.OrderModelTo;

public interface IOrdersFragmentView {
    Activity provideActivity();
    void showDataTo(List<OrderModelTo> ordersTo);
    void showEmptyData();
    void showDataFrom(List<OrderModelFrom> ordersFrom);
    void cancellSuccess();
    void showToast(String s);
}
