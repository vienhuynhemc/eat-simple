package com.vientamthuong.eatsimple.detailOrder;

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
import com.vientamthuong.eatsimple.beans.Cart;
import com.vientamthuong.eatsimple.cartPage.CartAdapter;
import com.vientamthuong.eatsimple.cartPage.CartConfiguration;
import com.vientamthuong.eatsimple.cartPage.LoadCartHandler;
import com.vientamthuong.eatsimple.cartPage.LoadCartHelper;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDetailOrder {

    public static void getData(Context context,String ma_dh){

        String urlLoad = "https://eat-simple-app.000webhostapp.com/loadProductDetail.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            List<Cart> carts = new ArrayList<>();

                            for (int i = 0;i<jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);

                                Cart cart = new Cart();

                                cart.setMa_sp(object.getString("ma_sp"));
                                cart.setTen_sp(object.getString("ten_sp"));
                                cart.getSizes().setMa_size(object.getString("ma_size"));
                                cart.getSizes().setTen_size(object.getString("ten_size"));
                                cart.setMa_kh(DataLocalManager.getAccount().getId());
                                cart.setSo_luong(Integer.parseInt(object.getString("so_luong")));
                                cart.setHinh(object.getString("hinh"));
                                cart.setUrl(object.getString("url"));
                                cart.setMa_dm(object.getString("ma_danh_muc"));
                                cart.setTen_dm(object.getString("ten_danh_muc"));
                                cart.setSo_luong_con_lai(Integer.parseInt(object.getString("so_luong_con_lai")));
                                cart.setGia(Integer.parseInt(object.getString("gia")));
                                cart.setGia_km(Integer.parseInt(object.getString("gia_km")));
                                cart.setSo_luong_ban_ra(Integer.parseInt(object.getString("so_luong_ban_ra")));
                                cart.setThong_tin(object.getString("thong_tin"));
                                cart.setThoi_gian_nau(Integer.parseInt(object.getString("thoi_gian_nau")));
                                cart.setKcal(Integer.parseInt(object.getString("kcal")));
                                System.out.println("Size order:" + cart.getSizes().getTen_size());

                                carts.add(cart);

                            }
                            Message message = new Message();
                            message.what = 149;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("carts", (Serializable) carts);
                            message.setData(bundle);
                            System.out.println("SIZE: "+ carts.size());
                            LoadCartHandler.getInstance().getHandler().sendMessage(message);
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
                params.put("ma_dh", ma_dh);
                return params;
            }
        };
        VolleyPool.getInstance(context).addRequest(request);
    }

    public static void getBitmapImage(Cart cart, Context context, DetailOrderAdapter adapter){
        ImageRequest imageRequest = new ImageRequest(cart.getUrl(), response -> {
            cart.setLoadImg(true);
            cart.setBitmap(response);
            adapter.notifyDataSetChanged();
        },0,0, ImageView.ScaleType.FIT_CENTER, null, error -> {
            Toast.makeText(context, "Lỗi tải hình sản phẩm!", Toast.LENGTH_SHORT).show();
        });
        Volley.newRequestQueue(context).add(imageRequest);

    }


}
