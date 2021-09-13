package com.vientamthuong.eatsimple.loadProductByID;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vientamthuong.eatsimple.beans.Comment;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.cartPage.LoadCartHandler;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetSoSao {

    public static void getData(LoadProductViewHolder holder, Product product, LoadProductViewAdapter adapter){

        String urlLoad = "https://eat-simple-app.000webhostapp.com/getSoSao.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            double sosao = Double.parseDouble(response);
                            product.setSosao((double) Math.round(sosao * 10) / 10);
                            adapter.notifyDataSetChanged();
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
                params.put("ma_sp",product.getMa_sp());
                return params;
            }
        };
        VolleyPool.getInstance(holder.getCardView().getContext()).addRequest(request);
    }

}
