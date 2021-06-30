package com.vientamthuong.eatsimple.checkout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.beans.Address;
import com.vientamthuong.eatsimple.beans.Cart;
import com.vientamthuong.eatsimple.beans.MaGiamGia;
import com.vientamthuong.eatsimple.beans.MaGiamGiaConfiguration;
import com.vientamthuong.eatsimple.cartPage.CartConfiguration;
import com.vientamthuong.eatsimple.cartPage.GetCart;
import com.vientamthuong.eatsimple.cartPage.LoadCartHandler;
import com.vientamthuong.eatsimple.cartPage.LoadCartHelper;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.homePage.HomeMeowBottom;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayActivity extends AppCompatActivity {

    private RecyclerView reDiaChi,recyclerView, reSanPham;
    private AppCompatButton appgiamgia;
    private EditText giamgia;
    private TextView tamtinh, tongtien,phivc;
    private List<Cart> carts;
    private List<Address> addresses;
    private FloatingActionButton back;
    private List<View> views;
    private MaGiamGia maGiamGia;
    private CardView themdiahchi;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        getView();

        init();

        event();
    }

    void event(){
        eventBack();
        eventMaGiamGia();
        eventThemDiaChi();
    }
    void eventThemDiaChi(){
        themdiahchi.setOnClickListener(v -> {

            Intent intent = new Intent(PayActivity.this,AddressActivity.class);

            intent.putExtra("carts", (Serializable) carts);

            startActivity(intent);

        });
    }
    void init(){
        initProduct();
        initAddress();
        phivc.setText(MaGiamGiaConfiguration.PHIVANCHUYEN + " VND");
        views = new ArrayList<>();
        recyclerView = reDiaChi;
    }
    void eventMaGiamGia(){


            appgiamgia.setOnClickListener(v -> {
                if (count == 0) {
                    String magg = giamgia.getText().toString().trim();
                    System.out.println("MAGIAMGIA: " + magg);

                    String urlLoad = "https://eat-simple-app.000webhostapp.com/getMaGiamGia.php";
                    StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.equals("NONE")){
                                        Toast.makeText(PayActivity.this, "Rất tiếc mã này không có hiệu lực, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                                    }else {
                                        try {

                                            maGiamGia = new MaGiamGia();

                                            JSONObject object = new JSONObject(response);

                                            maGiamGia.setMagg(object.getString("ma_mgg"));
                                            maGiamGia.setKieugg(Integer.parseInt(object.getString("kieu_mgg")));
                                            maGiamGia.setGiatri(Integer.parseInt(object.getString("gia_tri")));
                                            maGiamGia.setSolansudung(Integer.parseInt(object.getString("so_lan_su_dung")));
                                            maGiamGia.setSolansudungtoida(Integer.parseInt(object.getString("so_lan_su_dung_toi_da")));
                                            maGiamGia.setHansudung(new DateTime(object.getString("han_su_dung")));
                                            maGiamGia.setNgaytao(new DateTime(object.getString("ngay_tao")));
                                            maGiamGia.setTontai(Integer.parseInt(object.getString("tontai")));

                                            int tiencu = Integer.parseInt(tongtien.getText().toString().trim().substring(0,tongtien.getText().toString().trim().indexOf(" VND")).trim());

                                            int tienmoi = maGiamGia.convert(tiencu);

                                            Toast.makeText(PayActivity.this, maGiamGia.print(), Toast.LENGTH_SHORT).show();

                                            tongtien.setText(tienmoi + " VND");
                                            count++;

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                        @Nullable
                        @org.jetbrains.annotations.Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("ma_mgg", magg);
                            return params;
                        }
                    };
                    VolleyPool.getInstance(this).addRequest(request);
                } else {
                    Toast.makeText(this, "Tham lam", Toast.LENGTH_SHORT).show();
                }

            });
    }

    void eventCheck() {

        for (int i = 0; i < reDiaChi.getChildCount(); i++) {

            View view = reDiaChi.getChildAt(i);
            CheckBox checkBox = view.findViewById(R.id.checkbox_cart);
            System.out.println("Check" );

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        System.out.println("Check0" );
                        for (int i = 0; i < reDiaChi.getChildCount(); i++) {
                            View view = reDiaChi.getChildAt(i);
                            CheckBox checkBox1 = view.findViewById(R.id.checkbox_cart);
                            System.out.println("Check1" );
                            if (checkBox1 != checkBox) {
                                checkBox1.setChecked(false);
                                System.out.println("Check2" );
                            }
                        }

                    }
                }
            });


        }

    }

    void eventBack() {
        back.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeMeowBottom.class);
            Bundle bundle = new Bundle();
            bundle.putString("dichuyen","cart");
            intent.putExtra("call",bundle);
            startActivity(intent);
        });
    }

    void initAddress() {
        addresses = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        reDiaChi.setLayoutManager(linearLayoutManager);
        reDiaChi.setHasFixedSize(true);

        PayAddessAdapter adapter = new PayAddessAdapter(addresses);

        reDiaChi.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        adapter.setReDiaChi(reDiaChi);


        CheckoutHandler handler = CheckoutHandler.getInstance();
        handler.setAdds(addresses);
        handler.setAdapter(adapter);
        handler.getHandler();
        GetAddress.getData(this);
    }

    void initProduct() {

        Intent integer = getIntent();

        carts = (List<Cart>) integer.getSerializableExtra("carts");

        if (carts != null) {
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
        for (int i = 0; i < carts.size(); i++) {
            tien += carts.get(i).getGia_km();
        }
        tamtinh.setText(tien + " VND");
        tongtien.setText(tien + MaGiamGiaConfiguration.PHIVANCHUYEN + " VND");
    }

    void getView() {
        reDiaChi = findViewById(R.id.list_diachi);
        reSanPham = findViewById(R.id.list_product);
        themdiahchi = findViewById(R.id.themdiachi);
        appgiamgia = findViewById(R.id.apdung);
        giamgia = findViewById(R.id.add_giamgia);
        tamtinh = findViewById(R.id.total_product);
        tongtien = findViewById(R.id.total_price);
        back = findViewById(R.id.detail_back);
        phivc = findViewById(R.id.total_vc);
        count = 0;

    }
}