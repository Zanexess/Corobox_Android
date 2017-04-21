package me.labs.corobox.corobox.common.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.OrderModelTo;

public class OrderRealmAdapter extends RealmRecyclerViewAdapter<OrderModelTo, RecyclerView.ViewHolder> {

    private OrderedRealmCollection<OrderModelTo> orders;


    public OrderRealmAdapter(@Nullable OrderedRealmCollection<OrderModelTo> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.orders = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((OrderHolder)holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    private class OrderHolder extends RecyclerView.ViewHolder {

        private TextView number;
        private TextView date;
        private RecyclerView recyclerView;
        private TextView address;

        private OrderHolder(View itemView) {
            super(itemView);
            number = (TextView) itemView.findViewById(R.id.number);
            date = (TextView) itemView.findViewById(R.id.date);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            address = (TextView) itemView.findViewById(R.id.address);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));

        }

        private void bind(int position) {
            number.setText("Заказ # " + orders.get(getAdapterPosition()).getUUID().substring(0, 7));
            date.setText(orders.get(getAdapterPosition()).getDate());
            AddressModel addressModel = orders.get(getAdapterPosition()).getAddressModel();
            try {
                address.setText(addressModel.getStreet() + " кв." + addressModel.getFlat());
            } catch (Exception e) {

            }
            recyclerView.setAdapter(new CategoriesImagesAdapter(orders.get(getAdapterPosition()).getList().createSnapshot(), false));
        }
    }
}
