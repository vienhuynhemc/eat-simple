package com.vientamthuong.eatsimple.admin.donHang;

import com.vientamthuong.eatsimple.admin.model.DonHang;
import com.vientamthuong.eatsimple.admin.model.SanPham;
import com.vientamthuong.eatsimple.admin.sanPham.SanPhamSession;

public class DonHangSession {

    private static DonHangSession dataSession;
    private DonHang donHang;

    private DonHangSession() {
    }

    public static DonHangSession getInstance() {
        if (dataSession == null) {
            dataSession = new DonHangSession();
        }
        return dataSession;
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }

}
