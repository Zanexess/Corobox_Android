package me.labs.corobox.corobox.common.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.model.realm.CategoryNumberModel;

public class BoxImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Box> boxes;

    public BoxImagesAdapter(@Nullable List<Box> data) {
        this.boxes = data;
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
        return boxes.size();
    }

    private class CategoryPictureHolder extends RecyclerView.ViewHolder {
        ImageView imageView;


        public CategoryPictureHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }

        private void bindModel(int position) {
            Picasso.with(itemView.getContext())
                    .load(boxes.get(position).getImageUrl())
                    .error(R.drawable.error_placeholder)
                    .into(imageView);
        }
    }
}
