package com.vientamthuong.eatsimple.introductActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.data.SourceSound;

public class IntroductoryActivity extends AppCompatActivity {
    private ImageView logo, appName, splashImg;
    LottieAnimationView lottie;

    private static final int NUM_PAGE = 3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter adapter;

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
        // View pager
        viewPager = findViewById(R.id.pager);
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
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

}