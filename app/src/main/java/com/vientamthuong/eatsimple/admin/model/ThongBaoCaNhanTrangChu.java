package com.vientamthuong.eatsimple.admin.model;

import com.vientamthuong.eatsimple.date.DateTime;

public class ThongBaoCaNhanTrangChu {

    private DateTime ngay_tao;
    private String ten_nv_thuc_hien;
    private String cap_do_nv_thuc_hien;
    private String noi_dung;
    private String noi_dung_quan_trong;
    private int type;
    private String ten_nv_nhan;
    private String cap_do_nv_nhan;
    private String ma_nv_thuc_hien;
    private String ma_nv_nhan;

    public ThongBaoCaNhanTrangChu() {
    }

    public ThongBaoCaNhanTrangChu(ThongBaoCaNhanTrangChu thongBaoCaNhanTrangChu) {
        this.ngay_tao = thongBaoCaNhanTrangChu.getNgay_tao();
        this.ten_nv_thuc_hien = thongBaoCaNhanTrangChu.getTen_nv_thuc_hien();
        this.cap_do_nv_thuc_hien = thongBaoCaNhanTrangChu.getCap_do_nv_thuc_hien();
        this.noi_dung = thongBaoCaNhanTrangChu.getNoi_dung();
        this.noi_dung_quan_trong = thongBaoCaNhanTrangChu.getNoi_dung_quan_trong();
        this.type = thongBaoCaNhanTrangChu.getType();
        this.ten_nv_nhan = thongBaoCaNhanTrangChu.getTen_nv_nhan();
        this.cap_do_nv_nhan = thongBaoCaNhanTrangChu.getCap_do_nv_nhan();
        this.ma_nv_nhan = thongBaoCaNhanTrangChu.getMa_nv_nhan();
        this.ma_nv_thuc_hien = thongBaoCaNhanTrangChu.getMa_nv_thuc_hien();
    }

    public DateTime getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(DateTime ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public String getTen_nv_thuc_hien() {
        return ten_nv_thuc_hien;
    }

    public void setTen_nv_thuc_hien(String ten_nv_thuc_hien) {
        this.ten_nv_thuc_hien = ten_nv_thuc_hien;
    }

    public String getCap_do_nv_thuc_hien() {
        return cap_do_nv_thuc_hien;
    }

    public void setCap_do_nv_thuc_hien(String cap_do_nv_thuc_hien) {
        this.cap_do_nv_thuc_hien = cap_do_nv_thuc_hien;
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

    public String getTen_nv_nhan() {
        return ten_nv_nhan;
    }

    public void setTen_nv_nhan(String ten_nv_nhan) {
        this.ten_nv_nhan = ten_nv_nhan;
    }

    public String getCap_do_nv_nhan() {
        return cap_do_nv_nhan;
    }

    public void setCap_do_nv_nhan(String cap_do_nv_nhan) {
        this.cap_do_nv_nhan = cap_do_nv_nhan;
    }

    public String getMa_nv_thuc_hien() {
        return ma_nv_thuc_hien;
    }

    public void setMa_nv_thuc_hien(String ma_nv_thuc_hien) {
        this.ma_nv_thuc_hien = ma_nv_thuc_hien;
    }

    public String getMa_nv_nhan() {
        return ma_nv_nhan;
    }

    public void setMa_nv_nhan(String ma_nv_nhan) {
        this.ma_nv_nhan = ma_nv_nhan;
    }

    public String toResult() {
        String noi_dung_qt = noi_dung_quan_trong.charAt(noi_dung_quan_trong.length() - 1) == ',' ? noi_dung_quan_trong.substring(0, noi_dung_quan_trong.length() - 1) : noi_dung_quan_trong;
        String s = "";
        if (type == 0) {
            s += cap_do_nv_thuc_hien + " <b>" + ten_nv_thuc_hien + "</b> " + noi_dung + " <b>" + noi_dung_qt + "</b>";
        } else {
            String[] array = noi_dung.split("xxx");
            s += cap_do_nv_thuc_hien + " <b>" + ten_nv_thuc_hien + "</b> " + array[0] + " <b>" + noi_dung_qt + "</b> " + array[1] + " " + cap_do_nv_nhan + " <b>" + ten_nv_nhan + "</b>";
        }
        return s;
    }
}
