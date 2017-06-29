package me.labs.corobox.corobox.presenter.main_screen.boxes_fragment;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.network.ApiInterface;
import me.labs.corobox.corobox.view.main_screen.MainActivityView;
import me.labs.corobox.corobox.view.main_screen.boxes_fragment.IBoxesFragmentView;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BoxesFragmentPresenter implements IBoxesFragmentPresenter {

    private Activity activity;
    private IBoxesFragmentView view;

    @Inject ApiInterface apiInterface;

    @Override
    public void init(IBoxesFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void readyForOrder(HashSet<String> selected) {
        if (selected.size() != 0) {
            ((MainActivityView) activity).setVisibilityToolbarIcon(true);
        } else {
            ((MainActivityView) activity).setVisibilityToolbarIcon(false);
        }
    }

    @Override
    public void fetchData() {
        apiInterface.getStuff(CoroboxApp.AUTH_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Box>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadStuffFromDatabase();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Response<List<Box>> stuffResponse) {
                        if (stuffResponse.isSuccessful()) {
                            view.showData(stuffResponse.body());
                            saveStuffResults(stuffResponse.body());
                        } else {
                            loadStuffFromDatabase();
                        }
                    }
                });
    }

    @Override
    public void deliveryClicked() {
        view.deliveryClicked();
    }

    private void saveStuffResults(final List<Box> body) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Box stuff : body) {
                    realm.copyToRealmOrUpdate(stuff);
                }
            }
        });
    }

    private void loadStuffFromDatabase() {
        List<Box> addressModels = getBoxListFromDatabase();
        if (addressModels.size() != 0) {
            view.showData(addressModels);
        } else {
            view.showEmptyData();
        }
    }

    private List<Box> getBoxListFromDatabase() {
        RealmResults<Box> stuff = Realm.getDefaultInstance()
                .where(Box.class).findAll();
        return stuff.subList(0, stuff.size());
    }

}