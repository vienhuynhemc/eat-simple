package com.vientamthuong.eatsimple.homePage;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;

public class HomePageActivity extends AppCompatActivity {

    // Thời gian thoát activity
    private long lastTimePressBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xóa status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page_main);
        CardView cardView = findViewById(R.id.activity_home_page_serach);
        cardView.setOnClickListener(v -> {
            DiaLogLoader diaLogLoader = new DiaLogLoader(HomePageActivity.this);
            diaLogLoader.show();
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

}
