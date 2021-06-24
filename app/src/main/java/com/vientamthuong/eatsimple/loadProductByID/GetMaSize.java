package com.vientamthuong.eatsimple.loadProductByID;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.beans.Size;
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

public class GetMaSize {

    public static void getData(Context context){

        String urlLoad = "https://eat-simple-app.000webhostapp.com/getMaSize.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            List<Size> products = new ArrayList<>();

                            for (int i = 0;i<jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);

                                Size product = new Size();


                                products.add(product);

                            }

                            if (products.size() == 0 && !LoadProductHelp.getLoadProductHelp().isKiem_tra_danh_muc_moi()){
                                Toast.makeText(context, "Sản phẩm đã hết", Toast.LENGTH_SHORT).show();
                            }else{
                                Message message = new Message();
                                message.what = 99;
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("products", (Serializable) products);
                                message.setData(bundle);
                                System.out.println("SIZE: "+ products.size());
                                LoadProductHandler.getLoadPoductHandler().getHandler().sendMessage(message);
                            }
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
                params.put("ID_Cate", LoadProductHelp.getLoadProductHelp().getMa_danh_muc_hien_tai());
                params.put("start", String.valueOf(LoadProductConfiguration.NUM_PRODUCT*LoadProductHelp.getLoadProductHelp().getNum()));
                params.put("end", String.valueOf(LoadProductConfiguration.NUM_PRODUCT*LoadProductHelp.getLoadProductHelp().getNum())+LoadProductConfiguration.NUM_PRODUCT);
                System.out.println("Từ:" + LoadProductConfiguration.NUM_PRODUCT*LoadProductHelp.getLoadProductHelp().getNum());
                System.out.println("Đến:" + LoadProductConfiguration.NUM_PRODUCT*LoadProductHelp.getLoadProductHelp().getNum() + LoadProductConfiguration.NUM_PRODUCT);

                return params;
            }
        };
        VolleyPool.getInstance(context).addRequest(request);
    }

}
