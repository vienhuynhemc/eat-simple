package com.vientamthuong.eatsimple.admin.danhMuc;

import com.vientamthuong.eatsimple.admin.model.DanhMuc;

public class DanhMucSession {

    private static DanhMucSession dataSession;
    private DanhMuc danhMuc;

    private DanhMucSession() {
    }

    public static DanhMucSession getInstance() {
        if (dataSession == null) {
            dataSession = new DanhMucSession();
        }
        return dataSession;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }
}
