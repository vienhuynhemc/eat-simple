package com.vientamthuong.eatsimple.loadProductByID;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.vientamthuong.eatsimple.beans.Product;

import java.util.List;
import java.util.logging.LogRecord;

public class LoadProductHandler {

    private Handler handler;
    private List<Product> productList;
    private LoadProductViewAdapter loadProductViewAdapter;
    private static LoadProductHandler loadProductHandler;

    private LoadProductHandler(){
       // getHandler();
    }
    public static LoadProductHandler getLoadPoductHandler(){
        if (loadProductHandler == null){
            loadProductHandler = new LoadProductHandler();
        }
        return loadProductHandler;
    }

    public Handler getHandler() {

        if (handler == null){
             handler = new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 99){
                        Bundle bundle = msg.getData();
                        List<Product> products = (List<Product>) bundle.getSerializable("products");

                        if (LoadProductHelp.getLoadProductHelp().isKiem_tra_danh_muc_moi()){
                            productList.clear();
                            LoadProductHelp.getLoadProductHelp().setKiem_tra_danh_muc_moi(false);
                        }
                        productList.addAll(products);
                        loadProductViewAdapter.notifyDataSetChanged();
                        System.out.println("đã add vào " + products.size());
                    }
                }
            };

        }

        return handler;
    }

    public void setLoadProductViewAdapter(LoadProductViewAdapter loadProductViewAdapter) {
        this.loadProductViewAdapter = loadProductViewAdapter;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
