package com.vientamthuong.eatsimple.introductActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.data.SourceSound;
import com.vientamthuong.eatsimple.loadData.ConfigurationSound;

public class IntroductFragmentObject extends Fragment {

    private int resoucre;

    public IntroductFragmentObject(int res) {
        this.resoucre = res;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(resoucre, container, false);
        LottieAnimationView lottie = view.findViewById(R.id.lottieFresh);
        lottie.setRepeatCount(Integer.MAX_VALUE);
        return view;
    }
}
