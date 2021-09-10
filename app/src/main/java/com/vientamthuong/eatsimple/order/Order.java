package com.vientamthuong.eatsimple.order;

import com.vientamthuong.eatsimple.date.DateTime;

import java.io.Serializable;

public class Order {
    private String ma_dh;
    private String dia_chi;
    private DateTime ngay_tao;
    private int trang_thai_don_hang;
    private int tong_tien;
    private String ten_sp;
    private String ma_size;
    private int so_luong;
    private int tien;
    private int gia_tri;
    private String url;


    public Order(String ma_dh, String dia_chi, DateTime ngay_tao, int trang_thai_don_hang, int tong_tien, String ten_sp, String ma_size, int so_luong, int tien, int gia_tri, String url) {
        this.ma_dh = ma_dh;
        this.dia_chi = dia_chi;
        this.ngay_tao = ngay_tao;
        this.trang_thai_don_hang = trang_thai_don_hang;
        this.tong_tien = tong_tien;
        this.ten_sp = ten_sp;
        this.ma_size = ma_size;
        this.so_luong = so_luong;
        this.tien = tien;
        this.gia_tri = gia_tri;
        this.url = url;
    }

    public Order() {
    }

    public String getMa_dh() {
        return ma_dh;
    }

    public void setMa_dh(String ma_dh) {
        this.ma_dh = ma_dh;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public DateTime getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(DateTime ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public int getTrang_thai_don_hang() {
        return trang_thai_don_hang;
    }

    public void setTrang_thai_don_hang(int trang_thai_don_hang) {
        this.trang_thai_don_hang = trang_thai_don_hang;
    }

    public int getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(int tong_tien) {
        this.tong_tien = tong_tien;
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
        return "Order{" +
                "ma_dh='" + ma_dh + '\'' +
                ", dia_chi='" + dia_chi + '\'' +
                ", ngay_tao=" + ngay_tao +
                ", trang_thai_don_hang=" + trang_thai_don_hang +
                ", tong_tien=" + tong_tien +
                ", ten_sp='" + ten_sp + '\'' +
                ", ma_size='" + ma_size + '\'' +
                ", so_luong=" + so_luong +
                ", tien=" + tien +
                ", gia_tri=" + gia_tri +
                ", url='" + url + '\'' +
                '}';
    }
}
