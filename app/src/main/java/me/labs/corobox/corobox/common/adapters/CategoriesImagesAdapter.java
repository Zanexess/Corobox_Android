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
import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.Category;
import me.labs.corobox.corobox.model.realm.CategoryNumberModel;
import me.labs.corobox.corobox.model.realm.OrderModelTo;

public class CategoriesImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CategoryNumberModel> categories;
    private List<String> pictures;

    public CategoriesImagesAdapter(@Nullable List<CategoryNumberModel> data) {
        this.categories = data;
        pictures = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            for (int j = 0; j < categories.get(i).getNumber(); j++) {
                pictures.add(categories.get(i).getCategory().getPicture());
            }
        }
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
        return pictures.size();
    }

    private class CategoryPictureHolder extends RecyclerView.ViewHolder {
        ImageView imageView;


        public CategoryPictureHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

        private void bindModel(int position) {
            Picasso.with(itemView.getContext())
                    .load(pictures.get(position))
                    .error(R.drawable.error_placeholder)
                    .into(imageView);
        }
    }
}
