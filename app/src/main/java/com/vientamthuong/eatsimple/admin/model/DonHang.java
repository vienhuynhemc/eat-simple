package com.vientamthuong.eatsimple.admin.model;

import com.vientamthuong.eatsimple.date.DateTime;

import java.util.ArrayList;
import java.util.List;

public class DonHang {

    private String ma_dh;
    private KhachHangDonHang khachHangDonHang;
    private DateTime ngay_tao;
    private String ten_nguoi_nhan;
    private String dia_chi;
    private String so_dien_thoai;
    private String ma_giam_gia;
    private int trang_thai;
    private List<SanPhamDonHang> sanPhamDonHangs;
    private int tong_tien;

    public DonHang(String ma_dh, KhachHangDonHang khachHangDonHang, DateTime ngay_tao, String ten_nguoi_nhan, String dia_chi, String so_dien_thoai, String ma_giam_gia, int trang_thai, List<SanPhamDonHang> sanPhamDonHangs) {
        this.ma_dh = ma_dh;
        this.khachHangDonHang = khachHangDonHang;
        this.ngay_tao = ngay_tao;
        this.ten_nguoi_nhan = ten_nguoi_nhan;
        this.dia_chi = dia_chi;
        this.so_dien_thoai = so_dien_thoai;
        this.ma_giam_gia = ma_giam_gia;
        this.trang_thai = trang_thai;
        this.sanPhamDonHangs = sanPhamDonHangs;
    }

    public DonHang() {

    }

    public DonHang(DonHang donHang) {
        this.ma_dh = donHang.getMa_dh();
        if (donHang.getKhachHangDonHang() != null) {
            this.khachHangDonHang = new KhachHangDonHang(donHang.getKhachHangDonHang().getTen_hien_thi(), donHang.getKhachHangDonHang().getMa_kh(), donHang.getKhachHangDonHang().getHinh(),donHang.getKhachHangDonHang().getUrl());
        }
        this.ngay_tao = donHang.ngay_tao;
        this.ten_nguoi_nhan = donHang.ten_nguoi_nhan;
        this.dia_chi = donHang.dia_chi;
        this.so_dien_thoai = donHang.so_dien_thoai;
        this.ma_giam_gia = donHang.ma_giam_gia;
        this.trang_thai = donHang.trang_thai;
        if (donHang.getSanPhamDonHangs() != null) {
            List<SanPhamDonHang> list = new ArrayList<>();
            for (SanPhamDonHang sanPhamDonHang : donHang.sanPhamDonHangs) {
                list.add(new SanPhamDonHang(sanPhamDonHang.getMa_sp(), sanPhamDonHang.getTen_sp(), sanPhamDonHang.getHinh(), sanPhamDonHang.getMa_size(), sanPhamDonHang.getTen_size(), sanPhamDonHang.getSo_luong(), sanPhamDonHang.getTien(),sanPhamDonHang.getUrl()));
            }
            this.sanPhamDonHangs = list;
        }
        this.tong_tien = donHang.tong_tien;
    }

    public String getMa_dh() {
        return ma_dh;
    }

    public void setMa_dh(String ma_dh) {
        this.ma_dh = ma_dh;
    }

    public KhachHangDonHang getKhachHangDonHang() {
        return khachHangDonHang;
    }

    public void setKhachHangDonHang(KhachHangDonHang khachHangDonHang) {
        this.khachHangDonHang = khachHangDonHang;
    }

    public DateTime getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(DateTime ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public String getTen_nguoi_nhan() {
        return ten_nguoi_nhan;
    }

    public void setTen_nguoi_nhan(String ten_nguoi_nhan) {
        this.ten_nguoi_nhan = ten_nguoi_nhan;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public String getMa_giam_gia() {
        return ma_giam_gia;
    }

    public void setMa_giam_gia(String ma_giam_gia) {
        this.ma_giam_gia = ma_giam_gia;
    }

    public int getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }

    public List<SanPhamDonHang> getSanPhamDonHangs() {
        return sanPhamDonHangs;
    }

    public void setSanPhamDonHangs(List<SanPhamDonHang> sanPhamDonHangs) {
        this.sanPhamDonHangs = sanPhamDonHangs;
    }

    public int getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(int tong_tien) {
        this.tong_tien = tong_tien;
    }
}
