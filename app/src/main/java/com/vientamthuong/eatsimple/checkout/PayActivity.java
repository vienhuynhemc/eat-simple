package com.vientamthuong.eatsimple.checkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Address;
import com.vientamthuong.eatsimple.beans.Cart;
import com.vientamthuong.eatsimple.cartPage.GetCart;
import com.vientamthuong.eatsimple.cartPage.LoadCartHandler;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity {

    private RecyclerView reDiaChi,reSanPham;
    private AppCompatButton appgiamgia;
    private EditText giamgia;
    private TextView tamtinh,tongtien,themdiahchi;
    private  List<Cart> carts;
    private List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        getView();
        initProduct();
        initAddress();
    }
    void initAddress(){
        addresses = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        reDiaChi.setLayoutManager(linearLayoutManager);
        reDiaChi.setHasFixedSize(true);
        PayAddessAdapter adapter = new PayAddessAdapter(addresses);
        reDiaChi.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        CheckoutHandler handler = CheckoutHandler.getInstance();
        handler.setAdds(addresses);
        handler.setAdapter(adapter);
        handler.getHandler();
        GetAddress.getData(this);
    }
    void initProduct(){

        Intent integer = getIntent();

        carts = (List<Cart>) integer.getSerializableExtra("carts");

       // System.out.println("SIZE CART:"+carts.size() + " TENSP:" + carts.get(0).getTen_sp());


        if (carts != null){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            reSanPham.setLayoutManager(linearLayoutManager);
            reSanPham.setHasFixedSize(true);
            PayProductAdapter adapter = new PayProductAdapter(carts);
            reSanPham.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        int tien = 0;
        for (int i = 0;i < carts.size();i++){
            tien += carts.get(i).getGia_km();
        }
        tamtinh.setText(tien+"");
        tongtien.setText(tien+"");

    }
    void getView(){
        reDiaChi = findViewById(R.id.list_diachi);
        reSanPham = findViewById(R.id.list_product);
        themdiahchi = findViewById(R.id.themdiachi);
        appgiamgia = findViewById(R.id.apdung);
        giamgia = findViewById(R.id.add_giamgia);
        tamtinh = findViewById(R.id.total_product);
        tongtien = findViewById(R.id.total_price);

    }
}