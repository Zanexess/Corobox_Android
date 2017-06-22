package me.labs.corobox.corobox.common.adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.CategoryNumberModel;
import me.labs.corobox.corobox.model.realm.OrderModelTo;

public class CategoriesImagesAdapter extends RealmRecyclerViewAdapter<CategoryNumberModel, RecyclerView.ViewHolder> {

    private OrderedRealmCollection<CategoryNumberModel> categories;

    public CategoriesImagesAdapter(@Nullable OrderedRealmCollection<CategoryNumberModel> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.categories = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_picture, parent, false);
        return new CategoryPictureHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoryPictureHolder categoryPictureHolder = ((CategoryPictureHolder)holder);
        categoryPictureHolder.bindModel(position);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private class CategoryPictureHolder extends RecyclerView.ViewHolder {
        ImageView imageView;


        public CategoryPictureHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

        private void bindModel(int position) {
            Picasso.with(itemView.getContext())
                    .load(categories.get(position).getCategory().getPicture())
                    .into(imageView);
        }
    }
}
