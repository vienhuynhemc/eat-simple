package com.vientamthuong.eatsimple.detail;

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
import com.vientamthuong.eatsimple.beans.Comment;
import com.vientamthuong.eatsimple.cartPage.LoadCartHandler;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.detailOrder.DetailOrderAdapter;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetComment {

    public static void getData(Context context,String ma_sp){

        String urlLoad = "https://eat-simple-app.000webhostapp.com/loadComment.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            List<Comment> comments = new ArrayList<>();

                            for (int i = 0;i<jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);
                                Comment comment = new Comment();
                                comment.setTen(object.getString("ten"));
                                comment.setTen_sp(object.getString("ten_sp"));
                                comment.setSize(object.getString("ten_size"));
                                comment.setNoidung(object.getString("noi_dung"));
                                comment.setUrl(object.getString("url"));
                                comment.setSosao(Integer.parseInt(object.getString("so_sao")));
                                comment.setTime(new DateTime(object.getString("ngay_tao")));
                                comments.add(comment);
                            }
                            Message message = new Message();
                            message.what = 190;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("comments", (Serializable) comments);
                            message.setData(bundle);
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
                params.put("ma_sp",ma_sp);
                return params;
            }
        };
        VolleyPool.getInstance(context).addRequest(request);
    }

    public static void getBitmapImage(Comment comment, Context context, CommentAdapter adapter){
        ImageRequest imageRequest = new ImageRequest(comment.getUrl(), response -> {
            comment.setIsload(true);
            comment.setBitmap(response);
            adapter.notifyDataSetChanged();
        },0,0, ImageView.ScaleType.FIT_CENTER, null, error -> {
            Toast.makeText(context, "Lỗi tải hình sản phẩm!", Toast.LENGTH_SHORT).show();
        });
        Volley.newRequestQueue(context).add(imageRequest);

    }
}
