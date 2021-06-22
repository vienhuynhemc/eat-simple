package com.vientamthuong.eatsimple.loadProductByID;

import android.graphics.Paint;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;

import org.jetbrains.annotations.NotNull;

public class LoadProductViewHolder extends RecyclerView.ViewHolder {

    private TextView title,so_sao,gia,gia_km;
    private LottieAnimationView img;
    private CardView cardView;
    private ConstraintLayout layout;


    public LoadProductViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        getView(itemView);

    }

    void getView(View view){
        title = view.findViewById(R.id.cart_one_item_title);
        so_sao = view.findViewById(R.id.so_sao);
        gia = view.findViewById(R.id.gia);
        gia_km = view.findViewById(R.id.gia_km);
        img = view.findViewById(R.id.img_cart_one_item);
        gia.setPaintFlags(gia.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        cardView = view.findViewById(R.id.cardview_one_item);
        layout = view.findViewById(R.id.swpe_cart_layout);
    }

    public ConstraintLayout getLayout() {
        return layout;
    }

    public void setLayout(ConstraintLayout layout) {
        this.layout = layout;
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

    public TextView getGia_km() {
        return gia_km;
    }

    public void setGia_km(TextView gia_km) {
        this.gia_km = gia_km;
    }

    public LottieAnimationView getImg() {
        return img;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public void setImg(LottieAnimationView img) {
        this.img = img;
    }
}
