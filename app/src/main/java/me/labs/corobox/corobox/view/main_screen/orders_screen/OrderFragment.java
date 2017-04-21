package me.labs.corobox.corobox.view.main_screen.orders_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.adapters.OrderRealmAdapter;
import me.labs.corobox.corobox.model.realm.OrderModelTo;

public class OrderFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private TextView textView;

    public static OrderFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString("type", type);

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_order, container, false);
            initComponents();
        }
        return view;
    }

    private void initComponents() {
        String type = getArguments().getString("type");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        textView = (TextView) view.findViewById(R.id.no_stuff);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        if (type.equals("FROM")) {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<OrderModelTo> orderModelTos = realm.where(OrderModelTo.class).equalTo("status", "ORDERED").equalTo("type", "FROM").findAll();
            recyclerView.setAdapter(new OrderRealmAdapter(orderModelTos.createSnapshot(), false));
        } else {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }


}
