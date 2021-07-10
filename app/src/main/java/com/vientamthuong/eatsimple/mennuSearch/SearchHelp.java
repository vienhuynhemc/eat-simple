package com.vientamthuong.eatsimple.mennuSearch;

import com.vientamthuong.eatsimple.loadProductByID.LoadProductHelp;

public class SearchHelp {
    private static SearchHelp loadProductHelp;
    private int num = 0;
    private int state;
    private String edit;

    private int Y_MIN;


    private SearchHelp(
    ){
        state = 0;
        setYMIN(140);

    }

    public static SearchHelp getLoadProductHelp() {
        if (loadProductHelp == null){
            loadProductHelp = new SearchHelp();
        }
        return loadProductHelp;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }
}
