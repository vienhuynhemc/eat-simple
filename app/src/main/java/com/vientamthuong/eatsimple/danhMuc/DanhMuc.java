package com.vientamthuong.eatsimple.danhMuc;

import android.graphics.Bitmap;

public class DanhMuc {

    private String ma_danh_muc;
    private String ten_danh_muc;
    private String hinh;
    private Bitmap bitmap;

    public DanhMuc(String ma_danh_muc, String ten_danh_muc, String hinh) {
        this.ma_danh_muc = ma_danh_muc;
        this.ten_danh_muc = ten_danh_muc;
        this.hinh = hinh;
        bitmap = null;
    }

    public DanhMuc(String ma_danh_muc) {
        this.ma_danh_muc = ma_danh_muc;
    }

    // Hàm để nhận biết danh mục này load chưa
    public boolean isLoaded() {
        return ma_danh_muc != null && ten_danh_muc != null && hinh != null;
    }

    // GETTER AND SETTER
    public String getMa_danh_muc() {
        return ma_danh_muc;
    }

    public void setMa_danh_muc(String ma_danh_muc) {
        this.ma_danh_muc = ma_danh_muc;
    }

    public String getTen_danh_muc() {
        return ten_danh_muc;
    }

    public void setTen_danh_muc(String ten_danh_muc) {
        this.ten_danh_muc = ten_danh_muc;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
