package com.vientamthuong.eatsimple.admin.model;

public class Size {

    private String ma_size;
    private String ten_size;
    private int so_luong_con_lai;

    public Size(String ma_size, String ten_size, int so_luong_con_lai) {
        this.ma_size = ma_size;
        this.ten_size = ten_size;
        this.so_luong_con_lai = so_luong_con_lai;
    }

    public Size(Size size){
        this.ma_size = size.getMa_size();
        this.ten_size = size.getTen_size();
        this.so_luong_con_lai = size.getSo_luong_con_lai();
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

    public int getSo_luong_con_lai() {
        return so_luong_con_lai;
    }

    public void setSo_luong_con_lai(int so_luong_con_lai) {
        this.so_luong_con_lai = so_luong_con_lai;
    }
}
