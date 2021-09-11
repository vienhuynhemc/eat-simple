package com.vientamthuong.eatsimple.detail;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;

import org.jetbrains.annotations.NotNull;



public class CommentHolder extends RecyclerView.ViewHolder {

    private LottieAnimationView img;
    private TextView ten,ten_sp,so_sao,noi_dung,ngay_thang;

    public CommentHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        img = itemView.findViewById(R.id.img_cart_one_item);
        ten = itemView.findViewById(R.id.cart_one_item_title);
        ten_sp = itemView.findViewById(R.id.sanpham);
        so_sao = itemView.findViewById(R.id.sosao);
        noi_dung = itemView.findViewById(R.id.cart_one_item_content);
        ngay_thang = itemView.findViewById(R.id.ngaythang);

    }

    public LottieAnimationView getImg() {
        return img;
    }

    public void setImg(LottieAnimationView img) {
        this.img = img;
    }

    public TextView getTen() {
        return ten;
    }

    public void setTen(TextView ten) {
        this.ten = ten;
    }

    public TextView getTen_sp() {
        return ten_sp;
    }

    public void setTen_sp(TextView ten_sp) {
        this.ten_sp = ten_sp;
    }

    public TextView getSo_sao() {
        return so_sao;
    }

    public void setSo_sao(TextView so_sao) {
        this.so_sao = so_sao;
    }

    public TextView getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(TextView noi_dung) {
        this.noi_dung = noi_dung;
    }

    public TextView getNgay_thang() {
        return ngay_thang;
    }

    public void setNgay_thang(TextView ngay_thang) {
        this.ngay_thang = ngay_thang;
    }
}
