package com.vientamthuong.eatsimple.checkout;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;

import org.jetbrains.annotations.NotNull;

public class PayAddressHolder extends RecyclerView.ViewHolder {

    private CardView cardView;
    private TextView tv_name,tv_diachi,tv_ghichu,tv_so_dien_thoai;
    private CheckBox checkBox;

    public PayAddressHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        getView(itemView);

    }
    void getView(View view){
        cardView = view.findViewById(R.id.cart_pay_address);
        tv_name = view.findViewById(R.id.name_kh);
        tv_diachi = view.findViewById(R.id.diachi_kh);
        tv_ghichu = view.findViewById(R.id.ghichu_kh);
        tv_so_dien_thoai = view.findViewById(R.id.sodienthoai);
        checkBox = view.findViewById(R.id.checkbox_cart);
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public TextView getTv_name() {
        return tv_name;
    }

    public void setTv_name(TextView tv_name) {
        this.tv_name = tv_name;
    }

    public TextView getTv_diachi() {
        return tv_diachi;
    }

    public void setTv_diachi(TextView tv_diachi) {
        this.tv_diachi = tv_diachi;
    }

    public TextView getTv_ghichu() {
        return tv_ghichu;
    }

    public void setTv_ghichu(TextView tv_ghichu) {
        this.tv_ghichu = tv_ghichu;
    }

    public TextView getTv_so_dien_thoai() {
        return tv_so_dien_thoai;
    }

    public void setTv_so_dien_thoai(TextView tv_so_dien_thoai) {
        this.tv_so_dien_thoai = tv_so_dien_thoai;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
