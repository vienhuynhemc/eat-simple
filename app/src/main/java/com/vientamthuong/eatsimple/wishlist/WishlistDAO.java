package com.vientamthuong.eatsimple.wishlist;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vientamthuong.eatsimple.detail.Activity_detail;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WishlistDAO {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("yeu_thich");
    Context context;

    public WishlistDAO(Context context) {
        this.context = context;
    }

    public WishlistDAO() {
    }
    public void deleleteWishlist1(String idCustomer,String idProduct,String idSize){
        String url = "http://eat-simple-app.000webhostapp.com/deleteWishlist.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){
                            Toast.makeText(context, "Đã xóa khỏi danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "Đã xảy ra lỗi! Không thể xóa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Loi xóa wishlist", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("ma_khach_hang", idCustomer);
                params.put("ma_san_pham", idProduct);
                params.put("ma_size", idSize);
                return params;
            }
        };
        VolleyPool.getInstance(context).addRequest(stringRequest);
    }



    public boolean deleteWishlist(String idCustomer, String idDish,String size){
        if (database.child(idCustomer) == null){
            Log.d("WWW","Không tồn tại khách hàng!");
            return false;
        }
        else{
            if(database.child(idCustomer).child(idDish+"_"+size) != null) {
                database.child(idCustomer).child(idDish+"_"+size).removeValue();
                return true;
            }
            else{
                    return false;
                }
        }
    }
    // xoá nhiều món ăn khỏi danh sách wishlist
    public boolean deleletMoreWishlist(String idCustomer, ArrayList<String> idDishes){
        return false;
    }
    // insert wishlist
    public void insertToWishlist(String idCustomer,String idProduct,String idSize) {
        String url = "https://eat-simple-app.000webhostapp.com/addWishlist.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(context, "Đã thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Không thể thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Loi tai danh sach wishlist", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("ma_khach_hang", idCustomer);
                params.put("ma_san_pham", idProduct);
                params.put("ma_size", idSize);
                return params;
            }
        };
        VolleyPool.getInstance(context).addRequest(stringRequest);
    }

}
