package com.vientamthuong.eatsimple.wishlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.vientamthuong.eatsimple.R;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);


        RecyclerView recyclerView = findViewById(R.id.activity_wishlist_recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        WishlistAdapter wishlistAdapter = new WishlistAdapter(this,getProducts());
        recyclerView.setAdapter(wishlistAdapter);

    }
    public ArrayList<Wishlist> getProducts(){
        ArrayList<Wishlist> products = new ArrayList<>();
        products.add(new Wishlist("Pizza ","abc",59000,1,R.drawable.activity_detail_ham));



        return products;
    }

}