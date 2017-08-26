package me.labs.corobox.corobox.presenter.main_screen.orders_screen;

import android.app.Activity;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.model.realm.OrderModelFrom;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.network.ApiInterface;
import me.labs.corobox.corobox.view.main_screen.orders_screen.IOrdersFragmentView;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrdersFromFragmentPresenter implements IOrdersFragmentPresenter {

    private Activity activity;
    private IOrdersFragmentView view;

    @Inject ApiInterface apiInterface;

    @Override
    public void init(IOrdersFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void fetchData() {
        apiInterface.getOrderFrom(CoroboxApp.AUTH_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<OrderModelFrom>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadDataFromDatabase();
                    }

                    @Override
                    public void onNext(Response<List<OrderModelFrom>> listResponse) {
                        if (listResponse.isSuccessful()) {
                            saveOrderFromList(listResponse.body());
                            if (listResponse.body().size() > 0) {
                                view.showDataFrom(listResponse.body());
                            } else {
                                loadDataFromDatabase();
                            }
                        } else {
                            loadDataFromDatabase();
                        }
                    }
                });
    }

    private void loadDataFromDatabase() {
        List<OrderModelFrom> ordersFrom = getOrdersFromListFromDatabase();
        if (ordersFrom.size() > 0) {
            view.showDataFrom(ordersFrom);
        } else {
            view.showEmptyData();
        }
    }

    private void saveOrderFromList(final List<OrderModelFrom> body) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (OrderModelFrom order : body) {
                    realm.copyToRealmOrUpdate(order);
                }
            }
        });
    }

    private List<OrderModelFrom> getOrdersFromListFromDatabase() {
        RealmResults<OrderModelFrom> orderFrom = Realm.getDefaultInstance()
                .where(OrderModelFrom.class).findAll();
        return orderFrom.subList(0, orderFrom.size());
    }

    public void cancelOrderFrom(String uuid) {
        apiInterface.cancelOrderFrom(CoroboxApp.AUTH_KEY, uuid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("Не удалось отменить заказ");
                    }

                    @Override
                    public void onNext(Response<Object> objectResponse) {
                        if (objectResponse.code() == 204) {
                            view.cancellSuccess();
                        } else {
                            view.showToast("Не удалось отменить заказ");
                        }
                    }
                });
    }
}