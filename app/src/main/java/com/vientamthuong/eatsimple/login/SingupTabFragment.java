package com.vientamthuong.eatsimple.login;

import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.vientamthuong.eatsimple.R;

public class SingupTabFragment extends Fragment {
    View email;
    View pass, repass, singup;
    float v=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        ViewGroup root =(ViewGroup) inflater.inflate(R.layout.activity_singup_tab_fragment, container, false);

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        repass = root.findViewById(R.id.repass);
        singup = root.findViewById(R.id.button_singup);

        email.setTranslationX(800);
        pass.setTranslationX(800);
        repass.setTranslationX(800);
        singup.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        repass.setAlpha(v);
        singup.setAlpha(v);

        email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        repass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        singup.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        return root;
    }
}
