package me.labs.corobox.corobox.presenter.profile_screen;

import android.app.Activity;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.model.realm.ProfileModel;
import me.labs.corobox.corobox.network.ApiInterface;
import me.labs.corobox.corobox.view.profile_screen.IProfileFragmentView;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ProfileFragmentPresenter implements IProfileFragmentPresenter {

    private Activity activity;
    private IProfileFragmentView view;

    @Inject ApiInterface apiInterface;

    @Override
    public void init(IProfileFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void fetchData() {
        apiInterface.getProfile(CoroboxApp.AUTH_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<ProfileModel>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<List<ProfileModel>> profileModelResponse) {
                        if (profileModelResponse.isSuccessful()) {
                            if (profileModelResponse.body().size() > 0)
                                view.showProfileModel(profileModelResponse.body().get(0));
                        }
                    }
                });
    }

    @Override
    public void putProfile(ProfileModel profileModel) {
        apiInterface.putProfile(CoroboxApp.AUTH_KEY, "1", profileModel)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Object>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<Object> objectResponse) {
                        if (objectResponse.code() == 201) {
                            fetchData();
                            Toast.makeText(activity, "Данные изменены", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
