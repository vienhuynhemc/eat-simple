package com.vientamthuong.eatsimple.detailOrder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;

import org.jetbrains.annotations.NotNull;

public class DetailOrderHolder extends RecyclerView.ViewHolder {

    private TextView title,so_sao,gia,size;
    private LottieAnimationView img;
    private CardView cardView;
    private AppCompatButton button;
    public DetailOrderHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        getView(itemView);

    }
    void getView(View view){
        title = view.findViewById(R.id.cart_one_item_title);
        so_sao = view.findViewById(R.id.so_sao);
        gia = view.findViewById(R.id.gia_km);
        img = view.findViewById(R.id.img_cart_one_item);
        cardView = view.findViewById(R.id.cardview_one_item);
        size = view.findViewById(R.id.size);
        button = view.findViewById(R.id.apdung);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getSo_sao() {
        return so_sao;
    }

    public void setSo_sao(TextView so_sao) {
        this.so_sao = so_sao;
    }

    public TextView getGia() {
        return gia;
    }

    public void setGia(TextView gia) {
        this.gia = gia;
    }

    public TextView getSize() {
        return size;
    }

    public void setSize(TextView size) {
        this.size = size;
    }

    public LottieAnimationView getImg() {
        return img;
    }

    public void setImg(LottieAnimationView img) {
        this.img = img;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public AppCompatButton getButton() {
        return button;
    }

    public void setButton(AppCompatButton button) {
        this.button = button;
    }
}
