package com.vientamthuong.eatsimple.beans;

import java.io.Serializable;

public class Size implements Serializable {

    private String ma_size;
    private String ten_size;

    public Size(String ma_size, String ten_size) {
        this.ma_size = ma_size;
        this.ten_size = ten_size;
    }

    public Size() {
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
}
