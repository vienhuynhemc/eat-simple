package com.vientamthuong.eatsimple.admin.sanPham;

import com.vientamthuong.eatsimple.admin.model.DanhMuc;
import com.vientamthuong.eatsimple.admin.model.SanPham;

public class SanPhamSession {

    private static SanPhamSession dataSession;
    private SanPham danhMuc;

    private SanPhamSession() {
    }

    public static SanPhamSession getInstance() {
        if (dataSession == null) {
            dataSession = new SanPhamSession();
        }
        return dataSession;
    }

    public SanPham getSanPham() {
        return danhMuc;
    }

    public void setSanPham(SanPham danhMuc) {
        this.danhMuc = danhMuc;
    }
}