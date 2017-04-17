package me.labs.corobox.corobox.view.main_screen.terms_of_use_screen;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.CategoriesAdapter;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.model.Category;
import me.labs.corobox.corobox.presenter.main_screen.IMainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.categories_fragment.ICategoryFragmentPresenter;
import me.labs.corobox.corobox.presenter.main_screen.terms_of_use.ITermsFragmentPresenter;
import me.labs.corobox.corobox.view.main_screen.categories_fragment.ICategoryFragmentView;

public class TermsFragmentView extends BaseFragment implements ITermsFragmentView {

    @Inject
    ITermsFragmentPresenter presenter;

    private View view;
    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_terms, container, false);
            initComponents();
        }
        return view;
    }

    private void initComponents() {
        webView = (WebView) view.findViewById(R.id.webView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
        presenter.init(this);
        presenter.openTerms();
    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

    @Override
    public void openTerms() {
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.loadUrl("file:///android_asset/terms.html");
    }
}
