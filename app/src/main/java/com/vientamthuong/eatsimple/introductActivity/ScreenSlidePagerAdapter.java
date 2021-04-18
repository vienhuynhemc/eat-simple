package com.vientamthuong.eatsimple.introductActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.vientamthuong.eatsimple.R;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private final IntroductoryActivity appCompatActivity;

    public ScreenSlidePagerAdapter(@NonNull FragmentManager fm, IntroductoryActivity appCompatActivity) {
        super(fm);
        this.appCompatActivity = appCompatActivity;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        IntroductFragmentObject introductFragmentObject;
        switch (position) {
            case 0:
                introductFragmentObject = new IntroductFragmentObject(R.layout.activity_introductory_fresheverything, appCompatActivity);
                break;
            case 1:
                introductFragmentObject = new IntroductFragmentObject(R.layout.activity_introductory_delivery, appCompatActivity);
                break;
            default:
                introductFragmentObject = new IntroductFragmentObject(R.layout.activity_introductory_payment, IntroductConfiguration.END, appCompatActivity);
                break;
        }
        return introductFragmentObject;
    }

    @Override
    public int getCount() {
        return IntroductConfiguration.MAX_PAGE;
    }

}