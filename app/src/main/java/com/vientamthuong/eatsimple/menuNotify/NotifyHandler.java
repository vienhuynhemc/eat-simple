package com.vientamthuong.eatsimple.menuNotify;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.beans.Address;
import com.vientamthuong.eatsimple.beans.ThongBao;
import com.vientamthuong.eatsimple.checkout.CheckoutHandler;
import com.vientamthuong.eatsimple.checkout.PayAddessAdapter;

import java.util.List;

public class NotifyHandler {
    private Handler handler;
    private List<ThongBao> adds;
    private NotifiAdapter adapter;
    private static NotifyHandler loadCartHandler;
    private NotifyHandler(){
        // getHandler();
    }
    public static NotifyHandler getInstance(){
        if (loadCartHandler == null){
            loadCartHandler = new NotifyHandler();
        }
        return loadCartHandler;
    }

    public Handler getHandler() {

        if (handler == null){
            handler = new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 108){
                        Bundle bundle = msg.getData();
                        List<ThongBao> cart = (List<ThongBao>) bundle.getSerializable("notifys");
                        adds.addAll(cart);
                        adapter.notifyDataSetChanged();
                    }
                }
            };

        }

        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public List<ThongBao> getAdds() {
        return adds;
    }

    public void setAdds(List<ThongBao> adds) {
        this.adds = adds;
    }

    public NotifiAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(NotifiAdapter adapter) {
        this.adapter = adapter;
    }

    public static NotifyHandler getLoadCartHandler() {
        return loadCartHandler;
    }

    public static void setLoadCartHandler(NotifyHandler loadCartHandler) {
        NotifyHandler.loadCartHandler = loadCartHandler;
    }
}
