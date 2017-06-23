package me.labs.corobox.corobox.view.main_screen.orders_screen;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.ViewPagerAdapter;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.model.realm.OrderModelFrom;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.presenter.main_screen.IMainActivityPresenter;
import me.labs.corobox.corobox.presenter.main_screen.orders_screen.IOrdersFragmentPresenter;

public class OrdersFragmentView extends BaseFragment implements IOrdersFragmentView {

    @Inject
    IOrdersFragmentPresenter presenter;

    @Inject
    IMainActivityPresenter presenterActivity;

    private View view;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_orders, container, false);
            initComponents();
        }
        return view;
    }

    private void initComponents() {


        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(((AppCompatActivity)provideActivity()).getSupportFragmentManager());
        adapter.addFragment(OrderFragmentTo.newInstance(), "На склад");
        adapter.addFragment(OrderFragmentFrom.newInstance(), "Со склада");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
        presenter.init(this);


    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

    @Override
    public void showDataTo(List<OrderModelTo> ordersTo) {

    }

    @Override
    public void showEmptyData() {

    }

    @Override
    public void showDataFrom(List<OrderModelFrom> ordersFrom) {

    }

}
