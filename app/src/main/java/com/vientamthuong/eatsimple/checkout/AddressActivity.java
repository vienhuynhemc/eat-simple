package com.vientamthuong.eatsimple.checkout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.vientamthuong.eatsimple.beans.Huyen;
import com.vientamthuong.eatsimple.beans.MaGiamGia;
import com.vientamthuong.eatsimple.beans.Tinh;
import com.vientamthuong.eatsimple.beans.Xa;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {

    private FloatingActionButton back;
    private EditText ten,ghichu,sodienthoai;
    private EditText tinh,quan,xa;
    private AppCompatButton luu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        getView();
        event();
        eventAdd();

    }
    void event(){
        eventAdd();
        eventBack();
    }
    void eventBack(){

        back.setOnClickListener(v -> {

            Intent intent = getIntent();
            List<Cart> carts = (List<Cart>) intent.getSerializableExtra("carts");

            Intent intent1 = new Intent(AddressActivity.this,PayActivity.class);

            intent1.putExtra("carts", (Serializable) carts);

            startActivity(intent1);

        });

    }
    void eventAdd(){

        luu.setOnClickListener(v -> {
            String name = ten.getText().toString().trim();
            String diachi = tinh.getText().toString().trim() + " - " + quan.getText().toString().trim() + " - " + xa.getText().toString().trim();
            String ghi_chu = ghichu.getText().toString().trim();
            String sdt = sodienthoai.getText().toString().trim();


            String urlLoad = "https://eat-simple-app.000webhostapp.com/addAddress.php";
            StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equals("SUCCESS")){
                                Toast.makeText(AddressActivity.this, "Thêm địa chỉ thành công", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AddressActivity.this, "Thêm địa chỉ thất bại", Toast.LENGTH_SHORT).show();
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
                    params.put("ten",name);
                    params.put("diachi",diachi);
                    params.put("ghichu",ghi_chu);
                    params.put("sodienthoai",sdt);
                    return params;
                }
            };
            VolleyPool.getInstance(this).addRequest(request);

        });

    }

    void getView(){
        back = findViewById(R.id.detail_back);
        ten = findViewById(R.id.add_name);
        ghichu = findViewById(R.id.ghichu_add);
        sodienthoai = findViewById(R.id.sodienthoai_add);
        tinh = findViewById(R.id.tinh);
        quan = findViewById(R.id.quan);
        xa = findViewById(R.id.xa);
        luu = findViewById(R.id.luu);

    }
}