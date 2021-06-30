package com.vientamthuong.eatsimple.beans;

import java.util.List;

public class Huyen {
    private String ten;
    private List<Xa> list;

    public Huyen(String ten, List<Xa> list) {
        this.ten = ten;
        this.list = list;
    }

    public Huyen() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public List<Xa> getList() {
        return list;
    }

    public void setList(List<Xa> list) {
        this.list = list;
    }
}
