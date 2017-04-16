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
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.CategoriesAdapter;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.model.Category;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
//                categoriesAdapter.getFilter().filter(newText);
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

        categories.add(new Category("Рюкзак", 200, "Bag", R.drawable.bag_q));
        categories.add(new Category("Сноуборд", 250, "Sbowboards", R.drawable.snowboard_q));
        categories.add(new Category("Велосипед", 300, "Bycicle", R.drawable.bycicle_q));
        categories.add(new Category("Шины", 280, "Wheels", R.drawable.wheel_q));
        categories.add(new Category("Лыжи", 200, "Skiing", R.drawable.skiing_q));

        categoriesAdapter = new CategoriesAdapter(categories, presenter, presenterActivity);
        recyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }
}
