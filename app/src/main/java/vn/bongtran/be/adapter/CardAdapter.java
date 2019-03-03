package vn.bongtran.be.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import vn.bongtran.be.R;
import vn.bongtran.be.databinding.ItemCardViewBinding;
import vn.bongtran.be.interfaces.RecyclerViewClickListener;
import vn.bongtran.be.model.CardLiteModel;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private Context context;
    private ArrayList<CardLiteModel> cards;
    private LayoutInflater layoutInflater;
    private RecyclerViewClickListener itemListener;
    public CardAdapter(Context context, ArrayList<CardLiteModel> cards, RecyclerViewClickListener itemListener) {
        this.cards = cards;
        this.context = context;
        this.itemListener = itemListener;
    }

    public void setCards(ArrayList<CardLiteModel> cards){
        this.cards = cards;
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
        cardViewHolder.binding.setCard(cards.get(i));
    }

    @Override
    public int getItemCount() {
        return (cards == null) ? 0 : cards.size();
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
            if(itemListener != null)
                itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }
}
