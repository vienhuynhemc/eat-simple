package com.vientamthuong.eatsimple.admin.model;

public class DanhMucSanPham {

    private String tenDanhMuc;
    private String maDanhMuc;

    public DanhMucSanPham(String tenDanhMuc, String maDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
        this.maDanhMuc = maDanhMuc;
    }

    public DanhMucSanPham(DanhMucSanPham danhMucSanPham){
        this.tenDanhMuc = danhMucSanPham.getTenDanhMuc();
        this.maDanhMuc = danhMucSanPham.getMaDanhMuc();
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }
}
