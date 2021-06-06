package com.vientamthuong.eatsimple.admin.danhMuc;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;

import org.jetbrains.annotations.NotNull;

public class DanhMucViewHolder extends RecyclerView.ViewHolder {

    private ImageView hinhDaiDien;
    private LottieAnimationView hinhDaiDienLottie;
    private TextView tenDanhMuc;
    private CardView tenLottie;
    private TextView maDanhMuc;
    private CardView maLottie;
    private TextView ngayTao;
    private CardView ngayLottie;
    private TextView thongTin;
    private CardView sua;
    private CardView suaLottie;
    private CardView xoa;
    private CardView xoaLottie;
    private CheckBox chonXoa;
    private CardView chonXoaLottie;
    private CardView layout;
    private CardView slLottie;
    private ConstraintLayout sl;

    public DanhMucViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        getView(itemView);
    }

    public void updateHinhDaiDien() {
        hinhDaiDienLottie.setVisibility(View.GONE);
        hinhDaiDien.setVisibility(View.VISIBLE);
    }

    public void updateTenDanhMuc(){
        tenLottie.setVisibility(View.GONE);
        tenDanhMuc.setVisibility(View.VISIBLE);
    }

    public void updateSl(){
        slLottie.setVisibility(View.GONE);
        sl.setVisibility(View.VISIBLE);
    }

    public void updateMaDanhMuc(){
        maLottie.setVisibility(View.GONE);
        maDanhMuc.setVisibility(View.VISIBLE);
        chonXoaLottie.setVisibility(View.GONE);
        chonXoa.setVisibility(View.VISIBLE);
        suaLottie.setVisibility(View.GONE);
        sua.setVisibility(View.VISIBLE);
        xoaLottie.setVisibility(View.GONE);
        xoa.setVisibility(View.VISIBLE);
    }

    public void updateNgayTao(){
        ngayLottie.setVisibility(View.GONE);
        ngayTao.setVisibility(View.VISIBLE);
    }

    private void getView(View view) {
        hinhDaiDien = view.findViewById(R.id.hinhDaiDien);
        hinhDaiDienLottie = view.findViewById(R.id.hinhDaiDienLottie);
        tenDanhMuc = view.findViewById(R.id.tenDanhMuc);
        tenLottie = view.findViewById(R.id.tenLottie);
        maDanhMuc = view.findViewById(R.id.maDanhMuc);
        maLottie = view.findViewById(R.id.maLottie);
        ngayTao = view.findViewById(R.id.ngayTao);
        ngayLottie = view.findViewById(R.id.ngayLottie);
        thongTin = view.findViewById(R.id.thongTin);
        sua = view.findViewById(R.id.buttonSua);
        suaLottie = view.findViewById(R.id.suaLottie);
        xoa = view.findViewById(R.id.buttonXoa);
        xoaLottie = view.findViewById(R.id.xoaLottie);
        chonXoa = view.findViewById(R.id.chonXoa);
        chonXoaLottie = view.findViewById(R.id.chonXoaLottie);
        layout = view.findViewById(R.id.layout);
        sl = view.findViewById(R.id.noiDung);
        slLottie = view.findViewById(R.id.slLottie);
    }

    public ImageView getHinhDaiDien() {
        return hinhDaiDien;
    }

    public void setHinhDaiDien(ImageView hinhDaiDien) {
        this.hinhDaiDien = hinhDaiDien;
    }

    public TextView getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(TextView tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public TextView getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(TextView maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public TextView getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(TextView ngayTao) {
        this.ngayTao = ngayTao;
    }

    public TextView getThongTin() {
        return thongTin;
    }

    public void setThongTin(TextView thongTin) {
        this.thongTin = thongTin;
    }

    public CardView getSua() {
        return sua;
    }

    public void setSua(CardView sua) {
        this.sua = sua;
    }

    public CardView getXoa() {
        return xoa;
    }

    public void setXoa(CardView xoa) {
        this.xoa = xoa;
    }

    public CheckBox getChonXoa() {
        return chonXoa;
    }

    public void setChonXoa(CheckBox chonXoa) {
        this.chonXoa = chonXoa;
    }

    public CardView getLayout() {
        return layout;
    }

    public void setLayout(CardView layout) {
        this.layout = layout;
    }
}
