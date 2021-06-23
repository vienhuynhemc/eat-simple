package com.vientamthuong.eatsimple.model;

public class AccountFirebase {
    private String email;
    private String hinh_dai_dien;
    private String link_hinh_dai_dien;
    private String mat_khau;
    private String ngay_tao;
    private String tai_khoan;
    private String ten_hien_thi;

    public AccountFirebase(){

    }

    public AccountFirebase(String email, String hinh_dai_dien, String link_hinh_dai_dien, String mat_khau, String ngay_tao, String tai_khoan, String ten_hien_thi) {
        this.email = email;
        this.hinh_dai_dien = hinh_dai_dien;
        this.link_hinh_dai_dien = link_hinh_dai_dien;
        this.mat_khau = mat_khau;
        this.ngay_tao = ngay_tao;
        this.tai_khoan = tai_khoan;
        this.ten_hien_thi = ten_hien_thi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHinh_dai_dien() {
        return hinh_dai_dien;
    }

    public void setHinh_dai_dien(String hinh_dai_dien) {
        this.hinh_dai_dien = hinh_dai_dien;
    }

    public String getLink_hinh_dai_dien() {
        return link_hinh_dai_dien;
    }

    public void setLink_hinh_dai_dien(String link_hinh_dai_dien) {
        this.link_hinh_dai_dien = link_hinh_dai_dien;
    }

    public String getMat_khau() {
        return mat_khau;
    }

    public void setMat_khau(String mat_khau) {
        this.mat_khau = mat_khau;
    }

    public String getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(String ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public String getTai_khoan() {
        return tai_khoan;
    }

    public void setTai_khoan(String tai_khoan) {
        this.tai_khoan = tai_khoan;
    }

    public String getTen_hien_thi() {
        return ten_hien_thi;
    }

    public void setTen_hien_thi(String ten_hien_thi) {
        this.ten_hien_thi = ten_hien_thi;
    }

    @Override
    public String toString() {
        return "AccountFirebase{" +
                "email='" + email + '\'' +
                ", hinh_dai_dien='" + hinh_dai_dien + '\'' +
                ", link_hinh_dai_dien='" + link_hinh_dai_dien + '\'' +
                ", mat_khau='" + mat_khau + '\'' +
                ", ngay_tao='" + ngay_tao + '\'' +
                ", tai_khoan='" + tai_khoan + '\'' +
                ", ten_hien_thi='" + ten_hien_thi + '\'' +
                '}';
    }
}
