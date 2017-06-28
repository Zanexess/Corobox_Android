package me.labs.corobox.corobox.view.main_screen.categories_fragment;

import android.app.Activity;

import java.util.List;

import me.labs.corobox.corobox.model.realm.Category;

public interface ICategoryFragmentView {
    Activity provideActivity();
    void showData(List<Category> categoryList);
    void showEmptyData();
}
