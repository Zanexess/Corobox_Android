package me.labs.corobox.corobox.view.main_screen.categories_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import io.realm.Realm;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.CategoriesAdapter;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.model.eventbus.UpdateCategoriesMessage;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.presenter.main_screen.IMainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.categories_fragment.ICategoryFragmentPresenter;

public class CategoryFragmentView extends BaseFragment implements ICategoryFragmentView {

    @Inject
    ICategoryFragmentPresenter presenter;

    @Inject
    IMainActivityPresenter presenterActivity;

    private View view;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private CategoriesAdapter categoriesAdapter;
    private EventBus bus = EventBus.getDefault();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (!bus.isRegistered(this)) {
            bus.register(this);
        }
    }

    @Subscribe
    public void onMessage(UpdateCategoriesMessage message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                categoriesAdapter.notifyDataSetChanged();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_category, container, false);
            initComponents();
        }
        return view;
    }

    private void initComponents() {
        searchView = (SearchView) view.findViewById(R.id.search_view);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                categoriesAdapter.getFilter().filter(newText);
                return true;
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager slm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(slm);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
        presenter.init(this);

        ArrayList<Category> categories = new ArrayList<>();

        final Category category1 = new Category();
        category1.setTitle("Рюкзак");
        category1.setPrice(200);
        category1.setId("Bag");
        category1.setPicture("bag_q.png");

        final Category category2 = new Category();
        category2.setTitle("Велосипед");
        category2.setPrice(300);
        category2.setId("Bycicle");
        category2.setPicture("bycicle_q.png");

        final Category category3 = new Category();
        category3.setTitle("Сноуборд");
        category3.setPrice(250);
        category3.setId("Snowboard");
        category3.setPicture("snowboard_q.png");

        final Category category4 = new Category();
        category4.setTitle("Шины");
        category4.setPrice(280);
        category4.setId("Wheels");
        category4.setPicture("wheel_q.png");

        final Category category5 = new Category();
        category5.setTitle("Лыжи");
        category5.setPrice(200);
        category5.setId("Skiing");
        category5.setPicture("skiing_q.png");

        final Category category6 = new Category();
        category6.setTitle("Обувь");
        category6.setPrice(100);
        category6.setId("Shoes");
        category6.setPicture("shoes_q.png");

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(category1);
                realm.copyToRealmOrUpdate(category2);
                realm.copyToRealmOrUpdate(category3);
                realm.copyToRealmOrUpdate(category4);
                realm.copyToRealmOrUpdate(category5);
                realm.copyToRealmOrUpdate(category6);
            }
        });

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);
        categories.add(category6);

        HashMap<String, Integer> hashMap = new HashMap<>();
        for (Category category : categories) {
            hashMap.put(category.getId(), 0);
        }
        presenterActivity.setHashMap(hashMap);

        categoriesAdapter = new CategoriesAdapter(categories, hashMap, presenter, presenterActivity);
        recyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bus.isRegistered(this)) {
            bus.unregister(this);
        }
        categoriesAdapter.clearAll();
        presenterActivity.setDeliveryBadgeCount(0);
    }
}
