package me.labs.corobox.corobox.common.adapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.Box;
import me.labs.corobox.corobox.presenter.main_screen.boxes_fragment.IBoxesFragmentPresenter;

public class BoxesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Box> boxes;
    private HashSet<String> selected;
    private IBoxesFragmentPresenter presenter;

    public BoxesAdapter(List<Box> boxes, IBoxesFragmentPresenter presenter, HashSet<String> selected) {
        this.boxes = boxes;
        this.presenter = presenter;
        this.selected = selected;
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

        private ImageView addDelete;
        private ImageView imageView;
        private TextView numberView;
        private TextView titleView;

        public BoxHolder(View itemView) {
            super(itemView);

            addDelete = (ImageView) itemView.findViewById(R.id.add_or_delete);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            numberView = (TextView) itemView.findViewById(R.id.number);
            titleView = (TextView) itemView.findViewById(R.id.title);

            addDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!selected.contains(boxes.get(getAdapterPosition()).getUuid())) {
                        selected.add(boxes.get(getAdapterPosition()).getUuid());
                        addDelete.setImageResource(R.drawable.ic_close_circle_outline);
                    } else {
                        addDelete.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                        selected.remove(boxes.get(getAdapterPosition()).getUuid());
                    }
                    presenter.readyForOrder(selected);
                }
            });


//            numberView.setText(boxes.get(getAdapterPosition()).getCategory().getPrice());
        }

        public void bind() {
            Picasso.with(itemView.getContext())
                    .load(boxes.get(getAdapterPosition()).getImageUrl())
                    .into(imageView);

            if (!selected.contains(boxes.get(getAdapterPosition()).getUuid())) {
                addDelete.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
            } else {
                addDelete.setImageResource(R.drawable.ic_close_circle_outline);
            }

            numberView.setText(boxes.get(getAdapterPosition()).getCategory().getPrice()+"р");
            titleView.setText(boxes.get(getAdapterPosition()).getTitle());
        }
    }
}
