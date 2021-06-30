package com.vientamthuong.eatsimple.cartPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.beans.Cart;
import com.vientamthuong.eatsimple.checkout.PayActivity;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHandler;
import com.vientamthuong.eatsimple.login.Activity_login;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    // khai báo
    private RecyclerView recyclerView;
    private List<Cart> list_Item;
    private CartAdapter cartAdapter;
    private LinearLayout lottieAnimationView;
    private TextView total_product,total_price,total_vc;
    private FloatingActionButton back;
    private AppCompatButton dangnhap;
    private Button thanhtoan;

    public CartPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartPageFragment newInstance(String param1, String param2) {
        CartPageFragment fragment = new CartPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        eventback();

        if (DataLocalManager.getAccount() != null) {
            view = inflater.inflate(R.layout.activity_cart, container, false);
            getView(view);
            cartRecycle();
            event();
            eventthanhtoan();
        }else {
            view = inflater.inflate(R.layout.fragment_cart_page, container, false);
            getView2(view);
            eventdangnhap();
        }
        // Inflate the layout for this fragment
        return view;
    }

   void eventdangnhap(){
        dangnhap.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Activity_login.class));
        });
    }
    void getView2(View v){
        dangnhap = v.findViewById(R.id.dialog_lost_connection_try);
    }
    private void eventthanhtoan(){

    thanhtoan.setOnClickListener(v -> {

        List<Cart> carts = new ArrayList<>();

        for (int i =0;i<recyclerView.getChildCount();i++){
            View view = recyclerView.getChildAt(i);
            CheckBox checkBox = view.findViewById(R.id.checkbox_cart);
            if (checkBox.isChecked()){
                carts.add(list_Item.get(i));
            }
        }
        if (carts.size() != 0){
            for (int i =0;i<carts.size();i++){
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                carts.get(i).getBitmap().compress(Bitmap.CompressFormat.PNG,100,baos);
                byte[] bytes = baos.toByteArray();
                carts.get(i).setBytes(bytes);
                carts.get(i).setBitmap(null);
            }

            Intent intent = new Intent(getContext(), PayActivity.class);
            intent.putExtra("carts", (Serializable) carts);

            startActivity(intent);
        }else {
            Toast.makeText(getContext(), "Vui lòng chọn sản phẩm để thanh toán", Toast.LENGTH_SHORT).show();
        }

    });


    }
    private void eventback(){

    }
    private void event(){
        LoadCartHandler handler = LoadCartHandler.getInstance();
        handler.setCarts(list_Item);
        handler.setCartAdapter(cartAdapter);
        handler.getHandler();
        GetCart.getData(getContext());
    }

    void getView(View view){
        recyclerView = view.findViewById(R.id.list_item);
        lottieAnimationView = view.findViewById(R.id.activity_home_page_layout_location);
        list_Item = new ArrayList<>();
        total_price = view.findViewById(R.id.total_price);
        total_product = view.findViewById(R.id.total_product);
        total_vc = view.findViewById(R.id.total_vc);
        back = view.findViewById(R.id.detail_back);
        thanhtoan = view.findViewById(R.id.thanhtoan);

    }
//    void anim(){
//        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.activity_cart_translate);
//        lottieAnimationView.startAnimation(animation);
//    }
    void cartRecycle(){
        cartAdapter = new CartAdapter(list_Item,total_product,total_price);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
    }
}