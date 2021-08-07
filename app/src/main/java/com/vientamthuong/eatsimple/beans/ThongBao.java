package com.vientamthuong.eatsimple.beans;

import com.vientamthuong.eatsimple.date.DateTime;

import java.io.Serializable;

public class ThongBao implements Serializable {
    private DateTime ngay_tao;
    private String ma_nv_thuc_hien;
    private String ma_thong_bao;
    private String noi_dung;
    private String noi_dung_quan_trong;
    private int type;

    public ThongBao() {
    }

    public DateTime getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(DateTime ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public String getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(String noi_dung) {
        this.noi_dung = noi_dung;
    }

    public String getNoi_dung_quan_trong() {
        return noi_dung_quan_trong;
    }

    public void setNoi_dung_quan_trong(String noi_dung_quan_trong) {
        this.noi_dung_quan_trong = noi_dung_quan_trong;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMa_nv_thuc_hien() {
        return ma_nv_thuc_hien;
    }

    public void setMa_nv_thuc_hien(String ma_nv_thuc_hien) {
        this.ma_nv_thuc_hien = ma_nv_thuc_hien;
    }

    public String getMa_thong_bao() {
        return ma_thong_bao;
    }

    public void setMa_thong_bao(String ma_thong_bao) {
        this.ma_thong_bao = ma_thong_bao;
    }


}
