package vn.bongtran.be.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import vn.bongtran.be.R;
import vn.bongtran.be.databinding.ItemCardViewBinding;
import vn.bongtran.be.interfaces.RecyclerViewClickListener;
import vn.bongtran.be.model.CardLiteModel;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> implements Filterable {
    private Context context;
    private ArrayList<CardLiteModel> cards;
    private ArrayList<CardLiteModel> cardListFiltered;
    private LayoutInflater layoutInflater;
    private RecyclerViewClickListener itemListener;

    public CardAdapter(Context context, ArrayList<CardLiteModel> cards, RecyclerViewClickListener itemListener) {
        this.cards = cards;
        this.context = context;
        this.cardListFiltered = cards;
        this.itemListener = itemListener;
    }

    public void setCards(ArrayList<CardLiteModel> cards) {
        this.cards = cards;
        this.cardListFiltered = cards;
        notifyDataSetChanged();
    }

    public void addCard(CardLiteModel card) {
        this.cards.add(card);
        this.cardListFiltered = cards;
        notifyDataSetChanged();
    }

    public void addCard(int index, CardLiteModel card) {
        this.cards.add(index, card);
        this.cardListFiltered =  cards;
        notifyDataSetChanged();
    }

    public void addCards(ArrayList<CardLiteModel> cards) {
        this.cards.addAll(cards);
        this.cardListFiltered = this.cards;
        notifyDataSetChanged();
    }

    public void notifyChanged() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        ItemCardViewBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_card_view, viewGroup, false);
        return new CardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {
        cardViewHolder.binding.setCard(cardListFiltered.get(i));
    }

    @Override
    public int getItemCount() {
        return (cardListFiltered == null) ? 0 : cardListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    cardListFiltered = cards;
                } else {
                    ArrayList<CardLiteModel> filteredList = new ArrayList<>();
                    for (CardLiteModel row : cards) {
                        if (row.getName() != null && row.getMobile() != null)
                            if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getMobile().contains(charSequence)) {
                                filteredList.add(row);
                            }
                    }
                    cardListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = cardListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                cardListFiltered = (ArrayList<CardLiteModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemCardViewBinding binding;

        CardViewHolder(final ItemCardViewBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
            itemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemListener != null)
                itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }
}
