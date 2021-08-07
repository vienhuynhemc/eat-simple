package com.vientamthuong.eatsimple.checkout;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.beans.Address;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductConfiguration;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHandler;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHelp;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAddress {
    public static void getData(Context context){

        String urlLoad = "https://eat-simple-app.000webhostapp.com/getAddress.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            List<Address> addresses = new ArrayList<>();

                            for (int i = 0;i<jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);

                                Address address = new Address();
                                address.setMa_kh(DataLocalManager.getAccount().getId());
                                address.setMa_add(object.getString("ma_add"));
                                address.setName(object.getString("ten_kh"));
                                address.setDiachi(object.getString("dia_chi_kh"));
                                address.setGhichu(object.getString("ghi_chu_kh"));
                                address.setSodienthoai(object.getString("sdt_kh"));

                                addresses.add(address);

                            }
                            Message message = new Message();
                            message.what = 102;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("adds", (Serializable) addresses);
                            message.setData(bundle);
                           CheckoutHandler.getInstance().getHandler().sendMessage(message);
                            } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
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
                return params;
            }
        };
        VolleyPool.getInstance(context).addRequest(request);
    }
}
