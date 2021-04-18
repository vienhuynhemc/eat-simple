package com.vientamthuong.eatsimple.introductActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.data.SourceSound;
import com.vientamthuong.eatsimple.homePage.HomePageActivity;

public class IntroductoryActivity extends AppCompatActivity {
    private ImageView logo, appName, splashImg;
    private LottieAnimationView lottie;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xóa status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_introductory_main);
        // Ánh xạ view
        getView();
        // run animation
        runAnimation();
        // load nhạc
        SourceSound.getInstance().loadSound(IntroductoryActivity.this);
        // Khởi tạo các thuộc tính khác
        init();
    }

    private void init() {
        // Lấy dữ liệu chạy lần đầu
        sharedPreferences = getSharedPreferences(IntroductConfiguration.START_FIRST, MODE_PRIVATE);
        boolean isRuned = sharedPreferences.getBoolean("run", false);
        if (!isRuned) {
            // View pager
            ViewPager viewPager = findViewById(R.id.pager);
            ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), IntroductoryActivity.this);
            viewPager.setAdapter(adapter);
        } else {
            // Đã chạy rồi thì vô thẳng home page
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent intent = new Intent();
                intent.setClass(IntroductoryActivity.this, HomePageActivity.class);
                finish();
                startActivity(intent);
            }, 4500);
        }
    }

    private void runAnimation() {
        runOnUiThread(() -> {
            splashImg.animate().translationY(-3000).setDuration(1500).setStartDelay(4500);
            logo.animate().translationY(3000).setDuration(1500).setStartDelay(4500);
            appName.animate().translationY(3000).setDuration(1500).setStartDelay(4500);
            lottie.animate().translationY(3000).setDuration(1000).setStartDelay(4500);
        });
    }

    private void getView() {
        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.appName);
        splashImg = findViewById(R.id.img);
        lottie = findViewById(R.id.lottie);
    }

    // GETTER AND SETTER
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}