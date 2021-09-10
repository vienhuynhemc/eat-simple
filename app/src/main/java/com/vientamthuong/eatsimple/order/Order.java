package com.vientamthuong.eatsimple.order;

import com.vientamthuong.eatsimple.date.DateTime;

import java.io.Serializable;
import java.util.ArrayList;

public class Order {
    private String dia_chi;
    private DateTime ngay_tao;
    private int tong_tien;
    private ArrayList<OrderItem> item;
    private int trang_thai_don_hang;

    public Order() {

    }

    public Order(String dia_chi, DateTime ngay_tao, int tong_tien, ArrayList<OrderItem> item, int trang_thai_don_hang) {
        this.dia_chi = dia_chi;
        this.ngay_tao = ngay_tao;
        this.tong_tien = tong_tien;
        this.item = item;
        this.trang_thai_don_hang = trang_thai_don_hang;
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

    public int getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(int tong_tien) {
        this.tong_tien = tong_tien;
    }

    public ArrayList<OrderItem> getItem() {
        return item;
    }

    public void setItem(ArrayList<OrderItem> item) {
        this.item = item;
    }

    public int getTrang_thai_don_hang() {
        return trang_thai_don_hang;
    }

    public void setTrang_thai_don_hang(int trang_thai_don_hang) {
        this.trang_thai_don_hang = trang_thai_don_hang;
    }

    @Override
    public String toString() {
        return "Order{" +
                "dia_chi='" + dia_chi + '\'' +
                ", ngay_tao=" + ngay_tao +
                ", tong_tien=" + tong_tien +
                ", item=" + item +
                ", trang_thai_don_hang='" + trang_thai_don_hang + '\'' +
                '}';
    }
}
