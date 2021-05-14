package com.vientamthuong.eatsimple.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.vientamthuong.eatsimple.R;

public class LoginTabFragment extends Fragment {

    View email;
    View pass, forgotPass, login;
    float v=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_login_tab_fragment, container, false);

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        forgotPass = root.findViewById(R.id.forgot_pass);
        login = root.findViewById(R.id.button_login);

        email.setTranslationX(0);
        pass.setTranslationX(0);
        forgotPass.setTranslationX(0);
        login.setTranslationX(0);

        email.setAlpha(v);
        pass.setAlpha(v);
        forgotPass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(500).start();
        forgotPass.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(700).start();



        return root;
    }
}
