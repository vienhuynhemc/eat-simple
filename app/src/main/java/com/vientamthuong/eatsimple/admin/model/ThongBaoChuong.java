package com.vientamthuong.eatsimple.admin.model;

import android.graphics.Bitmap;

import com.vientamthuong.eatsimple.date.DateTime;

public class ThongBaoChuong {

    private DateTime ngay_tao;
    private String noi_dung;
    private String ten_nguoi_gui;
    private Bitmap hinh_nguoi_gui;
    private String url;

    public ThongBaoChuong(DateTime ngay_tao, String noi_dung, String ten_nguoi_gui, String url) {
        this.ngay_tao = ngay_tao;
        this.noi_dung = noi_dung;
        this.ten_nguoi_gui = ten_nguoi_gui;
        this.url = url;
    }

    public DateTime getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(DateTime ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public String getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(String noi_dung) {
        this.noi_dung = noi_dung;
    }

    public String getTen_nguoi_gui() {
        return ten_nguoi_gui;
    }

    public void setTen_nguoi_gui(String ten_nguoi_gui) {
        this.ten_nguoi_gui = ten_nguoi_gui;
    }

    public Bitmap getHinh_nguoi_gui() {
        return hinh_nguoi_gui;
    }

    public void setHinh_nguoi_gui(Bitmap hinh_nguoi_gui) {
        this.hinh_nguoi_gui = hinh_nguoi_gui;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
