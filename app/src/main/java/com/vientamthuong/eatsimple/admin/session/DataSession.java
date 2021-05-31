package com.vientamthuong.eatsimple.admin.session;

public class DataSession {

    private static DataSession dataSession;
    private String ma_tai_khoan;

    private DataSession() {
        ma_tai_khoan = "nv_1";
    }

    public static DataSession getInstance() {
        if (dataSession == null) {
            dataSession = new DataSession();
        }
        return dataSession;
    }

    public void setMaTaiKhoan(String ma_tai_khoan) {
        this.ma_tai_khoan = ma_tai_khoan;
    }

}
