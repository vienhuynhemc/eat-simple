package com.vientamthuong.eatsimple.admin.model;

public class DanhGiaSanPham {
    private String ma_danh_gia;
    private int so_sao;
    private String ma_size;

    public DanhGiaSanPham(String ma_danh_gia, int so_sao, String ma_size) {
        this.ma_danh_gia = ma_danh_gia;
        this.so_sao = so_sao;
        this.ma_size = ma_size;
    }

    public DanhGiaSanPham(DanhGiaSanPham danhGiaSanPham){
        this.ma_danh_gia = danhGiaSanPham.getMa_danh_gia();
        this.so_sao = danhGiaSanPham.getSo_sao();
        this.ma_size = danhGiaSanPham.getMa_size();
    }

    public String getMa_danh_gia() {
        return ma_danh_gia;
    }

    public void setMa_danh_gia(String ma_danh_gia) {
        this.ma_danh_gia = ma_danh_gia;
    }

    public int getSo_sao() {
        return so_sao;
    }

    public void setSo_sao(int so_sao) {
        this.so_sao = so_sao;
    }

    public String getMa_size() {
        return ma_size;
    }

    public void setMa_size(String ma_size) {
        this.ma_size = ma_size;
    }
}
