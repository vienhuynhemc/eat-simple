package com.vientamthuong.eatsimple.introductActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.homePage.HomePageActivity;

public class IntroductFragmentObject extends Fragment {

    private final int resoucre;
    private int state;
    private IntroductoryActivity appCompatActivity;

    public IntroductFragmentObject(int res) {
        this.resoucre = res;
    }

    public IntroductFragmentObject(int res, int state, IntroductoryActivity appCompatActivity) {
        this.resoucre = res;
        this.state = state;
        this.appCompatActivity = appCompatActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(resoucre, container, false);
        // Trường hợp layout cuối
        if (state == IntroductConfiguration.END) {
            CardView buttonNext = view.findViewById(R.id.activity_introductory_next);
            buttonNext.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), HomePageActivity.class);
                appCompatActivity.finish();
                // Ghi vô file để lần sau không chạy intro nữa
                SharedPreferences.Editor editor = appCompatActivity.getSharedPreferences().edit();
                editor.putBoolean("run", true);
                editor.apply();
                startActivity(intent);
            });
        }
        return view;
    }
}
