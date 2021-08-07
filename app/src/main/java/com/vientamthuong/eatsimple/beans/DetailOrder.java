package com.vientamthuong.eatsimple.beans;

import com.vientamthuong.eatsimple.date.DateTime;

public class DetailOrder {
    private String ma_dh;
    private String ma_kh;
    private String ma_add;
    private DateTime ngay_tao;
    private String thoi_gian_giao_hang;
    private String trang_thai_thanh_toan;
    private String tong_tien;
    private MaGiamGia ma_giam_gia;
    private Address address;

    public DetailOrder() {
    }

    public String getMa_dh() {
        return ma_dh;
    }

    public void setMa_dh(String ma_dh) {
        this.ma_dh = ma_dh;
    }

    public String getMa_kh() {
        return ma_kh;
    }

    public void setMa_kh(String ma_kh) {
        this.ma_kh = ma_kh;
    }

    public String getMa_add() {
        return ma_add;
    }

    public void setMa_add(String ma_add) {
        this.ma_add = ma_add;
    }

    public DateTime getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(DateTime ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public String getThoi_gian_giao_hang() {
        return thoi_gian_giao_hang;
    }

    public void setThoi_gian_giao_hang(String thoi_gian_giao_hang) {
        this.thoi_gian_giao_hang = thoi_gian_giao_hang;
    }

    public String getTrang_thai_thanh_toan() {
        return trang_thai_thanh_toan;
    }

    public void setTrang_thai_thanh_toan(String trang_thai_thanh_toan) {
        this.trang_thai_thanh_toan = trang_thai_thanh_toan;
    }

    public String getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(String tong_tien) {
        this.tong_tien = tong_tien;
    }

    public MaGiamGia getMa_giam_gia() {
        return ma_giam_gia;
    }

    public void setMa_giam_gia(MaGiamGia ma_giam_gia) {
        this.ma_giam_gia = ma_giam_gia;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
