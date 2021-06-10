package com.vientamthuong.eatsimple.homePage;

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
import com.vientamthuong.eatsimple.connection.CheckConnection;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.diaLog.DiaLogLostConnection;
import com.vientamthuong.eatsimple.footer.FooterPublicFragment;
import com.vientamthuong.eatsimple.header.HeaderPublicFragment;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.protocol.ActivityProtocol;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements ActivityProtocol {

    // Layout để xử lý refresh
    private SwipeRefreshLayout swipeRefreshLayout;
    // Thời gian thoát activity
    private long lastTimePressBack;
    // Dialog
    private DiaLogLostConnection diaLogLostConnection;
    private DiaLogLoader diaLogLoader;
    // Header
    private HeaderPublicFragment headerPublicFragment;
    // Footer
    private FooterPublicFragment footerPublicFragment;
    // Fragment danh mục
    private HomePageDanhMucFragment homePageDanhMucFragment;
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
        // Hành động
        action();
    }

    private void action(){
        // refresh
        swipeRefreshLayout.setOnRefreshListener(() -> {
           recreate();
           swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void getView() {
        swipeRefreshLayout = findViewById(R.id.activity_home_page_layout);
    }

    private void init() {
        // Tạo dialog
        initDialog();
        // Tạo header
        initHeader();
        // Tạo footer
        initFooter();
        // Tạo fragment danh mục
        initFragmentDanhMuc();
        // Check connection
        if (!CheckConnection.getInstance().isConnected(HomePageActivity.this)) {
            diaLogLostConnection.show();
        } else {
            getData();
        }
    }

    private void initFooter() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        footerPublicFragment = new FooterPublicFragment();
        // Cho nó biết đang ở trang chủ
        Bundle bundle = new Bundle();
        bundle.putInt("data", HomePageConfiguration.HOME);
        footerPublicFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.activity_home_page_footer, footerPublicFragment, "footer");
        fragmentTransaction.commit();
    }

    private void initHeader() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        headerPublicFragment = new HeaderPublicFragment();
        fragmentTransaction.replace(R.id.activity_home_page_header, headerPublicFragment, "header");
        fragmentTransaction.commit();
    }

    private void initFragmentDanhMuc() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        homePageDanhMucFragment = new HomePageDanhMucFragment();
        fragmentTransaction.replace(R.id.activity_home_page_list_danh_muc, homePageDanhMucFragment, "home-page-danh-muc-fragment");
        fragmentTransaction.commit();
    }

    private void getData() {
        // Không mất kết nối thì lấy dữ liêu  fire base về của activity này
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference root = firebaseDatabase.getReference();
        imagesNeedLoad = new ArrayList<>();
        // Load dữ liệu header
        headerPublicFragment.getData(root, diaLogLoader, imagesNeedLoad, HomePageActivity.this);
        // Load danh mục fragment
        homePageDanhMucFragment.getData(root,diaLogLoader,imagesNeedLoad,HomePageActivity.this);
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
    public void loadImageFromIntenet() {
        // Tải hình về
        if (imagesNeedLoad.size() > 0) {
            Thread thread = new Thread(() -> {
                boolean isError = false;
                do {
                    int count = 0;
                    while (count < imagesNeedLoad.size()) {
                        LoadImageForView loadImageForView = imagesNeedLoad.get(count);
                        if (loadImageForView!= null &&!loadImageForView.isStart()) {
                            loadImageForView.setStart(true);
                            loadImageForView.run();
                            count++;
                        } else if(loadImageForView!= null)  {
                            if (loadImageForView.isComplete()) {
                                // Kiểm tra nếu thằng xong này type là danh mục thì thôgn báo cho adpater danh mục
                                if (imagesNeedLoad.get(count).getType() == LoadDataConfiguration.IMAGE_DANH_MUC) {
                                    runOnUiThread(() -> homePageDanhMucFragment.update());
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
