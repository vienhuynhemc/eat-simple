package com.vientamthuong.eatsimple.loadProductByID;

public class LoadProductHelp {

    private static LoadProductHelp loadProductHelp;
    private int num = 0;
    private String ma_danh_muc_hien_tai;
    private boolean kiem_tra_danh_muc_moi;
    private int Y_MIN;

    private LoadProductHelp(
    ){
        kiem_tra_danh_muc_moi = false;
        setYMIN(146);
    }

    public static LoadProductHelp getLoadProductHelp() {
        if (loadProductHelp == null){
            loadProductHelp = new LoadProductHelp();
        }
        return loadProductHelp;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMa_danh_muc_hien_tai() {
        return ma_danh_muc_hien_tai;
    }

    public void setMa_danh_muc_hien_tai(String ma_danh_muc_hien_tai) {
        this.ma_danh_muc_hien_tai = ma_danh_muc_hien_tai;
    }

    public boolean isKiem_tra_danh_muc_moi() {
        return kiem_tra_danh_muc_moi;
    }

    public void setKiem_tra_danh_muc_moi(boolean kiem_tra_danh_muc_moi) {
        this.kiem_tra_danh_muc_moi = kiem_tra_danh_muc_moi;
    }

    public int getYMIN() {
        return Y_MIN;
    }

    public void setYMIN(int YMIN) {
        this.Y_MIN = YMIN;
    }
}
