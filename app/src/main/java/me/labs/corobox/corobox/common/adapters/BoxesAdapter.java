package me.labs.corobox.corobox.common.adapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    class BoxHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.add_or_delete) ImageView addDelete;
        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.title) TextView titleView;
        @BindView(R.id.time_lost) TextView time;
        @BindView(R.id.share) ImageView share;
        @BindView(R.id.pay) ImageView pay;

        public BoxHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            addDelete = (ImageView) itemView.findViewById(R.id.add_or_delete);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            titleView = (TextView) itemView.findViewById(R.id.title);

            addDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!selected.contains(boxes.get(getAdapterPosition()).getUuid())) {
                        selected.add(boxes.get(getAdapterPosition()).getUuid());
                        addDelete.setImageResource(R.drawable.ic_minus);
                    } else {
                        addDelete.setImageResource(R.drawable.ic_plus);
                        selected.remove(boxes.get(getAdapterPosition()).getUuid());
                    }
                    presenter.readyForOrder(selected);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!selected.contains(boxes.get(getAdapterPosition()).getUuid())) {
                        selected.add(boxes.get(getAdapterPosition()).getUuid());
                        addDelete.setImageResource(R.drawable.ic_minus);
                    } else {
                        addDelete.setImageResource(R.drawable.ic_plus);
                        selected.remove(boxes.get(getAdapterPosition()).getUuid());
                    }
                    presenter.readyForOrder(selected);
                    return true;
                }
            });

            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Пополнение срока хранения", Toast.LENGTH_SHORT).show();
                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Поделиться", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bind() {
            Picasso.with(itemView.getContext())
                    .load(boxes.get(getAdapterPosition()).getImageUrl())
                    .into(imageView);

            if (!selected.contains(boxes.get(getAdapterPosition()).getUuid())) {
                addDelete.setImageResource(R.drawable.ic_plus);
            } else {
                addDelete.setImageResource(R.drawable.ic_minus);
            }

            titleView.setText(boxes.get(getAdapterPosition()).getTitle());

            long date_from = boxes.get(getAdapterPosition()).getDateCreated();
            long date_till = boxes.get(getAdapterPosition()).getPaid_till();


            time.setText(((date_till - date_from) / 24 / 60 / 60) + " дня");
        }
    }
}
