package com.vientamthuong.eatsimple;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

public class DeliveryFragment extends Fragment {
    private LottieAnimationView lottie;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.delivery,container,false);

        lottie = root.findViewById(R.id.lottieDelivery);
        lottie.setRepeatCount(Integer.MAX_VALUE);

        return root;
    }
}
