package me.labs.corobox.corobox.common.adapters;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.presenter.main_screen.boxes_fragment.IBoxesFragmentPresenter;

public class BoxesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Box> boxes;
    private HashSet<Integer> selected;
    private IBoxesFragmentPresenter presenter;

    public BoxesAdapter(ArrayList<Box> boxes, IBoxesFragmentPresenter presenter) {
        this.boxes = boxes;
        this.presenter = presenter;
        this.selected = new HashSet<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_box, parent, false);
        return new BoxHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BoxHolder boxHolder = (BoxHolder) holder;
        boxHolder.bind();
    }

    @Override
    public int getItemCount() {
        return boxes.size();
    }

    private class BoxHolder extends RecyclerView.ViewHolder {

        ImageView addDelete;
        ImageView imageView;
        TextView textView;

        public BoxHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.number);
            addDelete = (ImageView) itemView.findViewById(R.id.add_or_delete);
            addDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!selected.contains(getAdapterPosition())) {
                        selected.add(getAdapterPosition());
                        addDelete.setImageResource(R.drawable.ic_close_circle_outline);
                    } else {
                        addDelete.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                        selected.remove(getAdapterPosition());
                    }
                    presenter.readyForOrder(selected);
                }
            });
        }


        public void bind() {
            textView.setText(boxes.get(getAdapterPosition()).getPrice().toString() + " рублей\\месяц");
            try {
                InputStream ims = imageView.getContext().getAssets().open(boxes.get(getAdapterPosition()).getUrl());
                Drawable d = Drawable.createFromStream(ims, null);
                imageView.setImageDrawable(d);
            }
            catch(IOException ex) {
                return;
            }
        }
    }
}
