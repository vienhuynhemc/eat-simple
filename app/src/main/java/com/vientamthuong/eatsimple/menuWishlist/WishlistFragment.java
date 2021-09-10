package com.vientamthuong.eatsimple.menuWishlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.connection.CheckConnection;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.diaLog.DiaLogLostConnection;
import com.vientamthuong.eatsimple.homePage.CustomDanhMucAdapter;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.model.DanhMuc;
import com.vientamthuong.eatsimple.wishlist.Wishlist;
import com.vientamthuong.eatsimple.wishlist.WishlistActivity;
import com.vientamthuong.eatsimple.wishlist.WishlistAdapter;
import com.vientamthuong.eatsimple.wishlist.WishlistDAO;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class WishlistFragment extends Fragment {

    // wishlist
    private ArrayList<Wishlist> products;
    // adapter
    private WishlistAdapter wishlistAdapter;
    // list checked item
    private Set<String> itemsChecked = new HashSet<>();
    private LinearLayout btnCartWishlist;
    private TextView notify;
    private CardView btnAddCart;
    private int cartNumber = 0;


    //Button add to more cart
    TextView btnAddMoreCart, btnDeleteMore;

    // ma khach hang
    private String idCustomer;


    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_wishlist, container, false);

        getView(view);

        addCartItem();;

        btnCartWishlist.setVisibility(View.VISIBLE);
        notify.setVisibility(View.GONE);

        // get account
        idCustomer = DataLocalManager.getAccount().getId();


        // list view wishlist
        RecyclerView recyclerView = view.findViewById(R.id.activity_wishlist_recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        products = new ArrayList<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("yeu_thich").child(idCustomer);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                clearAll();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Wishlist w = ds.getValue(Wishlist.class);
                    products.add(w);
                }
                if (products.size()==0) {
                    btnCartWishlist.setVisibility(View.GONE);
                    notify.setVisibility(View.VISIBLE);
                }

                wishlistAdapter = new WishlistAdapter(getContext(),products);
                recyclerView.setAdapter(wishlistAdapter);

                itemsChecked = wishlistAdapter.getCheckboxes();
                wishlistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        handlerAddCart(idCustomer);
        handlerRemove(idCustomer);
        return view;
    }

    // xóa khỏi wishlist
    public void handlerRemove(String idCustomer){
        btnDeleteMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Wishlist> chooseInCheckbox = new ArrayList<>();
                for (Wishlist w : products) {
                    for (String str : wishlistAdapter.getCheckboxes()) {
                        if (str.equals(w.getId()+"_"+w.getSize())) {
                            chooseInCheckbox.add(w);
                        }
                    }
                }
                DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("yeu_thich").child(idCustomer);
                if(database.getKey()==null){
                    Toast.makeText(getActivity(), "Không tồn tại khách hàng!", Toast.LENGTH_SHORT).show();
                }else {
                    if (chooseInCheckbox.size() < 1) {
                        Toast.makeText(getContext(), "Vui lòng chọn món ăn!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            int count = 0;
                            WishlistDAO wishlistDAO = new WishlistDAO();
                            for (Wishlist w : chooseInCheckbox) {
                                Toast.makeText(getContext(), w.getId()+"_"+w.getSize(), Toast.LENGTH_SHORT).show();
                                if (wishlistDAO.deleteWishlist(idCustomer, w.getId(),w.getSize())) {
                                    deleleteWishlist(idCustomer,w.getId(),w.getSize());
                                    count++;
                                }
                            }
                            wishlistAdapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(), "Đã xóa " + count + " món ăn!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            };
        });
    }


    // thêm vào giỏ hàng
    public void handlerAddCart(String idCustomer){

        btnAddMoreCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Wishlist> chooseInCheckbox = new ArrayList<>();
                for(Wishlist w : products) {
                    for (String str : wishlistAdapter.getCheckboxes()) {
                        if (str.equals(w.getId()+"_"+w.getSize())) {
                            chooseInCheckbox.add(w);
                            Log.d("WWW",chooseInCheckbox.size()+"");
                        }
                    }
                }

                if(chooseInCheckbox.size() <1){
                    Log.d("AAA", "NO");
                    Toast.makeText(getContext(), "Vui lòng chọn món ăn!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("AAA","OK");
                    String s ="";
                    for (String str : wishlistAdapter.getCheckboxes()){
                        s += str +", ";
                    }
                    int count = 0;
                    for (Wishlist w: chooseInCheckbox){
                        addCart(idCustomer,w.getId(),w.getSize());
                        count++;
                    }
                    String rs = s.substring(0,s.lastIndexOf(","));

                    if(cartNumber > 0){
                        Toast.makeText(getContext(), "Đã thêm "+ cartNumber+" món ăn vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getContext(), "Không thể thêm món ăn vào giỏ hàng có thể do hết món ăn!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // xoa view
    public void clearAll(){
        if (products != null){
            products.clear();
        }
        products = new ArrayList<>();
        if (wishlistAdapter != null){
            wishlistAdapter.notifyDataSetChanged();
        }

    }

    public void addCartItem(){

    }
    private void getView(View view) {

        // button add more cart
        btnAddMoreCart = view.findViewById(R.id.activity_wishlist_addMoreCart);
        btnDeleteMore = view.findViewById(R.id.activity_wishlist_deleleMore);
        notify = view.findViewById(R.id.activity_wishlist_notify);
        btnCartWishlist = view.findViewById(R.id.dialog_checkbox_item);
        btnAddCart = view.findViewById(R.id.btnAddCart);

    }

    // xoa khỏi wishlist
    private void deleleteWishlist(String idCustomer,String idProduct,String idSize){
        String url = "http://eat-simple-app.000webhostapp.com/deleteWishlist.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){
                            Toast.makeText(getContext(), "Đã xóa khỏi danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Đã xảy ra lỗi! Không thể xóa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Loi xóa wishlist", Toast.LENGTH_SHORT).show();
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
        VolleyPool.getInstance(getContext()).addRequest(stringRequest);
    }
    private void addCart(String idCustomer,String idDish,String idSize){
        String url = "https://eat-simple-app.000webhostapp.com/addCart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("THEM_THANH_CONG")){
                            cartNumber++;
//                            Toast.makeText(getContext(), "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                        }
                        else{
//                            Toast.makeText(getContext(), "Không thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("AAA",error.toString());
                    }
                }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("ma_kh",idCustomer);
                params.put("ma_sp",idDish);
                params.put("ma_size",idSize);
                params.put("so_luong",1+"");
                return params;
            }
        };
        VolleyPool.getInstance(getContext()).addRequest(stringRequest);
    }
}