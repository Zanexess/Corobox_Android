package me.labs.corobox.corobox.presenter.main_screen.categories_fragment;

import android.app.Activity;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import me.labs.corobox.corobox.app.CoroboxApp;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.network.ApiInterface;
import me.labs.corobox.corobox.view.main_screen.categories_fragment.ICategoryFragmentView;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CategoryFragmentPresenter implements ICategoryFragmentPresenter {

    private Activity activity;
    private ICategoryFragmentView view;

    @Inject
    ApiInterface apiInterface;

    @Override
    public void init(ICategoryFragmentView view) {
        this.view = view;
        this.activity = view.provideActivity();
        CoroboxApp.get(activity).getApiComponent().inject(this);
    }

    @Override
    public void fetchData() {
        apiInterface.getCategories("Token e4668dfe95fab640344fe580f543ef3e56d3a7c5")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<List<Category>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadDataFromDatabase();
                    }

                    @Override
                    public void onNext(Response<List<Category>> listResponse) {
                        if (listResponse.isSuccessful()) {
                            view.showData(listResponse.body());
                            saveCategoriesData(listResponse.body());
                        } else {
                            loadDataFromDatabase();
                        }
                    }
                });
    }

    private void loadDataFromDatabase() {
        List<Category> categories = getCategoryFromDatabase();
        if (categories.size() != 0) {
            view.showData(categories);
        } else {
            view.showEmptyData();
        }
    }

    private List<Category> getCategoryFromDatabase() {
        RealmResults<Category> categories = Realm.getDefaultInstance()
                .where(Category.class).findAll();
        return categories.subList(0, categories.size());
    }

    private void saveCategoriesData(final List<Category> categories) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Category category : categories) {
                    realm.copyToRealmOrUpdate(category);
                }
            }
        });
    }
}