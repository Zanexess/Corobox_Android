package me.labs.corobox.corobox.common.adapters;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.AddressModel;
import me.labs.corobox.corobox.presenter.address_screen.IAddressFragmentPresenter;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);
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

    class AddressHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.address) TextView address;
        @BindView(R.id.access)  TextView access;
        @BindView(R.id.floor) TextView floor;
        @BindView(R.id.flat) TextView flat;
        @BindView(R.id.delete_address) ImageView deleteAddress;
        @BindView(R.id.home_address) ImageView homeAddress;
        @BindView(R.id.default_layout) LinearLayout default_layout;

        private AddressHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            deleteAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                }
            });

            homeAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.setDefaultAddress(addressModels.get(getAdapterPosition()).getId());
                }
            });
        }

        private void bind(int position) {
            AddressModel addressModel = addressModels.get(getAdapterPosition());

            if (!addressModel.isUseAsDefault()) {
                default_layout.setVisibility(View.GONE);
                homeAddress.setVisibility(View.VISIBLE);
                itemView.setBackgroundColor(Color.WHITE);
            } else {
                default_layout.setVisibility(View.VISIBLE);
                itemView.setBackgroundColor(Color.parseColor("#40808080"));
                homeAddress.setVisibility(View.GONE);
            }

            address.setText(addressModel.getAddress());
            access.setText(addressModel.getAccess());
            flat.setText(addressModel.getFlat());
            floor.setText(addressModel.getFloor());
        }
    }
}
