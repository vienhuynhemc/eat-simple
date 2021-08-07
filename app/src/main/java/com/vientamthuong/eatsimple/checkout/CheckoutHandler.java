package com.vientamthuong.eatsimple.checkout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.beans.Address;
import com.vientamthuong.eatsimple.beans.Cart;
import com.vientamthuong.eatsimple.cartPage.CartAdapter;
import com.vientamthuong.eatsimple.cartPage.LoadCartHandler;

import java.util.ArrayList;
import java.util.List;

public class CheckoutHandler {
    private Handler handler;
    private List<Address> adds;
    private PayAddessAdapter adapter;
    private static CheckoutHandler loadCartHandler;
    private RecyclerView recyclerView;
    private CheckoutHandler(){
        // getHandler();
    }
    public static CheckoutHandler getInstance(){
        if (loadCartHandler == null){
            loadCartHandler = new CheckoutHandler();
        }
        return loadCartHandler;
    }

    public Handler getHandler() {

        if (handler == null){
            handler = new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 102){
                        Bundle bundle = msg.getData();
                        List<Address> cart = (List<Address>) bundle.getSerializable("adds");
                        adds.addAll(cart);
                        adapter.notifyDataSetChanged();

//                        List<View > views = new ArrayList<>();
//                        for (int i= 0;i<cart.size();i++){
//                            views.add(recyclerView.getChildAt(i));
//                        }
//
//                        adapter.setViews(views);
                    }
                }
            };

        }

        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public List<Address> getAdds() {
        return adds;
    }

    public void setAdds(List<Address> adds) {
        this.adds = adds;
    }

    public PayAddessAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PayAddessAdapter adapter) {
        this.adapter = adapter;
    }

    public static CheckoutHandler getLoadCartHandler() {
        return loadCartHandler;
    }

    public static void setLoadCartHandler(CheckoutHandler loadCartHandler) {
        CheckoutHandler.loadCartHandler = loadCartHandler;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
