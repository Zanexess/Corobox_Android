package me.labs.corobox.corobox.presenter.make_order_screen;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import io.realm.OrderedRealmCollection;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.common.IntegerWrap;
import me.labs.corobox.corobox.presenter.main_screen.address_screen.IAddressActivityPresenter;
import me.labs.corobox.corobox.view.main_screen.address_screen.AddressFragmentView;
import me.labs.corobox.corobox.view.main_screen.address_screen.IAddressActivityView;
import me.labs.corobox.corobox.view.main_screen.make_order_screen.IMakeOrderActivityView;

public class MakeOrderActivityPresenter implements IMakeOrderActivityPresenter {

    private IMakeOrderActivityView view;
    private FragmentType currentType;

    @Inject
    public MakeOrderActivityPresenter(IMakeOrderActivityView view) {
        this.view = view;
    }

    @Override
    public void countAll(OrderedRealmCollection<Category> data, OrderedRealmCollection<IntegerWrap> count) {
        Integer c = 0;
        for (int i = 0; i < data.size(); i++) {
            c += data.get(i).getPrice() * count.get(i).getCount();
        }
        String counter = String.valueOf(c);
        view.showPrice(counter);
    }

    @Override
    public void updateList() {
        view.updateList();
    }

    @Override
    public void finish() {
        view.finishActivity();
    }
}