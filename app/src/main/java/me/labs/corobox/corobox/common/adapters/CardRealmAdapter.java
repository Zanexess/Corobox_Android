package me.labs.corobox.corobox.common.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import me.labs.corobox.corobox.R;
import me.labs.corobox.corobox.model.realm.CardModel;
import me.labs.corobox.corobox.presenter.main_screen.card_fragment.ICardFragmentPresenter;

public class CardRealmAdapter extends RealmRecyclerViewAdapter<CardModel, RecyclerView.ViewHolder> {

    private OrderedRealmCollection<CardModel> cards;
    private ICardFragmentPresenter presenter;

    public CardRealmAdapter(@Nullable OrderedRealmCollection<CardModel> data, boolean autoUpdate, ICardFragmentPresenter presenter) {
        super(data, autoUpdate);
        this.cards = data;
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CardHolder)holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    private class CardHolder extends RecyclerView.ViewHolder {

        private TextView number;
        private TextView date;
        private ImageView defaultCard;

        private CardHolder(View itemView) {
            super(itemView);
            number = (TextView) itemView.findViewById(R.id.card_number);
            date = (TextView) itemView.findViewById(R.id.date);
            defaultCard = (ImageView) itemView.findViewById(R.id.defaultCard);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    presenter.deleteCard(cards.get(getAdapterPosition()).getUuid());
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.setDefaultCard(cards.get(getAdapterPosition()).getUuid());
                }
            });
        }

        private void bind(int position) {
            String cardNumber = cards.get(position).getCardNumber();
            number.setText(cardNumber.substring(0, 4) + " " + cardNumber.substring(4, 8) + " " + cardNumber.substring(8, 12) + " " + cardNumber.substring(12));
            date.setText(cards.get(position).getExpirationMonth() + " / " + cards.get(position).getExpirationYear());
            if (cards.get(position).isUseAsDefault()) {
                defaultCard.setVisibility(View.VISIBLE);
            } else {
                defaultCard.setVisibility(View.GONE);
            }
        }


    }
}
