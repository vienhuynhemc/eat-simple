package com.vientamthuong.eatsimple.loadProductByID;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vientamthuong.eatsimple.admin.HomePageActivity;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.cartPage.LoadCartHelper;
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

public class GetListProduct {


    public static void getData(Context context){

        String urlLoad = "https://eat-simple-app.000webhostapp.com/loadProductByID.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            List<Product> products = new ArrayList<>();

                            for (int i = 0;i<jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);

                                Product product = new Product();

                                product.setMa_sp(object.getString("ma_sp"));
                                product.setTen_sp(object.getString("ten_sp"));
                                product.setHinh(object.getString("hinh"));
                                product.setUrl(object.getString("url"));
                                product.setKcal(Integer.parseInt(object.getString("kcal")));
                                product.setThoi_gian_nau(Integer.parseInt(object.getString("time")));
                                product.setTon_tai(Integer.parseInt(object.getString("ton_tai")));
                                product.setSo_luong_ban_ra(Integer.parseInt(object.getString("so_luong_ban_ra")));
                                product.setTrang_thai(Integer.parseInt(object.getString("trang_thai")));
                                product.setNgay_tao(new DateTime(object.getString("ngay_tao")));
                                product.setMa_dm(object.getString("ma_danh_muc"));
                                product.setSo_luong_con_lai(Integer.parseInt(object.getString("so_luong_con_lai")));
                                product.setGia(Integer.parseInt(object.getString("gia")));
                                product.setGia_km(Integer.parseInt(object.getString("gia_km")));
                                product.setThong_tin(object.getString("thong_tin"));
                                System.out.println("Thong_thin " + product.getThong_tin());
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

    public static void getBitmapImage(Product product, Context context,LoadProductViewAdapter adapter){
        ImageRequest imageRequest = new ImageRequest(product.getUrl(), response -> {
            product.setLoadImg(true);
            product.setBitmap(response);
            adapter.notifyDataSetChanged();
        },0,0, ImageView.ScaleType.FIT_CENTER, null, error -> {
            Toast.makeText(context, "Lỗi tải hình sản phẩm!", Toast.LENGTH_SHORT).show();
        });
        Volley.newRequestQueue(context).add(imageRequest);

    }

}
