package me.labs.corobox.corobox.presenter.make_order_screen;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmObject;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.common.serializers.AddressSerializer;
import me.labs.corobox.corobox.common.serializers.BoxSerializer;
import me.labs.corobox.corobox.common.serializers.CategoryNumberModelSerializer;
import me.labs.corobox.corobox.common.serializers.OrderModelFromSerializer;
import me.labs.corobox.corobox.common.serializers.OrderModelToSerializer;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.CategoryNumberModel;
import me.labs.corobox.corobox.model.realm.OrderModelFrom;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.model.realm.common.IntegerWrap;
import me.labs.corobox.corobox.network.ApiInterface;
import me.labs.corobox.corobox.presenter.main_screen.address_screen.IAddressActivityPresenter;
import me.labs.corobox.corobox.view.main_screen.address_screen.AddressFragmentView;
import me.labs.corobox.corobox.view.main_screen.address_screen.IAddressActivityView;
import me.labs.corobox.corobox.view.main_screen.make_order_screen.IMakeOrderActivityView;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MakeOrderActivityPresenter implements IMakeOrderActivityPresenter {

    private IMakeOrderActivityView view;
    private FragmentType currentType;
    private Activity activity;

    @Inject ApiInterface apiInterface;

    @Inject
    public MakeOrderActivityPresenter(IMakeOrderActivityView view) {
        this.view = view;
        this.activity = view.getActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void countAll(OrderedRealmCollection<CategoryNumberModel> data) {
        Integer c = 0;
        for (int i = 0; i < data.size(); i++) {
            c += data.get(i).getCategory().getPrice() * data.get(i).getNumber();
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

    @Override
    public void putOrder(final OrderModelTo orderModel) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        try {
            gsonBuilder.registerTypeAdapter(Class.forName("io.realm.AddressModelRealmProxy"), new AddressSerializer());
            gsonBuilder.registerTypeAdapter(Class.forName("io.realm.OrderModelToRealmProxy"), new OrderModelToSerializer());
            gsonBuilder.registerTypeAdapter(Class.forName("io.realm.CategoryNumberModelRealmProxy"), new CategoryNumberModelSerializer());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson =  gsonBuilder.create();

        apiInterface.putOrderTo(CoroboxApp.AUTH_KEY, gson.toJson(orderModel))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("Произошла ошибка, проверьте ваше интернет соединение");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<Object> objectResponse) {
                        if (objectResponse.code() == 201) {
                            view.orderSuccessfullyAdded();
                        } else {
                            view.showToast(objectResponse.code() + "");
                        }
                    }
                });
    }

    @Override
    public void putOrderFrom(OrderModelFrom orderModelFrom) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        try {
            gsonBuilder.registerTypeAdapter(Class.forName("io.realm.AddressModelRealmProxy"), new AddressSerializer());
            gsonBuilder.registerTypeAdapter(Class.forName("io.realm.OrderModelFromRealmProxy"), new OrderModelFromSerializer());
            gsonBuilder.registerTypeAdapter(Class.forName("io.realm.BoxRealmProxy"), new BoxSerializer());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson =  gsonBuilder.create();

        apiInterface.putOrderFrom(CoroboxApp.AUTH_KEY, gson.toJson(orderModelFrom))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showToast("Произошла ошибка, проверьте ваше интернет соединение");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<Object> objectResponse) {
                        if (objectResponse.code() == 201) {
                            view.orderSuccessfullyAdded();
                        } else {
                            view.showToast(objectResponse.code() + "");
                        }
                    }
                });
    }
}