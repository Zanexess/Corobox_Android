package me.labs.corobox.corobox.common.adapters;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.presenter.main_screen.address_screen.IAddressFragmentPresenter;

public class AddressRealmAdapter extends RealmRecyclerViewAdapter<AddressModel, RecyclerView.ViewHolder> {

    private OrderedRealmCollection<AddressModel> addressModels;
    private IAddressFragmentPresenter presenter;

    public AddressRealmAdapter(@Nullable OrderedRealmCollection<AddressModel> data, boolean autoUpdate, IAddressFragmentPresenter presenter) {
        super(data, autoUpdate);
        this.addressModels = data;
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new AddressHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AddressHolder)holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return addressModels.size();
    }

    private class AddressHolder extends RecyclerView.ViewHolder {

        private TextView number;
        private TextView date;
        private ImageView defaultCard;

        private AddressHolder(final View itemView) {
            super(itemView);
            number = (TextView) itemView.findViewById(R.id.card_number);
            date = (TextView) itemView.findViewById(R.id.date);
            defaultCard = (ImageView) itemView.findViewById(R.id.defaultCard);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Удалить адрес")
                            .setMessage("Вы действительно хотите удалить этот адрес?")
                            .setCancelable(false)
                            .setPositiveButton("Да, удалить", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    presenter.deleteAddress(addressModels.get(getAdapterPosition()).getId());
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
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.setDefaultAddress(addressModels.get(getAdapterPosition()).getId());
                }
            });
        }

        private void bind(int position) {
            number.setText(addressModels.get(position).getAddress());
            date.setText("Квартира " + addressModels.get(position).getFlat());
            if (addressModels.get(position).isUseAsDefault()) {
                defaultCard.setVisibility(View.VISIBLE);
            } else {
                defaultCard.setVisibility(View.GONE);
            }
        }
    }
}
