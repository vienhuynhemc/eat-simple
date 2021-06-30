package com.vientamthuong.eatsimple.beans;

import java.util.List;

public class Tinh {

    private String ten;
    private List<Huyen> list;

    public Tinh() {
    }

    public Tinh(String ten, List<Huyen> list) {
        this.ten = ten;
        this.list = list;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public List<Huyen> getList() {
        return list;
    }

    public void setList(List<Huyen> list) {
        this.list = list;
    }
}
