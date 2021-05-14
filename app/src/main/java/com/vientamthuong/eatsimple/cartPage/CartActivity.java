package com.vientamthuong.eatsimple.cartPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<CartModel> list_Item;
    private CartAdapter cartAdapter;
    private LinearLayout lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cart);
        // ánh xạ
        getView();
        anim();
        // load data
        loadData();
        // khỏi tạo
        cartRecycle();


    }
    void getView(){
        recyclerView = findViewById(R.id.list_item);
        lottieAnimationView = findViewById(R.id.activity_home_page_layout_location);

    }
    void anim(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.activity_cart_translate);
        lottieAnimationView.startAnimation(animation);
    }

    void cartRecycle(){
        cartAdapter = new CartAdapter(list_Item);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(cartAdapter);
    }
    void loadData(){
        list_Item = new ArrayList<>();
        list_Item.add(new CartModel(R.drawable.activity_introductory_logo,"Sản phẩm 1","Nội dung 1",20000,2));
        list_Item.add(new CartModel(R.drawable.activity_introductory_logo,"Sản phẩm 2","Nội dung 2",20000,2));
        list_Item.add(new CartModel(R.drawable.activity_introductory_logo,"Sản phẩm 3","Nội dung 3",20000,2));
        list_Item.add(new CartModel(R.drawable.activity_introductory_logo,"Sản phẩm 4","Nội dung 4",20000,2));
        list_Item.add(new CartModel(R.drawable.activity_introductory_logo,"Sản phẩm 5","Nội dung 5",20000,2));
        list_Item.add(new CartModel(R.drawable.activity_introductory_logo,"Sản phẩm 6","Nội dung 6",20000,2));
        list_Item.add(new CartModel(R.drawable.activity_introductory_logo,"Sản phẩm 7","Nội dung 7",20000,2));
        list_Item.add(new CartModel(R.drawable.activity_introductory_logo,"Sản phẩm 8","Nội dung 8",20000,2));
        list_Item.add(new CartModel(R.drawable.activity_introductory_logo,"Sản phẩm 9","Nội dung 9",20000,2));
        list_Item.add(new CartModel(R.drawable.activity_introductory_logo,"Sản phẩm 10","Nội dung 10",20000,2));
        list_Item.add(new CartModel(R.drawable.activity_introductory_logo,"Sản phẩm 11","Nội dung 11",20000,2));

    }
}