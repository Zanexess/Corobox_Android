package me.labs.corobox.corobox.common.adapters;

import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.common.Utils.Utils;
import me.labs.corobox.corobox.model.eventbus.UpdateOrdersMessage;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.model.realm.OrderModelFrom;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.presenter.main_screen.orders_screen.OrdersFromFragmentPresenter;

public class OrderFromAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderModelFrom> orders;
    private OrdersFromFragmentPresenter presenter;

    public OrderFromAdapter(List<OrderModelFrom> data, OrdersFromFragmentPresenter presenter) {
        this.orders = data;
        this.presenter = presenter;
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
        @BindView(R.id.cancel_button) ImageView cancel;

        private OrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Отменить заказ.")
                            .setMessage("Вы действительно хотите отменить этот заказ?")
                            .setCancelable(false)
                            .setPositiveButton("Да, отменить", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    presenter.cancelOrderFrom(orders.get(getAdapterPosition()).getUUID());
                                }
                            })
                            .setNegativeButton("Нет",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }

        private void bind(int position) {
            number.setText("Заказ №" + orders.get(getAdapterPosition()).getOrder_id().toString());
            date.setText(Utils.getDate(orders.get(getAdapterPosition()).getTill() * 1000));
            status.setText(Utils.getStatus(orders.get(getAdapterPosition()).getStatus()));

            if (orders.get(getAdapterPosition()).getStatus().equals("pending")) {
                cancel.setVisibility(View.VISIBLE);
            } else {
                cancel.setVisibility(View.GONE);
            }

            AddressModel addressModel = orders.get(getAdapterPosition()).getAddressModel();
            try {
                address.setText(addressModel.getAddress() + " кв." + addressModel.getFlat());
            } catch (Exception e) {

            }
            recyclerView.setAdapter(new BoxImagesAdapter(orders.get(getAdapterPosition()).getCategoryNumberModel().subList(0, orders.get(getAdapterPosition()).getCategoryNumberModel().size())));
        }
    }
}
