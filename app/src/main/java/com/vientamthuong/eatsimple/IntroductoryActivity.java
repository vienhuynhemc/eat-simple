package com.vientamthuong.eatsimple;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;

public class IntroductoryActivity extends AppCompatActivity {
    private ImageView logo,appName,splashImg;
    LottieAnimationView lottie;

    private static final int NUM_PAGE = 3;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.appName);
        splashImg = findViewById(R.id.img);
        lottie = findViewById(R.id.lottie);

        splashImg.animate().translationY(-3000).setDuration(1500).setStartDelay(4500);
        logo.animate().translationY(3000).setDuration(1500).setStartDelay(4500);
        appName.animate().translationY(3000).setDuration(1500).setStartDelay(4500);
        lottie.animate().translationY(3000).setDuration(1000).setStartDelay(4500);


        viewPager = findViewById(R.id.pager);

        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
    }

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    FreshFragment freshFragment = new FreshFragment();
                    return freshFragment;

                case 1:
                    DeliveryFragment deliveryFragment = new DeliveryFragment();
                    return deliveryFragment;

                case 2:
                    PayFragment payFragment = new PayFragment();
                    return payFragment;

            }

            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGE;
        }
    }

}