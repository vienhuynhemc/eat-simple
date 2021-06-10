package com.vientamthuong.eatsimple.admin.session;

public class DataSession {

    private static DataSession dataSession;
    private String ma_tai_khoan;
    private int cap_do;

    private DataSession() {
        ma_tai_khoan = "nv_1";
        cap_do = 0;
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

    public void setCap_do(int cap_do) {
        this.cap_do = cap_do;
    }

    public String getMa_tai_khoan() {
        return ma_tai_khoan;
    }

    public int getCap_do() {
        return cap_do;
    }
}
