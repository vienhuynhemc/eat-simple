package com.vientamthuong.eatsimple.wishlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.vientamthuong.eatsimple.connection.CheckConnection;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.model.DanhMuc;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.diaLog.DiaLogLostConnection;
import com.vientamthuong.eatsimple.homePage.CustomDanhMucAdapter;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class WishlistActivity extends AppCompatActivity {

    // Thời gian thoát activity
    private long lastTimePressBack;
    // Dialog
    private DiaLogLostConnection diaLogLostConnection;
    private DiaLogLoader diaLogLoader;
    // Button Menu
    private AppCompatButton appCompatButtonMenu;
    private LottieAnimationView lottieAnimationMenu;
    // Button avatar
    private AppCompatButton appCompatButtonAvatar;
    private LottieAnimationView lottieAnimationViewAvatar;
    // List danh muc
    private List<DanhMuc> danhMucs;
    private RecyclerView recyclerViewDanhMuc;
    private CustomDanhMucAdapter customDanhMucAdapter;
    // List image cần tải hình
    private List<LoadImageForView> imagesNeedLoad;
    // Biến boolean để kiểm tra luồng volley có đang chạy hay chưa
    private boolean isRunningVolley;

    private LinearLayout hidenDialog;
    // wishlist
    private ArrayList<Wishlist> products;
    // adapter
    private WishlistAdapter wishlistAdapter;
    // list checked item
    private Set<String> itemsChecked = new HashSet<>();
    private LinearLayout btnCartWishlist;
    private TextView notify;


    //Button add to more cart
    TextView btnAddMoreCart, btnDeleteMore;

    // ma khach hang
    private String idCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        getView();

        init();



        // get account
        Intent intent = getIntent();
        idCustomer = intent.getStringExtra("ma_kh");
//            for (int i = 4; i < 7;i++){
//                DatabaseReference databaseA = FirebaseDatabase.getInstance().getReference("yeu_thich").child(idCustomer).child("sp00"+i+"_size_"+i);
//                databaseA.child("id").setValue("sp00"+i);
//                databaseA.child("size").setValue("size_"+i);
//                databaseA.child("name").setValue("Gà Rán "+i);
//                databaseA.child("nameSize").setValue("nhỏ");
//                databaseA.child("priceS").setValue(0);
//                databaseA.child("priceP").setValue(39000);
//                databaseA.child("idCustomer").setValue(idCustomer);
//                databaseA.child("img").setValue("https://firebasestorage.googleapis.com/v0/b/eat-simple.appspot.com/o/san_pham%2Fsp_1%2Fsp_1.jpg?alt=media&token=905b8cd1-db13-4baa-abe5-f37cc7e5f8c7");
//            }

            // list view wishlist
            RecyclerView recyclerView = findViewById(R.id.activity_wishlist_recylerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
                    wishlistAdapter = new WishlistAdapter(WishlistActivity.this,products);
                    recyclerView.setAdapter(wishlistAdapter);

                    itemsChecked = wishlistAdapter.getCheckboxes();
                    wishlistAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
            if (products.size()==0){
                btnCartWishlist.setVisibility(View.GONE);
                notify.setVisibility(View.VISIBLE);
            }



            handlerAddCart(idCustomer);
            handlerRemove(idCustomer);
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
                 Toast.makeText(WishlistActivity.this, "Không tồn tại khách hàng!", Toast.LENGTH_SHORT).show();
             }else {
                 if (chooseInCheckbox.size() < 1) {
                     Toast.makeText(WishlistActivity.this, "Vui lòng chọn món ăn!", Toast.LENGTH_SHORT).show();
                 } else {
                     try {
                         int count = 0;
                         WishlistDAO wishlistDAO = new WishlistDAO();
                         for (Wishlist w : chooseInCheckbox) {
                             Toast.makeText(WishlistActivity.this, w.getId()+"_"+w.getSize(), Toast.LENGTH_SHORT).show();
                             if (wishlistDAO.deleteWishlist(idCustomer, w.getId(),w.getSize())) {
                                 count++;
                             }
                         }
                         wishlistAdapter.notifyDataSetChanged();
                         Toast.makeText(WishlistActivity.this, "Đã xóa " + count + " món ăn!", Toast.LENGTH_SHORT).show();
                     } catch (Exception e) {
                         Toast.makeText(WishlistActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(WishlistActivity.this, "Vui lòng chọn món ăn!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("AAA","OK");
                    String s ="";
                        for (String str : wishlistAdapter.getCheckboxes()){
                            s += str +", ";
                    }
                        String rs = s.substring(0,s.lastIndexOf(","));
                    Toast.makeText(WishlistActivity.this, "Đã thêm "+ rs+" vào giỏ hàng!", Toast.LENGTH_SHORT).show();
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

    private void init() {
        // Tạo dialog
        initDialog();
        // Tạo recyclerview danh mục
     //   initRecyclerViewDanhMuc();
        // Check connection
        if (!CheckConnection.getInstance().isConnected(WishlistActivity.this)) {
            diaLogLostConnection.show();
        } else {
            getData();
        }
    }
    private void getView() {
        // Button menu
        appCompatButtonMenu = findViewById(R.id.activity_home_page_button_menu_button);
        lottieAnimationMenu = findViewById(R.id.activity_home_page_button_menu_animation);
        // Button avatar
        appCompatButtonAvatar = findViewById(R.id.activity_home_page_avatar_button);
        lottieAnimationViewAvatar = findViewById(R.id.activity_home_page_avatar_animation);

        // button add more cart
        btnAddMoreCart = findViewById(R.id.activity_wishlist_addMoreCart);
        btnDeleteMore = findViewById(R.id.activity_wishlist_deleleMore);
        notify = findViewById(R.id.activity_wishlist_notify);
        btnCartWishlist = findViewById(R.id.dialog_checkbox_item);

    }
    private void getData() {
        // Không mất kết nối thì lấy dữ liêu  fire base về của activity này
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference root = firebaseDatabase.getReference();
        // Load hình cho activity
        DatabaseReference databaseHomePage = root.child("activity_home_page");
        imagesNeedLoad = new ArrayList<>();
        databaseHomePage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Hiện màn hình chờ
                diaLogLoader.show();
                imagesNeedLoad.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (Objects.requireNonNull(dataSnapshot.getKey())) {
                        case "activity_home_page_button_menu":
                            LoadImageForView loadImageForView = new LoadImageForView(appCompatButtonMenu,
                                    WishlistActivity.this,
                                    lottieAnimationMenu,
                                    LoadDataConfiguration.VIEW_NORMAL,
                                    Objects.requireNonNull(dataSnapshot.getValue()).toString());
                            imagesNeedLoad.add(loadImageForView);
                            break;
                        case "activity_home_page_avatar":
                            loadImageForView = new LoadImageForView(appCompatButtonAvatar,
                                    WishlistActivity.this,
                                    lottieAnimationViewAvatar,
                                    LoadDataConfiguration.VIEW_NORMAL,
                                    Objects.requireNonNull(dataSnapshot.getValue()).toString());
                            imagesNeedLoad.add(loadImageForView);
                            break;
                    }
                }
                // Tải dữ liệu từ firebase về thành công
                // Và giờ tải hình từ các link hình
                if (!isRunningVolley) {
                    isRunningVolley = true;
                    loadImageFromIntenet();
                }
                // Tắt màn hình chờ
                diaLogLoader.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WishlistActivity.this, "Lỗi tải dữ liệu từ firebase !", Toast.LENGTH_SHORT).show();
            }
        });
        // Load danh mục cho activit

    }

    public void loadImageFromIntenet() {
        // Tải hình về
        if (imagesNeedLoad.size() > 0) {
            Thread thread = new Thread(() -> {
                boolean isError = false;
                do {
                    int count = 0;
                    while (count < imagesNeedLoad.size()) {
                        LoadImageForView loadImageForView = imagesNeedLoad.get(count);
                        if (!loadImageForView.isStart()) {
                            loadImageForView.setStart(true);
                            loadImageForView.run();
                            count++;
                        } else {
                            if (loadImageForView.isComplete()) {
                                // Kiểm tra nếu thằng xong này type là danh mục thì thôgn báo cho adpater danh mục
                                if (imagesNeedLoad.get(count).getType() == LoadDataConfiguration.IMAGE_DANH_MUC) {
                                    runOnUiThread(() -> customDanhMucAdapter.notifyDataSetChanged());
                                }
                                imagesNeedLoad.remove(count);
                            } else if (loadImageForView.isError()) {
                                isError = true;
                                break;
                            } else {
                                count++;
                            }
                        }
                    }
                } while (!isError && imagesNeedLoad.size() != 0);
                // Cho biến là hết chạy volley
                isRunningVolley = false;
                // Lỗi mạng
                if (isError) {
                    runOnUiThread(() -> {
                        diaLogLostConnection.show();
                        diaLogLostConnection.getBtTry().setOnClickListener(v -> {
                            if (CheckConnection.getInstance().isConnected(WishlistActivity.this)) {
                                diaLogLostConnection.dismiss();
                                // Cho các đối tượng hiện tại trong list về trạng thái ban đầu
                                for (LoadImageForView loadImageForView : imagesNeedLoad) {
                                    if (loadImageForView.isStart()) {
                                        loadImageForView.setStart(false);
                                    }
                                    if (loadImageForView.isError()) {
                                        loadImageForView.setError(false);
                                    }
                                }
                                if (!isRunningVolley) {
                                    isRunningVolley = true;
                                    loadImageFromIntenet();
                                }
                            } else {
                                Toast.makeText(WishlistActivity.this, "Không tìm thấy kết nối!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                }
            });
            thread.start();
        }
    }

    private void initDialog() {
        // lost connection
        diaLogLostConnection = new DiaLogLostConnection(WishlistActivity.this);
        diaLogLostConnection.getBtIgnore().setOnClickListener(v -> {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        });
        diaLogLostConnection.getBtTry().setOnClickListener(v -> {
            if (CheckConnection.getInstance().isConnected(WishlistActivity.this)) {
                diaLogLostConnection.dismiss();
                getData();
            } else {
                Toast.makeText(WishlistActivity.this, "Không tìm thấy kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
        // loader
        diaLogLoader = new DiaLogLoader(WishlistActivity.this);
    }
    // load ds wishlist
    String url = "";
    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length();i++){
                            JSONObject object = array.getJSONObject(i);
                            Wishlist wishlist = new Wishlist();
                            wishlist.setId(object.getString(""));
                            wishlist.setName(object.getString(""));
                            wishlist.setImg(object.getString(""));
                            wishlist.setPriceP(Integer.parseInt(""));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(WishlistActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String,String> params = new HashMap<>();
            params.put("ma_khach_hang",idCustomer);
            return params;
        }
    };
    // insert wishlist
    private void insertToWishlist(String idCustomer,String idProduct,String idSize){
        String url = "";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){
                            Toast.makeText(WishlistActivity.this, "Đã thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(WishlistActivity.this, "Không thể thêm vào danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WishlistActivity.this, "Loi tai danh sach wishlist", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("mai_tai_khoan", idCustomer);
                params.put("ma_san_pham", idProduct);
                params.put("size", idSize);
                return params;
            }
        };
        VolleyPool.getInstance(this).addRequest(stringRequest);
    }
    // xoa khỏi wishlist
    private void deleleteWishlist(String idCustomer,String idProduct,String idSize){
        String url = "";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){
                            Toast.makeText(WishlistActivity.this, "Đã xóa khỏi danh sách yêu thích!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(WishlistActivity.this, "Đã xảy ra lỗi! Không thể xóa!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WishlistActivity.this, "Loi xóa wishlist", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("mai_tai_khoan", idCustomer);
                params.put("ma_san_pham", idProduct);
                params.put("size", idSize);
                return params;
            }
        };
        VolleyPool.getInstance(this).addRequest(stringRequest);
    }



}