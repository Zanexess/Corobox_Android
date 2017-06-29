package me.labs.corobox.corobox.presenter.address_screen;

import android.app.Activity;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.network.ApiInterface;
import me.labs.corobox.corobox.view.address_screen.IAddressFragmentView;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddressFragmentPresenter implements IAddressFragmentPresenter {

    private Activity activity;
    private IAddressFragmentView view;

    @Inject
    ApiInterface apiInterface;

    @Override
    public void init(IAddressFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void deleteAddress(final Integer id) {
        apiInterface.deleteAddress(CoroboxApp.AUTH_KEY, id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("Не удалось удалить адрес. Проверьте ваше интернет соединение");
                    }

                    @Override
                    public void onNext(Response<Object> objectResponse) {
                        if (objectResponse.code() == 204) {
                            view.deleteAddress(id);
                        } else {
                            view.showToast("Не удалось удалить адрес. Проверьте ваше интернет соединение");
                        }
                    }
                });
    }

    @Override
    public void setDefaultAddress(final Integer id) {
        apiInterface.setDefaultAddress(CoroboxApp.AUTH_KEY, id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setDefaultAddress(id);
                    }

                    @Override
                    public void onNext(Response<Object> objectResponse) {
                        if (objectResponse.code() == 201) {
                            view.setDefaultAddress(id);
                        } else {
                            view.showToast("Не удалось обновить адрес по умолчанию");
                        }
                    }
                });
    }

    @Override
    public void fetchData() {
        apiInterface.getAddressList(CoroboxApp.AUTH_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<AddressModel>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        loadDataFromDatabase();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<List<AddressModel>> listResponse) {
                        if (listResponse.isSuccessful()) {
                            saveAddressList(listResponse.body());
                            view.showData(listResponse.body());
                        } else {
                            loadDataFromDatabase();
                        }
                    }
                });
    }

    @Override
    public void putAddress(AddressModel addressModel) {
        apiInterface.putAddress(CoroboxApp.AUTH_KEY, addressModel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("Не удалось отправить данные. Проверьте интернет соединение");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<Object> objectResponse) {
                        if (objectResponse.code() == 201) {
                            fetchData();
                            view.putSuccess();
                        } else {
                            view.showToast("Не удалось отправить данные. Проверьте интернет соединение");
                        }
                    }
                });

    }

    private void loadDataFromDatabase() {
        List<AddressModel> addressModels = getAddressListFromDatabase();
        if (addressModels.size() != 0) {
            view.showData(addressModels);
        } else {
            view.showEmptyData();
        }
    }

    private void saveAddressList(final List<AddressModel> body) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (AddressModel address : body) {
                    realm.copyToRealmOrUpdate(address);
                }
            }
        });
    }

    private List<AddressModel> getAddressListFromDatabase() {
        RealmResults<AddressModel> addressModels = Realm.getDefaultInstance()
                .where(AddressModel.class).findAll();
        return addressModels.subList(0, addressModels.size());
    }
}