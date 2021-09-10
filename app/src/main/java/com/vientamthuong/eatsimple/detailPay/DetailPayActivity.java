package com.vientamthuong.eatsimple.detailPay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
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
import com.vientamthuong.eatsimple.beans.MaGiamGia;
import com.vientamthuong.eatsimple.beans.MaGiamGiaConfiguration;
import com.vientamthuong.eatsimple.checkout.CheckoutConfiguration;
import com.vientamthuong.eatsimple.checkout.PayActivity;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.detailOrder.DetailOrder;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.menuNotify.EventRing;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailPayActivity extends AppCompatActivity {

    private Button xem_chi_tiet;
    private TextView ma_don_hang,ngay_tao,tong_tien,phi_van_chuyen,giam_gia,thanh_tien,name,dia_chi,
    ghi_chu,so_dien_thoai;
    private String ma_dh;
    private FloatingActionButton back;
    private ImageView ring;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pay);

        getView();

        init();

        event();

    }
    void event(){

        back.setOnClickListener(v -> {
            finish();
        });

        EventRing.getInstance().setView(ring);
        EventRing.getInstance().startAnim();

        xem_chi_tiet.setOnClickListener(v -> {

            Intent intent = new Intent(DetailPayActivity.this, DetailOrder.class);
            intent.putExtra("ma_dh",getMa_don_hang());
            this.startActivity(intent);
        });

    }

    void init(){

        String urlLoad = "https://eat-simple-app.000webhostapp.com/getDetailOrder.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);

                            ma_don_hang.setText(object.getString("ma_dh"));
                            System.out.println("NGAY TẠO: " + object.getString("ngay_tao"));
                            ngay_tao.setText(new DateTime(object.getString("ngay_tao")).toStringDateTypeNumberStringNumber().toString());
                            MaGiamGia maGiamGia = new MaGiamGia();
                            maGiamGia.setKieugg(Integer.parseInt(object.getString("kieu_mgg")));
                            maGiamGia.setGiatri(Integer.parseInt(object.getString("gia_tri")));
                            System.out.println("KIEU MAGG: " + maGiamGia.getKieugg());
                            tong_tien.setText(maGiamGia.convert2(Integer.parseInt(object.getString("tong_tien"))) + " VND");
                            phi_van_chuyen.setText(MaGiamGiaConfiguration.PHIVANCHUYEN + " VND");
                            giam_gia.setText(maGiamGia.discount(Integer.parseInt(object.getString("tong_tien"))) + " VND");
                            thanh_tien.setText(object.getString("tong_tien") + " VND");
                            name.setText(object.getString("ten_nguoi_nhan"));
                            dia_chi.setText(object.getString("dia_chi"));
                            ghi_chu.setText(object.getString("ghi_chu"));
                            so_dien_thoai.setText(object.getString("so_dien_thoai"));

                        } catch (JSONException e) {
                            e.printStackTrace();
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
                params.put("ma_kh", DataLocalManager.getAccount().getId());
                params.put("ma_dh",ma_dh);
                return params;
            }
        };
        VolleyPool.getInstance(this).addRequest(request);

    }

    void getView(){
        xem_chi_tiet = findViewById(R.id.thanhtoan);
        ma_don_hang = findViewById(R.id.ma_dh);
        ngay_tao = findViewById(R.id.ngay_tao);
        tong_tien = findViewById(R.id.tong_tien);
        phi_van_chuyen = findViewById(R.id.phi_van_chuyen);
        giam_gia = findViewById(R.id.giam_gia);
        thanh_tien = findViewById(R.id.thanhtien);
        name = findViewById(R.id.textView11);
        dia_chi = findViewById(R.id.textView12);
        ghi_chu = findViewById(R.id.textView14);
        so_dien_thoai = findViewById(R.id.textView15);
        back = findViewById(R.id.detail_back);
        ring = findViewById(R.id.notify);

        Intent intent = getIntent();
        ma_dh =  intent.getStringExtra("ma_don_hang");


    }

    public String getMa_don_hang() {
        return ma_dh;
    }

}