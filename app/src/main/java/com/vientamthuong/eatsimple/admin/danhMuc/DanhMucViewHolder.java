package com.vientamthuong.eatsimple.admin.danhMuc;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;

import org.jetbrains.annotations.NotNull;

public class DanhMucViewHolder extends RecyclerView.ViewHolder {

    private ImageView hinhDaiDien;
    private TextView tenDanhMuc;
    private TextView maDanhMuc;
    private TextView ngayTao;
    private TextView thongTin;
    private CardView sua;
    private CardView xoa;
    private CheckBox chonXoa;
    private CardView layout;

    public DanhMucViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        getView(itemView);
    }

    private void getView(View view) {
        hinhDaiDien = view.findViewById(R.id.hinhDaiDien);
        tenDanhMuc = view.findViewById(R.id.tenDanhMuc);
        maDanhMuc = view.findViewById(R.id.maDanhMuc);
        ngayTao = view.findViewById(R.id.ngayTao);
        thongTin = view.findViewById(R.id.thongTin);
        sua = view.findViewById(R.id.buttonSua);
        xoa = view.findViewById(R.id.buttonXoa);
        chonXoa = view.findViewById(R.id.chonXoa);
        layout = view.findViewById(R.id.layout);
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
