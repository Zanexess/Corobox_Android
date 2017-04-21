package me.labs.corobox.corobox.presenter.make_order_screen;

import io.realm.OrderedRealmCollection;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.common.IntegerWrap;

public interface IMakeOrderActivityPresenter {

    void countAll(OrderedRealmCollection<Category> data, OrderedRealmCollection<IntegerWrap> count);
    void updateList();
    void finish();
}