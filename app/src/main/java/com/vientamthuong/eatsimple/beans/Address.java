package com.vientamthuong.eatsimple.beans;

public class Address {

    private String ma_add;
    private String ma_kh;
    private String name;
    private String diachi;
    private String ghichu;
    private String sodienthoai;

    public Address() {
    }

    public Address(String name, String diachi, String ghichu, String sodienthoai) {
        this.name = name;
        this.diachi = diachi;
        this.ghichu = ghichu;
        this.sodienthoai = sodienthoai;
    }

    public String getMa_add() {
        return ma_add;
    }

    public void setMa_add(String ma_add) {
        this.ma_add = ma_add;
    }

    public String getMa_kh() {
        return ma_kh;
    }

    public void setMa_kh(String ma_kh) {
        this.ma_kh = ma_kh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }
}
