package com.vientamthuong.eatsimple.checkout;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;

import org.jetbrains.annotations.NotNull;

public class PayProductHolder extends RecyclerView.ViewHolder {

    private LottieAnimationView img;
    private CardView layout;
    private TextView textView;

    public PayProductHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        getView(itemView);
    }

    void getView(View view){
        img = view.findViewById(R.id.img_product);
        layout = view.findViewById(R.id.cardView_category);
        textView = view.findViewById(R.id.tensp);
    }

    public LottieAnimationView getImg() {
        return img;
    }

    public void setImg(LottieAnimationView img) {
        this.img = img;
    }


    public CardView getLayout() {
        return layout;
    }

    public void setLayout(CardView layout) {
        this.layout = layout;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
