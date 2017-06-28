package me.labs.corobox.corobox.presenter.make_order_screen;

import android.app.Activity;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.realm.RealmObject;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.common.FragmentType;
import me.labs.corobox.corobox.common.Utils.Utils;
import me.labs.corobox.corobox.common.serializers.AddressSerializer;
import me.labs.corobox.corobox.common.serializers.BoxSerializer;
import me.labs.corobox.corobox.common.serializers.CategoryNumberModelSerializer;
import me.labs.corobox.corobox.common.serializers.OrderModelFromSerializer;
import me.labs.corobox.corobox.common.serializers.OrderModelToSerializer;
import me.labs.corobox.corobox.model.realm.CategoryNumberModel;
import me.labs.corobox.corobox.model.realm.OrderModelFrom;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.network.ApiInterface;
import me.labs.corobox.corobox.view.make_order_screen.IMakeOrderActivityView;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MakeOrderActivityPresenter implements IMakeOrderActivityPresenter {

    private IMakeOrderActivityView view;
    private FragmentType currentType;
    private Activity activity;

    @Inject ApiInterface apiInterface;

    private long date_till;
    private long date_from;

    @Inject
    public MakeOrderActivityPresenter(IMakeOrderActivityView view) {
        this.view = view;
        this.activity = view.getActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void countAll(List<CategoryNumberModel> data, long numDays) {
        long c = 0;
        for (int i = 0; i < data.size(); i++) {
            c += data.get(i).getCategory().getDaily_price() * data.get(i).getNumber() * numDays;
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

    @Override
    public void setTill(String toString, String fromString) {
        try {
            date_from = Utils.dateToTimestamp(Utils.stringToDate(toString, "dd.MM.yyyy", Locale.getDefault()));
            date_till = Utils.dateToTimestamp(Utils.stringToDate(fromString, "dd.MM.yyyy", Locale.getDefault()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long countDays() {
        return (date_till - date_from) / 24 / 60 / 60;
    }
}