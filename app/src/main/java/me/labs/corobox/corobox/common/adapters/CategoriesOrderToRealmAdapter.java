package me.labs.corobox.corobox.common.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.CategoryNumberModel;
import me.labs.corobox.corobox.model.realm.OrderModelTo;
import me.labs.corobox.corobox.presenter.make_order_screen.IMakeOrderActivityPresenter;

public class CategoriesOrderToRealmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final IMakeOrderActivityPresenter presenter;
    private final OrderModelTo orderModel;
    private List<CategoryNumberModel> categoryNumberModels;

    public CategoriesOrderToRealmAdapter(OrderModelTo orderModel, IMakeOrderActivityPresenter presenter, boolean autoUpdate) {
        this.categoryNumberModels = orderModel.getCategoryNumberModel().createSnapshot();
        this.presenter = presenter;
        this.orderModel = orderModel;
        presenter.countAll(orderModel.getCategoryNumberModel().createSnapshot(), presenter.countDays());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_category_item, parent, false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CategoryHolder)holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return categoryNumberModels.size();
    }

    private class CategoryHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView image;
        ImageView plus;
        ImageView minus;
        TextView title;
        TextView price;
        TextView number;

        private CategoryHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            plus = (ImageView) itemView.findViewById(R.id.plus);
            minus = (ImageView) itemView.findViewById(R.id.minus);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            number = (TextView) itemView.findViewById(R.id.count);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        categoryNumberModels.get(getAdapterPosition()).setNumber(categoryNumberModels.get(getAdapterPosition()).getNumber() + 1);
                        realm.commitTransaction();
                        presenter.countAll(categoryNumberModels, presenter.countDays());
                        presenter.updateList();
                    } catch (Exception e) {

                    }
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    Integer counter = categoryNumberModels.get(getAdapterPosition()).getNumber();
                    categoryNumberModels.get(getAdapterPosition()).setNumber(counter - 1);


                    if (categoryNumberModels.get(getAdapterPosition()).getNumber() == 0) {
                        categoryNumberModels.get(getAdapterPosition()).deleteFromRealm();
                        categoryNumberModels = orderModel.getCategoryNumberModel().createSnapshot();
                        notifyItemRemoved(getAdapterPosition());

                        if (categoryNumberModels.size() == 0) {
                            presenter.finish();
                        }
                    }

                    realm.commitTransaction();
                    presenter.countAll(categoryNumberModels, presenter.countDays());
                    presenter.updateList();

                }
            });

        }

        private void bind(int position) {
            Category category = categoryNumberModels.get(position).getCategory();
            Integer counter = categoryNumberModels.get(position).getNumber();

            Picasso.with(itemView.getContext())
                    .load(category.getPicture())
                    .error(R.drawable.error_placeholder)
                    .into(image);

            title.setText(category.getTitle());
            price.setText(String.valueOf(category.getPrice() + "р месяц"));
            number.setText(String.valueOf(counter) + " шт.");

            if (counter == 0) {
                itemView.setVisibility(View.GONE);
            } else {
                itemView.setVisibility(View.VISIBLE);
            }
        }


    }
}
