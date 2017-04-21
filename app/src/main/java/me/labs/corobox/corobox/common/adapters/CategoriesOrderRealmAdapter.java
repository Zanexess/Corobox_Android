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

import java.io.IOException;
import java.io.InputStream;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.CardModel;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.common.IntegerWrap;
import me.labs.corobox.corobox.presenter.main_screen.card_screen.ICardFragmentPresenter;
import me.labs.corobox.corobox.presenter.make_order_screen.IMakeOrderActivityPresenter;

public class CategoriesOrderRealmAdapter extends RealmRecyclerViewAdapter<Category, RecyclerView.ViewHolder> {

    private final IMakeOrderActivityPresenter presenter;
    private OrderedRealmCollection<Category> categories;
    private OrderedRealmCollection<IntegerWrap> count;

    public CategoriesOrderRealmAdapter(@Nullable OrderedRealmCollection<Category> data, OrderedRealmCollection<IntegerWrap> count, IMakeOrderActivityPresenter presenter, boolean autoUpdate) {
        super(data, autoUpdate);
        this.categories = data;
        this.count = count;
        this.presenter = presenter;
        presenter.countAll(data, count);
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
        return categories.size();
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
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    count.get(getAdapterPosition()).setCount(count.get(getAdapterPosition()).getCount() + 1);
                    realm.commitTransaction();
                    presenter.countAll(categories, count);
                    presenter.updateList();
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    if (count.get(getAdapterPosition()).getCount() == 1) {
                        count.get(getAdapterPosition()).setCount(count.get(getAdapterPosition()).getCount() - 1);
                    } else {
                        count.get(getAdapterPosition()).setCount(count.get(getAdapterPosition()).getCount() - 1);
                    }
                    realm.commitTransaction();
                    presenter.countAll(categories, count);
                    if (categories.size() == 0) {
                        presenter.finish();
                    }
                    presenter.updateList();
                }
            });

        }

        private void bind(int position) {
            Category category = categories.get(position);
            Integer counter = count.get(position).getCount();
            try {
                InputStream ims = image.getContext().getAssets().open(category.getPicture());
                Drawable d = Drawable.createFromStream(ims, null);
                image.setImageDrawable(d);
            }
            catch(IOException ex) {
                return;
            }

            title.setText(category.getTitle());
            price.setText(String.valueOf(category.getPrice() + "р месяц"));
            number.setText(String.valueOf(counter) + " шт.");

            if (count.get(position).getCount() == 0) {
                itemView.setVisibility(View.GONE);
                presenter.finish();
            } else {
                itemView.setVisibility(View.VISIBLE);
            }
        }


    }
}
