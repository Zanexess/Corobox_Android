package me.labs.corobox.corobox.presenter.main_screen.categories_fragment;

import java.util.List;

import me.labs.corobox.corobox.common.BaseFragmentPresenter;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.view.main_screen.categories_fragment.ICategoryFragmentView;
import retrofit2.Response;
import rx.Observable;

public interface ICategoryFragmentPresenter extends BaseFragmentPresenter<ICategoryFragmentView> {
    void fetchData();
}


