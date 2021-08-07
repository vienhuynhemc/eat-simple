package com.vientamthuong.eatsimple.cartPage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import java.util.HashMap;
import java.util.Map;

public class UpdateCart {

    public void deleteCart( Context context, String ma_sp, String ma_size,int active){
        String urlLoad = "https://eat-simple-app.000webhostapp.com/updateCart.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("FAIL")){

                            Toast.makeText(context, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();

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
                params.put("ma_sp",ma_sp);
                params.put("ma_size", ma_size);
                params.put("ma_kh", DataLocalManager.getAccount().getId());
                params.put("active", String.valueOf(active));

                return params;
            }
        };
        VolleyPool.getInstance(context).addRequest(request);


    }

}
