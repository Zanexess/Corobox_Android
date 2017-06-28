package me.labs.corobox.corobox.view.main_screen.orders_screen;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.BaseFragment;
import me.labs.corobox.corobox.common.adapters.OrderToAdapter;
import me.labs.corobox.corobox.di.components.activities.IMainActivityComponent;
import me.labs.corobox.corobox.model.eventbus.UpdateOrdersMessage;
import me.labs.corobox.corobox.model.realm.OrderModelFrom;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.presenter.main_screen.orders_screen.OrdersToFragmentPresenter;

public class OrderFragmentTo extends BaseFragment implements IOrdersFragmentView {

    private View view;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.no_stuff) TextView textView;

    private OrderToAdapter adapter;

    @Inject OrdersToFragmentPresenter presenter;

    public static OrderFragmentTo newInstance() {
        Bundle args = new Bundle();
        OrderFragmentTo fragment = new OrderFragmentTo();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_order, container, false);
            ButterKnife.bind(this, view);
            initComponents();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
        presenter.init(this);
        presenter.fetchData();
    }

    private void initComponents() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

    @Override
    public void showDataTo(List<OrderModelTo> ordersTo) {
        adapter = new OrderToAdapter(ordersTo, presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyData() {
        recyclerView.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDataFrom(List<OrderModelFrom> ordersFrom) {

    }

    @Override
    public void cancellSuccess() {
        presenter.fetchData();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
