package me.labs.corobox.corobox.common.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.internal.Util;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.Utils.Utils;
import me.labs.corobox.corobox.model.eventbus.UpdateOrdersMessage;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.OrderModelTo;

public class OrderToAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderModelTo> orders;

    public OrderToAdapter(List<OrderModelTo> data) {
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

    class OrderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.number) TextView number;
        @BindView(R.id.date) TextView date;
        @BindView(R.id.recyclerView) RecyclerView recyclerView;
        @BindView(R.id.address) TextView address;
        @BindView(R.id.status) TextView status;

        private OrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.where(OrderModelTo.class).equalTo("UUID", orders.get(getAdapterPosition()).getUUID()).findAll().deleteAllFromRealm();
                    realm.commitTransaction();
                    EventBus.getDefault().post(new UpdateOrdersMessage());
                    return true;
                }
            });
        }

        private void bind(int position) {
            number.setText("Заказ №" + orders.get(getAdapterPosition()).getOrderId().toString());
            date.setText(Utils.getDate(orders.get(getAdapterPosition()).getTill() * 1000));
            status.setText(Utils.getStatus(orders.get(getAdapterPosition()).getStatus()));
            AddressModel addressModel = orders.get(getAdapterPosition()).getAddressModel();
            try {
                address.setText(addressModel.getAddress() + " кв." + addressModel.getFlat());
            } catch (Exception e) {

            }
            recyclerView.setAdapter(new CategoriesImagesAdapter(orders.get(getAdapterPosition()).getCategoryNumberModel().subList(0, orders.get(getAdapterPosition()).getCategoryNumberModel().size())));
        }
    }
}
