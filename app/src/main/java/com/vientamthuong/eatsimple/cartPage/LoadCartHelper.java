package com.vientamthuong.eatsimple.cartPage;

import com.vientamthuong.eatsimple.loadProductByID.LoadProductHelp;

public class LoadCartHelper {
    private static LoadCartHelper loadCartHelper;
    private int num = 0;
    private int Y_MIN;

    private LoadCartHelper(
    ){
        setYMIN(146);
    }

    public static LoadCartHelper getInstance() {
        if (loadCartHelper == null){
            loadCartHelper = new LoadCartHelper();
        }
        return loadCartHelper;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getYMIN() {
        return Y_MIN;
    }

    public void setYMIN(int YMIN) {
        this.Y_MIN = YMIN;
    }

    public static LoadCartHelper getLoadCartHelper() {
        return loadCartHelper;
    }

    public static void setLoadCartHelper(LoadCartHelper loadCartHelper) {
        LoadCartHelper.loadCartHelper = loadCartHelper;
    }
}
