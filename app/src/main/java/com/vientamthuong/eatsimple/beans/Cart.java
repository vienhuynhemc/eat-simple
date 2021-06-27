package com.vientamthuong.eatsimple.beans;

import android.graphics.Bitmap;

import com.vientamthuong.eatsimple.date.DateTime;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {

    private String ma_sp;
    private String ma_kh;
    private int so_luong;
    private static final long serialVersionUID = 1L;
    private String ten_sp;
    private String hinh;
    private String url;
    private int kcal;
    private int thoi_gian_nau;
    private int ton_tai;
    private int trang_thai;
    private DateTime ngay_tao;
    private String ma_dm;
    private String ten_dm;
    private Size sizes;
    private int so_luong_con_lai;
    private int gia;
    private int gia_km;
    private String thong_tin;
    private Bitmap bitmap;
    private boolean isLoadImg;

    public Cart() {
        sizes = new Size();
    }

    public String getMa_sp() {
        return ma_sp;
    }

    public String getTen_dm() {
        return ten_dm;
    }

    public void setTen_dm(String ten_dm) {
        this.ten_dm = ten_dm;
    }

    public void setMa_sp(String ma_sp) {
        this.ma_sp = ma_sp;
    }

    public String getMa_kh() {
        return ma_kh;
    }

    public void setMa_kh(String ma_kh) {
        this.ma_kh = ma_kh;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTen_sp() {
        return ten_sp;
    }

    public void setTen_sp(String ten_sp) {
        this.ten_sp = ten_sp;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getThoi_gian_nau() {
        return thoi_gian_nau;
    }

    public void setThoi_gian_nau(int thoi_gian_nau) {
        this.thoi_gian_nau = thoi_gian_nau;
    }

    public int getTon_tai() {
        return ton_tai;
    }

    public void setTon_tai(int ton_tai) {
        this.ton_tai = ton_tai;
    }

    public int getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }

    public DateTime getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(DateTime ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public String getMa_dm() {
        return ma_dm;
    }

    public void setMa_dm(String ma_dm) {
        this.ma_dm = ma_dm;
    }

    public Size getSizes() {
        return sizes;
    }

    public void setSizes(Size sizes) {
        this.sizes = sizes;
    }

    public int getSo_luong_con_lai() {
        return so_luong_con_lai;
    }

    public void setSo_luong_con_lai(int so_luong_con_lai) {
        this.so_luong_con_lai = so_luong_con_lai;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getGia_km() {
        return gia_km;
    }

    public void setGia_km(int gia_km) {
        this.gia_km = gia_km;
    }

    public String getThong_tin() {
        return thong_tin;
    }

    public void setThong_tin(String thong_tin) {
        this.thong_tin = thong_tin;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isLoadImg() {
        return isLoadImg;
    }

    public void setLoadImg(boolean loadImg) {
        isLoadImg = loadImg;
    }
}
