package com.vientamthuong.eatsimple.introductActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.vientamthuong.eatsimple.R;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        IntroductFragmentObject introductFragmentObject = null;
        switch (position) {
            case 0:
                introductFragmentObject = new IntroductFragmentObject(R.layout.fresheverything);
                break;
            case 1:
                introductFragmentObject = new IntroductFragmentObject(R.layout.delivery);
                break;
            case 2:
                introductFragmentObject = new IntroductFragmentObject(R.layout.payment);
                break;
        }
        return introductFragmentObject;
    }

    @Override
    public int getCount() {
        return IntroductConfiguration.MAX_PAGE;
    }

}