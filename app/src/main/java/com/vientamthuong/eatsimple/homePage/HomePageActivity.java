package com.vientamthuong.eatsimple.homePage;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePageActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xóa status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page_main);
        // Ánh xạ view
        getView();
        // Thêm dữ liệu
        init();
    }

    private void getView() {
        // Button menu
        appCompatButtonMenu = findViewById(R.id.activity_home_page_button_menu_button);
        lottieAnimationMenu = findViewById(R.id.activity_home_page_button_menu_animation);
        // Button avatar
        appCompatButtonAvatar = findViewById(R.id.activity_home_page_avatar_button);
        lottieAnimationViewAvatar = findViewById(R.id.activity_home_page_avatar_animation);
        // recyclerview danh mục
        recyclerViewDanhMuc = findViewById(R.id.activity_home_page_list_danh_muc);
    }

    private void init() {
        // Tạo dialog
        initDialog();
        // Tạo recyclerview danh mục
        initRecyclerViewDanhMuc();
        // Check connection
        if (!CheckConnection.getInstance().isConnected(HomePageActivity.this)) {
            diaLogLostConnection.show();
        } else {
            getData();
        }
    }

    private void initRecyclerViewDanhMuc() {
        danhMucs = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomePageActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerViewDanhMuc.setLayoutManager(linearLayoutManager);
        recyclerViewDanhMuc.setHasFixedSize(true);
        int[] resources = {R.layout.activity_home_page_custom_danh_muc_first, R.layout.activity_home_page_custom_danh_muc, R.layout.activity_home_page_custom_danh_muc_last};
        customDanhMucAdapter = new CustomDanhMucAdapter(resources, danhMucs);
        recyclerViewDanhMuc.setAdapter(customDanhMucAdapter);
    }

    private void getData() {
        // Hiện màn hình chờ
        diaLogLoader.show();
        // Không mất kết nối thì lấy dữ liêu  fire base về của activity này
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference root = firebaseDatabase.getReference();
        // Load hình cho activity
        DatabaseReference databaseHomePage = root.child("activity_home_page");
        imagesNeedLoad = new ArrayList<>();
        databaseHomePage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imagesNeedLoad.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    switch (Objects.requireNonNull(dataSnapshot.getKey())) {
                        case "activity_home_page_button_menu":
                            LoadImageForView loadImageForView = new LoadImageForView(appCompatButtonMenu,
                                    HomePageActivity.this,
                                    lottieAnimationMenu,
                                    LoadDataConfiguration.VIEW_NORMAL,
                                    Objects.requireNonNull(dataSnapshot.getValue()).toString());
                            imagesNeedLoad.add(loadImageForView);
                            break;
                        case "activity_home_page_avatar":
                            loadImageForView = new LoadImageForView(appCompatButtonAvatar,
                                    HomePageActivity.this,
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePageActivity.this, "Lỗi tải dữ liệu từ firebase !", Toast.LENGTH_SHORT).show();
            }
        });
        // Load danh mục cho activit
        DatabaseReference databaseReferenceDanhMuc = root.child("danh_muc");
        databaseReferenceDanhMuc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhMucs.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DanhMuc danhMuc = new DanhMuc(dataSnapshot.getKey());
                    danhMuc.setHinh(dataSnapshot.child("hinh").getValue().toString());
                    danhMuc.setTen_danh_muc(dataSnapshot.child("ten").getValue().toString());
                    danhMucs.add(danhMuc);
                    customDanhMucAdapter.notifyDataSetChanged();
                }
                // Tải dữ liệu từ firebase về thành công
                // Đưa vô imageNeedLoad
                for (DanhMuc danhMuc : danhMucs) {
                    imagesNeedLoad.add(new LoadImageForView(HomePageActivity.this, danhMuc, LoadDataConfiguration.IMAGE_DANH_MUC));
                }
                // Và giờ tải hình từ các link hình
                if (!isRunningVolley) {
                    isRunningVolley = true;
                    loadImageFromIntenet();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePageActivity.this, "Lỗi tải dữ liệu từ firebase !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadImageFromIntenet() {
        // Tải hình về
        if (imagesNeedLoad.size() > 0) {
            Thread thread = new Thread(() -> {
                runOnUiThread(() -> {
                    if (!diaLogLoader.isShowing()) {
                        diaLogLoader.show();
                    }
                });
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
                diaLogLoader.dismiss();
                // Cho biến là hết chạy volley
                isRunningVolley = false;
                // Lỗi mạng
                if (isError) {
                    runOnUiThread(() -> {
                        diaLogLostConnection.show();
                        diaLogLostConnection.getBtTry().setOnClickListener(v -> {
                            if (CheckConnection.getInstance().isConnected(HomePageActivity.this)) {
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
                                Toast.makeText(HomePageActivity.this, "Không tìm thấy kết nối!", Toast.LENGTH_SHORT).show();
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
        diaLogLostConnection = new DiaLogLostConnection(HomePageActivity.this);
        diaLogLostConnection.getBtIgnore().setOnClickListener(v -> {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        });
        diaLogLostConnection.getBtTry().setOnClickListener(v -> {
            if (CheckConnection.getInstance().isConnected(HomePageActivity.this)) {
                diaLogLostConnection.dismiss();
                getData();
            } else {
                Toast.makeText(HomePageActivity.this, "Không tìm thấy kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
        // loader
        diaLogLoader = new DiaLogLoader(HomePageActivity.this);
    }

    @Override
    public void onBackPressed() {
        if (lastTimePressBack == 0 || System.currentTimeMillis() - lastTimePressBack > 2000) {
            lastTimePressBack = System.currentTimeMillis();
            Toast.makeText(HomePageActivity.this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

}
