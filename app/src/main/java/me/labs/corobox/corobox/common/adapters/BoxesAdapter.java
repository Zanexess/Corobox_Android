package me.labs.corobox.corobox.common.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashSet;

import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.Box;
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

    }

    @Override
    public int getItemCount() {
        return boxes.size();
    }

    private class BoxHolder extends RecyclerView.ViewHolder {

        ImageView addDelete;

        public BoxHolder(View itemView) {
            super(itemView);
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
    }
}
