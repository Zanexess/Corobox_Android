
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
import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.CategoryNumberModel;
import me.labs.corobox.corobox.presenter.make_order_screen.IMakeOrderActivityPresenter;

public class CategoriesOrderFromRealmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final IMakeOrderActivityPresenter presenter;
    private List<Box> categoryNumberModels;

    public CategoriesOrderFromRealmAdapter(List<Box> data, IMakeOrderActivityPresenter presenter) {
        this.categoryNumberModels = data;
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_box_item, parent, false);
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

    public void setTill(long date_arr, long date_t) {
    }

    private class CategoryHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView image;
        TextView title;
        TextView price;

        private CategoryHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            image = (ImageView) itemView.findViewById(R.id.imageView);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
        }

        private void bind(int position) {
            Category category = categoryNumberModels.get(position).getCategory();

            Picasso.with(itemView.getContext())
                    .load(categoryNumberModels.get(position).getImageUrl())
                    .error(R.drawable.error_placeholder)
                    .into(image);

            title.setText(category.getTitle());
        }
    }
}
