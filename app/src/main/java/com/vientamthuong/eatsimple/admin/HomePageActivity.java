package com.vientamthuong.eatsimple.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.header.HeaderFragment;
import com.vientamthuong.eatsimple.admin.header.TopHeaderFragment;
import com.vientamthuong.eatsimple.admin.loadData.LoadData;
import com.vientamthuong.eatsimple.connection.CheckConnection;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.diaLog.DiaLogLostConnection;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.protocol.ActivityProtocol;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements ActivityProtocol {

    // Swipe layout
    private SwipeRefreshLayout swipeRefreshLayout;
    // Header
    private HeaderFragment headerFragment;
    private TopHeaderFragment topHeaderFragment;
    // Thời gian thoát activity
    private long lastTimePressBack;
    // List image cần tải hình
    private List<LoadImageForView> imagesNeedLoad;
    // Biến boolean để kiểm tra luồng volley có đang chạy hay chưa
    private boolean isRunningVolley;
    // Dialog
    private DiaLogLostConnection diaLogLostConnection;
    private DiaLogLoader diaLogLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xóa status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.admin_activity_home_page);
        // Ánh xạ view
        getView();
        // Thêm dữ liệu
        init();
        // Hành động
        action();
        // Check connection
        if (!CheckConnection.getInstance().isConnected(HomePageActivity.this)) {
            diaLogLostConnection.show();
        } else {
            getDataHeader();
        }
    }

    public void getDataHeader() {
        // Các fragment sẵn sàng hết thì mới chạy
        if (LoadData.getInstance().isReadyFromThongBaoNoi()) {
            // Không mất kết nối thì lấy dữ liêu  fire base về của activity này
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference root = firebaseDatabase.getReference();
            imagesNeedLoad = new ArrayList<>();
            // Load dữ liệu top header
            topHeaderFragment.getData(root, diaLogLoader, imagesNeedLoad, HomePageActivity.this);
        }
    }

    @Override
    public boolean isRunningVolley() {
        return isRunningVolley;
    }

    @Override
    public void setRunningVolley(boolean isRunningVolley) {
        this.isRunningVolley = isRunningVolley;
    }

    @Override
    public synchronized void loadImageFromIntenet() {
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

    private void getView() {
        swipeRefreshLayout = findViewById(R.id.layout);
    }

    private void init() {
        // Tạo dialog
        initDialog();
        // Tạo header
        initHeader();
        // Tạo top header
        initTopHeader();
    }

    private void initHeader() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        headerFragment = new HeaderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("data", Configuration.HOME);
        headerFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.header, headerFragment);
        fragmentTransaction.commit();
    }

    private void initTopHeader() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        topHeaderFragment = new TopHeaderFragment();
        fragmentTransaction.replace(R.id.f1, topHeaderFragment);
        fragmentTransaction.commit();
    }

    private void action() {
        // Swipe
        swipeRefreshLayout.setOnRefreshListener(() -> {
            LoadData.getInstance().reset();
            recreate();
            swipeRefreshLayout.setRefreshing(false);
        });
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
                getDataHeader();
            } else {
                Toast.makeText(HomePageActivity.this, "Không tìm thấy kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
        // loader
        diaLogLoader = new DiaLogLoader(HomePageActivity.this);
    }
}
