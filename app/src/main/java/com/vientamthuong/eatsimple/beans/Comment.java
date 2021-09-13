package com.vientamthuong.eatsimple.beans;

import android.graphics.Bitmap;

import com.vientamthuong.eatsimple.date.DateTime;

public class Comment {
    private String ten;
    private String noidung;
    private int sosao;
    private String url;
    private String ten_sp;
    private String size;
    private DateTime time;
    private Bitmap bitmap;
    private double avg_so_sao;
    private boolean isload;

    public Comment() {
    }

    public double getAvg_so_sao() {
        return avg_so_sao;
    }

    public void setAvg_so_sao(double avg_so_sao) {
        this.avg_so_sao = avg_so_sao;
    }

    public boolean isIsload() {
        return isload;
    }

    public void setIsload(boolean isload) {
        this.isload = isload;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public int getSosao() {
        return sosao;
    }

    public void setSosao(int sosao) {
        this.sosao = sosao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTen_sp() {
        return ten_sp;
    }

    public void setTen_sp(String ten_sp) {
        this.ten_sp = ten_sp;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
