package com.vientamthuong.eatsimple.mennuSearch;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;

import org.jetbrains.annotations.NotNull;

public class HashtagHolder extends RecyclerView.ViewHolder {

    private CardView cardView;
    private TextView name;


    public HashtagHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        getView(itemView);

    }

    void getView(View view){

        cardView = view.findViewById(R.id.id_tag);
        name = view.findViewById(R.id.name_tag);

    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }
}
