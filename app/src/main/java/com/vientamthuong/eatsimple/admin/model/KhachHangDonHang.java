package com.vientamthuong.eatsimple.admin.model;

import android.graphics.Bitmap;

public class KhachHangDonHang {

    private String ten_hien_thi;
    private String ma_kh;
    private String url;
    private Bitmap hinh;

    public KhachHangDonHang(String ten_hien_thi, String ma_kh, Bitmap hinh,String url) {
        this.ten_hien_thi = ten_hien_thi;
        this.ma_kh = ma_kh;
        this.hinh = hinh;
        this.url = url;
    }

    public String getTen_hien_thi() {
        return ten_hien_thi;
    }

    public void setTen_hien_thi(String ten_hien_thi) {
        this.ten_hien_thi = ten_hien_thi;
    }

    public String getMa_kh() {
        return ma_kh;
    }

    public void setMa_kh(String ma_kh) {
        this.ma_kh = ma_kh;
    }

    public Bitmap getHinh() {
        return hinh;
    }

    public void setHinh(Bitmap hinh) {
        this.hinh = hinh;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
