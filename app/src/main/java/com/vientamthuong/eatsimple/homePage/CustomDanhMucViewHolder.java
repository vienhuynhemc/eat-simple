package com.vientamthuong.eatsimple.homePage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;

public class CustomDanhMucViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView textView;
    private View layout;
    private CardView cardView;

    // Loading
    private CardView cardViewLoading;
    private CardView cardViewLoading2;


    public CustomDanhMucViewHolder(@NonNull View itemView) {
        super(itemView);
        this.layout = itemView;
        getView(itemView);
    }

    private void getView(View view) {
        imageView = view.findViewById(R.id.activity_home_page_custom_danh_muc_image_view);
        textView = view.findViewById(R.id.activity_home_page_custom_danh_muc_text_view);
        cardView = view.findViewById(R.id.cardView_category);
        // loading
        cardViewLoading = view.findViewById(R.id.card_view_loading);
        cardViewLoading2 = view.findViewById(R.id.card_view_loading_2);
    }

    public void hiddenCardViewLoading2() {
        cardViewLoading2.setVisibility(View.GONE);
    }

    public void hiddenCardViewLoading() {
        cardViewLoading.setVisibility(View.GONE);
    }

    // GETTER AND SETTER
    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public View getLayout() {
        return layout;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }
}
