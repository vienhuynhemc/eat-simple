package com.vientamthuong.eatsimple.admin;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.header.HeaderFragment;

public class HomePageActivity extends AppCompatActivity {

    // Swipe layout
    private SwipeRefreshLayout swipeRefreshLayout;
    // Header
    private HeaderFragment headerFragment;

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
    }

    private void getView() {
        swipeRefreshLayout = findViewById(R.id.layout);
    }

    private void init() {
        // Tạo header
        initHeader();
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

    private void action() {
        // Swipe
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recreate();
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}
