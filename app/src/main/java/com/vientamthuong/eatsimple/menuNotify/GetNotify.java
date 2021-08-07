package com.vientamthuong.eatsimple.menuNotify;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.beans.Address;
import com.vientamthuong.eatsimple.beans.ThongBao;
import com.vientamthuong.eatsimple.checkout.CheckoutHandler;
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

public class GetNotify {

    public static void getData(Context context){

        String urlLoad = "https://eat-simple-app.000webhostapp.com/getNotify.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            List<ThongBao> addresses = new ArrayList<>();

                            for (int i = 0;i<jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);

                                ThongBao thongBao = new ThongBao();
                                thongBao.setMa_nv_thuc_hien(object.getString("ma_kh"));
                                thongBao.setMa_thong_bao(object.getString("ma_tt"));
                                thongBao.setNoi_dung(object.getString("noi_dung"));
                                thongBao.setNoi_dung_quan_trong(object.getString("noi_dung_quan_trong"));
                                thongBao.setNgay_tao(new DateTime(object.getString("ngay_tao")));
                                thongBao.setType(Integer.parseInt(object.getString("type")));

                                addresses.add(thongBao);
                            }
                            System.out.println("THOng báo: " + addresses.size());
                            Message message = new Message();
                            message.what = 108;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("notifys", (Serializable) addresses);
                            message.setData(bundle);
                            NotifyHandler.getInstance().getHandler().sendMessage(message);
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
