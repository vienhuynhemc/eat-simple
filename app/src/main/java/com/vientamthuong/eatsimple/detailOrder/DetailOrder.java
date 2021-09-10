package com.vientamthuong.eatsimple.detailOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Cart;
import com.vientamthuong.eatsimple.cartPage.GetCart;
import com.vientamthuong.eatsimple.cartPage.LoadCartHandler;
import com.vientamthuong.eatsimple.checkout.AddressActivity;
import com.vientamthuong.eatsimple.checkout.PayActivity;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductViewAdapter;
import com.vientamthuong.eatsimple.menuNotify.EventRing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailOrder extends AppCompatActivity {

    private ScrollView scrollView;
    private RecyclerView recyclerView;
    private List<Cart> cartList;
    private DetailOrderAdapter adapter;
    private FloatingActionButton back;
    private ImageView ring;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        getView();
        init();
        event();

    }
    void init(){
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new DetailOrderAdapter(cartList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void getView(){
        scrollView = findViewById(R.id.scroll_sp);
        recyclerView = findViewById(R.id.list_sp);
        cartList = new ArrayList<>();
        back = findViewById(R.id.detail_back);
        ring = findViewById(R.id.notify);
    }
    public void eventRing(){
        //EventRing.getInstance().setContext(getContext());
        EventRing.getInstance().setView(ring);
        EventRing.getInstance().startAnim();
    }
    private void event(){

        Intent intent = getIntent();
        String ma_dh = intent.getStringExtra("ma_dh");
        LoadCartHandler handler = LoadCartHandler.getInstance();
        handler.setOrder(cartList);
        handler.setOrderAdapter(adapter);
        handler.getHandler();
        GetDetailOrder.getData(DetailOrder.this,ma_dh);
        eventRing();

        back.setOnClickListener(v -> {
           finish();
        });
    }
}