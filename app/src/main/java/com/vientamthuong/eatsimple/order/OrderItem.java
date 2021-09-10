package com.vientamthuong.eatsimple.order;

import com.vientamthuong.eatsimple.date.DateTime;

public class OrderItem {
    private String ma_dh;
    private String ten_sp;
    private String ma_size;
    private int so_luong;
    private int tien;
    private int gia_tri;
    private String url;

    public OrderItem(String ma_dh, String ten_sp, String ma_size, int so_luong, int tien, int gia_tri, String url) {
        this.ma_dh = ma_dh;
        this.ten_sp = ten_sp;
        this.ma_size = ma_size;
        this.so_luong = so_luong;
        this.tien = tien;
        this.gia_tri = gia_tri;
        this.url = url;
    }

    public OrderItem() {
    }

    public String getMa_dh() {
        return ma_dh;
    }

    public void setMa_dh(String ma_dh) {
        this.ma_dh = ma_dh;
    }

    public String getTen_sp() {
        return ten_sp;
    }

    public void setTen_sp(String ten_sp) {
        this.ten_sp = ten_sp;
    }

    public String getMa_size() {
        return ma_size;
    }

    public void setMa_size(String ma_size) {
        this.ma_size = ma_size;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }

    public int getGia_tri() {
        return gia_tri;
    }

    public void setGia_tri(int gia_tri) {
        this.gia_tri = gia_tri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "ma_dh='" + ma_dh + '\'' +
                ", ten_sp='" + ten_sp + '\'' +
                ", ma_size='" + ma_size + '\'' +
                ", so_luong=" + so_luong +
                ", tien=" + tien +
                ", gia_tri=" + gia_tri +
                ", url='" + url + '\'' +
                '}';
    }
}
