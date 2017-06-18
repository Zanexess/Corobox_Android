package me.labs.corobox.corobox.presenter.main_screen;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.ActivityType;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.model.eventbus.UpdateCategoriesMessage;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.model.realm.common.IntegerWrap;
import me.labs.corobox.corobox.view.main_screen.IMainActivityView;
import me.labs.corobox.corobox.view.main_screen.MainActivityView;
import me.labs.corobox.corobox.view.main_screen.address_screen.AddressActivityView;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.BoxesFragmentView;
import me.labs.corobox.corobox.view.main_screen.card_screen.CardActivityView;
import me.labs.corobox.corobox.view.main_screen.categories_fragment.CategoryFragmentView;
import me.labs.corobox.corobox.view.main_screen.make_order_screen.MakeOrderActivityView;
import me.labs.corobox.corobox.view.main_screen.orders_screen.OrdersFragmentView;
import me.labs.corobox.corobox.view.main_screen.settings_fragment.SettingsFragmentView;
import me.labs.corobox.corobox.view.main_screen.terms_of_use_fragment.TermsFragmentView;

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivityView view;
    private FragmentType currentType;
    private int badgeCount = 0;
    private HashMap<String, Integer> hashMap;

    @Inject
    public MainActivityPresenter(IMainActivityView view) {
        this.view = view;
    }

    @Override
    public void changeFragment(FragmentType type) {
        if (currentType != type) {
            currentType = type;
            FragmentManager fm = ((AppCompatActivity) view.getActivity()).getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            switch (type) {
                case BOXES:
                    changeTitle(view.getActivity().getString(R.string.my_boxes_title));
                    ft.replace(R.id.frame_layout, new BoxesFragmentView());
                    setVisibilityDeliveryMenu(false);
                    break;
                case NEW_BOX:
                    changeTitle(view.getActivity().getString(R.string.sent_to_stock_title));
                    ft.replace(R.id.frame_layout, new CategoryFragmentView());
                    setVisibilityDeliveryMenu(true);
                    break;
                case TERMS:
                    changeTitle(view.getActivity().getString(R.string.terms_title));
                    ft.replace(R.id.frame_layout, new TermsFragmentView());
                    setVisibilityDeliveryMenu(false);
                    break;
                case SETTINGS:
                    changeTitle(view.getActivity().getString(R.string.settings_title));
                    ft.replace(R.id.frame_layout, new SettingsFragmentView());
                    setVisibilityDeliveryMenu(false);
                    break;
                case HISTORY:
                    changeTitle("Статус заказов");
                    ft.replace(R.id.frame_layout, new OrdersFragmentView());
                    setVisibilityDeliveryMenu(false);
                    break;
            }
            ft.commit();
        }
    }

    @Override
    public void changeActivity(ActivityType type) {
        switch (type) {
            case ADDRESS:
                Intent intent = new Intent(view.getActivity(), AddressActivityView.class);
                view.getActivity().startActivity(intent);
                break;
            case CARD:
                Intent intent1 = new Intent(view.getActivity(), CardActivityView.class);
                view.getActivity().startActivity(intent1);
                break;
        }
    }

    @Override
    public void changeTitle(String title) {
        ((MainActivityView) view).getSupportActionBar().setTitle(title);
    }

    @Override
    public void updateBadgeCounter(int i) {
        setDeliveryBadgeCount(badgeCount + i);
        view.updateMenu(i);
    }

    @Override
    public void setVisibilityDeliveryMenu(boolean isVisible) {
        view.setVisibilityDeliveryMenu(isVisible);
    }

    @Override
    public int getDeliveryBadgeCount() {
        return badgeCount;
    }

    @Override
    public void setDeliveryBadgeCount(int i) {
        this.badgeCount = i;
    }

    @Override
    public void setHashMap(HashMap<String, Integer> hashMap) {
        this.hashMap = hashMap;
    }

    @Override
    public void openCart(HashMap<String, Integer> hashMap) {
        Realm realm = Realm.getDefaultInstance();

        RealmList<Category> realmList = new RealmList<>();
        RealmList<IntegerWrap> realmList1 = new RealmList<>();
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            if (entry.getValue() != 0) {
                realmList.add(realm.where(Category.class).equalTo("category_id", entry.getKey()).findFirst());
                IntegerWrap integerWrap = new IntegerWrap();
                integerWrap.setCount(entry.getValue());
                realmList1.add(integerWrap);
            }
        }
        String uuid = UUID.randomUUID().toString();

        realm.beginTransaction();
        realm.where(OrderModelTo.class).equalTo("status", "STARTED").findAll().deleteAllFromRealm();
        realm.commitTransaction();

        OrderModelTo orderModelTo = new OrderModelTo();
        orderModelTo.setUUID(uuid);
        orderModelTo.setList(realmList);
        orderModelTo.setCount(realmList1);
        orderModelTo.setStatus("STARTED");

        realm.beginTransaction();
        realm.copyToRealm(orderModelTo);
        realm.commitTransaction();

        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            entry.setValue(0);
        }
        badgeCount = 0;
        setDeliveryBadgeCount(0);
        updateBadgeCounter(0);
        EventBus.getDefault().post(new UpdateCategoriesMessage());

        Intent intent = new Intent(view.getActivity(), MakeOrderActivityView.class);
        intent.putExtra("uuid", uuid);
        view.getActivity().startActivity(intent);
    }

    @Override
    public HashMap<String, Integer> getHashMap() {
        return hashMap;
    }

    @Override
    public FragmentType getCurrentType() {
        return currentType;
    }
}