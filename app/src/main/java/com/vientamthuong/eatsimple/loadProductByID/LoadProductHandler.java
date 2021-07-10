package com.vientamthuong.eatsimple.loadProductByID;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.mennuSearch.SearchHelp;

import java.util.List;
import java.util.logging.LogRecord;

public class LoadProductHandler {

    private Handler handler;
    private List<Product> productList;
    private List<Product> productList2;
    private LoadProductViewAdapter loadProductViewAdapter;
    private LoadProductViewAdapter loadProductViewAdapter2;
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
                    }else if (msg.what == 15){
                        Bundle bundle = msg.getData();
                        List<Product> products = (List<Product>) bundle.getSerializable("products");

                        if (SearchHelp.getLoadProductHelp().getState() == 0){
                            productList2.clear();
                        }

                        productList2.addAll(products);
                        loadProductViewAdapter2.notifyDataSetChanged();
                        loadProductViewAdapter2.getFilter().filter("");
                        System.out.println("đã add vào notify " + products.size());
                    }
                }
            };

        }

        return handler;
    }

    public void setLoadProductViewAdapter(LoadProductViewAdapter loadProductViewAdapter) {
        this.loadProductViewAdapter = loadProductViewAdapter;
    }

    public LoadProductViewAdapter getLoadProductViewAdapter2() {
        return loadProductViewAdapter2;
    }

    public void setLoadProductViewAdapter2(LoadProductViewAdapter loadProductViewAdapter2) {
        this.loadProductViewAdapter2 = loadProductViewAdapter2;
    }

    public List<Product> getProductList2() {
        return productList2;
    }

    public void setProductList2(List<Product> productList2) {
        this.productList2 = productList2;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
