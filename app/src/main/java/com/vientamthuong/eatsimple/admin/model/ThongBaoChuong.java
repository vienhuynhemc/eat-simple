package com.vientamthuong.eatsimple.admin.model;

import android.graphics.Bitmap;

import com.vientamthuong.eatsimple.date.DateTime;

public class ThongBaoChuong {

    private DateTime ngay_tao;
    private String noi_dung;
    private String ten_nguoi_gui;
    private Bitmap hinh_nguoi_gui;
    private String url;
    private int kieu_nguoi_gui;
    private String ma_thong_bao_chuong;
    private String ma_nguoi_gui;

    public ThongBaoChuong(DateTime ngay_tao, String noi_dung, String ten_nguoi_gui, String url) {
        this.ngay_tao = ngay_tao;
        this.noi_dung = noi_dung;
        this.ten_nguoi_gui = ten_nguoi_gui;
        this.url = url;
    }

    public int getKieu_nguoi_gui() {
        return kieu_nguoi_gui;
    }

    public void setKieu_nguoi_gui(int kieu_nguoi_gui) {
        this.kieu_nguoi_gui = kieu_nguoi_gui;
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

    public String getMa_thong_bao_chuong() {
        return ma_thong_bao_chuong;
    }

    public void setMa_thong_bao_chuong(String ma_thong_bao_chuong) {
        this.ma_thong_bao_chuong = ma_thong_bao_chuong;
    }

    public String getMa_nguoi_gui() {
        return ma_nguoi_gui;
    }

    public void setMa_nguoi_gui(String ma_nguoi_gui) {
        this.ma_nguoi_gui = ma_nguoi_gui;
    }
}
