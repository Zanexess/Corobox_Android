package me.labs.corobox.corobox.common.adapters;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.CardModel;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.CategoryNumberModel;
import me.labs.corobox.corobox.model.realm.common.IntegerWrap;
import me.labs.corobox.corobox.presenter.main_screen.card_screen.ICardFragmentPresenter;
import me.labs.corobox.corobox.presenter.make_order_screen.IMakeOrderActivityPresenter;

public class CategoriesOrderRealmAdapter extends RealmRecyclerViewAdapter<CategoryNumberModel, RecyclerView.ViewHolder> {

    private final IMakeOrderActivityPresenter presenter;
    private OrderedRealmCollection<CategoryNumberModel> categoryNumberModels;

    public CategoriesOrderRealmAdapter(@Nullable OrderedRealmCollection<CategoryNumberModel> data, IMakeOrderActivityPresenter presenter, boolean autoUpdate) {
        super(data, autoUpdate);
        this.categoryNumberModels = data;
        this.presenter = presenter;
        presenter.countAll(data);
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
                        presenter.countAll(categoryNumberModels);
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
                    if (counter == 1) {
                        categoryNumberModels.get(getAdapterPosition()).setNumber(counter - 1);
                    } else {
                        categoryNumberModels.get(getAdapterPosition()).setNumber(counter - 1);
                    }
                    realm.commitTransaction();
                    presenter.countAll(categoryNumberModels);
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
