package com.vientamthuong.eatsimple.cartPage;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.beans.Cart;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class DeleteCart {

    public void deleteCart(Handler handler,Context context, String ma_sp, String ma_size){
        String urlLoad = "https://eat-simple-app.000webhostapp.com/deleteCart.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Message message = new Message();
                            message.what = 101;
                            Bundle bundle = new Bundle();
                            bundle.putString("Result",response);
                            message.setData(bundle);
                           handler.sendMessage(message);
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
                params.put("ma_kh",DataLocalManager.getAccount().getId());

                return params;
            }
        };
        VolleyPool.getInstance(context).addRequest(request);


    }

}
