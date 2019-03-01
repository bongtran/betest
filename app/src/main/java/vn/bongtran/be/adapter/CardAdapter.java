package vn.bongtran.be.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import vn.bongtran.be.R;
import vn.bongtran.be.databinding.ItemCardViewBinding;
import vn.bongtran.be.model.CardModel;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private Context context;
    private ArrayList<CardModel> cards;
    private LayoutInflater layoutInflater;
    public CardAdapter(Context context, ArrayList<CardModel> cards) {
        this.cards = cards;
        this.context = context;
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

    class CardViewHolder extends RecyclerView.ViewHolder {
        private final ItemCardViewBinding binding;

        public CardViewHolder(final ItemCardViewBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
