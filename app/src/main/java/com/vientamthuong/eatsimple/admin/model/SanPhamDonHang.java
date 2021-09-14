package com.vientamthuong.eatsimple.admin.model;

import android.graphics.Bitmap;

public class SanPhamDonHang {

    private String ma_sp;
    private String ten_sp;
    private Bitmap hinh;
    private String ma_size;
    private String ten_size;
    private int so_luong;
    private int tien;
    private String url;

    public SanPhamDonHang(String ma_sp, String ten_sp, Bitmap hinh, String ma_size, String ten_size, int so_luong, int tien,String url) {
        this.ma_sp = ma_sp;
        this.ten_sp = ten_sp;
        this.hinh = hinh;
        this.ma_size = ma_size;
        this.ten_size = ten_size;
        this.so_luong = so_luong;
        this.tien = tien;
        this.url =url;
    }

    public SanPhamDonHang(){

    }

    public String getMa_sp() {
        return ma_sp;
    }

    public void setMa_sp(String ma_sp) {
        this.ma_sp = ma_sp;
    }

    public String getTen_sp() {
        return ten_sp;
    }

    public void setTen_sp(String ten_sp) {
        this.ten_sp = ten_sp;
    }

    public Bitmap getHinh() {
        return hinh;
    }

    public void setHinh(Bitmap hinh) {
        this.hinh = hinh;
    }

    public String getMa_size() {
        return ma_size;
    }

    public void setMa_size(String ma_size) {
        this.ma_size = ma_size;
    }

    public String getTen_size() {
        return ten_size;
    }

    public void setTen_size(String ten_size) {
        this.ten_size = ten_size;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
