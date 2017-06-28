package me.labs.corobox.corobox.presenter.main_screen.orders_screen;

import android.app.Activity;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.network.ApiInterface;
import me.labs.corobox.corobox.view.main_screen.orders_screen.IOrdersFragmentView;
import retrofit2.Response;
import retrofit2.http.PUT;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrdersToFragmentPresenter implements IOrdersFragmentPresenter {

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
        apiInterface.getOrderTo(CoroboxApp.AUTH_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<OrderModelTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadDataFromDatabase();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<List<OrderModelTo>> listResponse) {
                        if (listResponse.isSuccessful()) {
                            saveAddressList(listResponse.body());
                            view.showDataTo(listResponse.body());
                        } else {
                            loadDataFromDatabase();
                        }
                    }
                });
    }

    private void loadDataFromDatabase() {
        List<OrderModelTo> ordersTo = getOrdersToListFromDatabase();
        if (ordersTo.size() != 0) {
            view.showDataTo(ordersTo);
        } else {
            view.showEmptyData();
        }
    }

    private void saveAddressList(final List<OrderModelTo> body) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (OrderModelTo order : body) {
                    realm.copyToRealmOrUpdate(order);
                }
            }
        });
    }

    private List<OrderModelTo> getOrdersToListFromDatabase() {
        RealmResults<OrderModelTo> orderTo = Realm.getDefaultInstance()
                .where(OrderModelTo.class).findAll();
        return orderTo.subList(0, orderTo.size());
    }

    public void cancelOrderTo(String uuid) {
        apiInterface.cancelOrderTo(CoroboxApp.AUTH_KEY, uuid)
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