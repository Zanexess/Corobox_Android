package me.labs.corobox.corobox.presenter.make_order_screen;

import java.util.List;

import io.realm.OrderedRealmCollection;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.CategoryNumberModel;
import me.labs.corobox.corobox.model.realm.OrderModelFrom;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.model.realm.common.IntegerWrap;

public interface IMakeOrderActivityPresenter {
    void countAll(OrderedRealmCollection<CategoryNumberModel> data);
    void updateList();
    void finish();
    void putOrder(OrderModelTo orderModel);
    void putOrderFrom(OrderModelFrom orderModelFrom);
}