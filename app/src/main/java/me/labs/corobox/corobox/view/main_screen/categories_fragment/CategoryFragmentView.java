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
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.CategoriesAdapter;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.model.eventbus.UpdateCategoriesMessage;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.presenter.main_screen.IMainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.categories_fragment.ICategoryFragmentPresenter;

public class CategoryFragmentView extends BaseFragment implements ICategoryFragmentView {

    @Inject ICategoryFragmentPresenter presenter;
    @Inject IMainActivityPresenter presenterActivity;

    private View view;

    @BindView(R.id.search_view) SearchView searchView;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

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
            ButterKnife.bind(this, view);
            initComponents();
        }
        return view;
    }

    private void initComponents() {
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

        searchView.setIconified(true);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setQuery("", false);
                return true;
            }
        });

        StaggeredGridLayoutManager slm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(slm);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
        presenter.init(this);
        presenter.fetchData();
    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

    @Override
    public void showData(final List<Category> categories) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (Category category : categories) {
            hashMap.put(category.getCategory_id(), 0);
        }

        presenterActivity.setHashMap(hashMap);

        categoriesAdapter = new CategoriesAdapter(categories, hashMap, presenter, presenterActivity);
        recyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public void showEmptyData() {
        Toast.makeText(getContext(), "Данные не получены. Проверьте ваше интернет соединение", Toast.LENGTH_SHORT).show();
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
