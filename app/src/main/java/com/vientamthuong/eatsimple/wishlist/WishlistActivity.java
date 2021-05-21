package com.vientamthuong.eatsimple.wishlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.connection.CheckConnection;
import com.vientamthuong.eatsimple.danhMuc.DanhMuc;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.diaLog.DiaLogLostConnection;
import com.vientamthuong.eatsimple.homePage.CustomDanhMucAdapter;
import com.vientamthuong.eatsimple.homePage.HomePageActivity;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        getView();

        init();




        RecyclerView recyclerView = findViewById(R.id.activity_wishlist_recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        products = new ArrayList<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("yeu_thich").child("001");
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
                for(String s: itemsChecked){
                    Toast.makeText(WishlistActivity.this, s+"", Toast.LENGTH_SHORT).show();
                }

                wishlistAdapter.notifyDataSetChanged();

            }



            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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

        // hiden dialog
        hidenDialog = findViewById(R.id.dialog_checkbox_item);

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

}