package com.vientamthuong.eatsimple.login;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.airbnb.lottie.L;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTaps;


    public LoginAdapter(FragmentManager fm, Context context, int totalTaps){
        super(fm);
        this.context = context;
        this.totalTaps = totalTaps;

    }

    @Override
    public int getCount() {
        return totalTaps;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return loginTabFragment;
            case 1:
                SingupTabFragment singupTabFragment = new SingupTabFragment();
                return singupTabFragment;
            default:
                return null;
        }
    }
}
