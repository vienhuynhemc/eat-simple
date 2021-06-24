package com.vientamthuong.eatsimple.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.airbnb.lottie.L;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTaps;
    private String repack;


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

                if (repack != null){
                Bundle bundle = new Bundle();
                bundle.putString("Call",repack);
                loginTabFragment.setArguments(bundle);
                }
                Log.d("AAA","a");
                return loginTabFragment;

            case 1:
                SingupTabFragment singupTabFragment = new SingupTabFragment();
                Log.d("AAA","b");
                return singupTabFragment;
            default:
                return null;
        }
    }

    public String getRepack() {
        return repack;
    }

    public void setRepack(String repack) {
        this.repack = repack;
    }
}
