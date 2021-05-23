package com.vientamthuong.eatsimple.wishlist;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WishlistDAO {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("yeu_thich");
    public boolean deleteWishlist(String idCustomer, String idDish){
        if (database.child(idCustomer) == null){
            Log.d("WWW","Không tồn tại khách hàng!");
            return false;
        }
        else{
            if(database.child(idCustomer).child(idDish) != null) {
                database.child(idCustomer).child(idDish).removeValue();
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
}
