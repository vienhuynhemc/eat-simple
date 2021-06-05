package com.vientamthuong.eatsimple.login;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.vientamthuong.eatsimple.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingupTabFragment extends Fragment {
    // test commit
    EditText email, pass, repass;
    TextView notify;
    Button btnSignUp;
    String sRePassword = "", sEmail = "", sPassword = "";
    boolean checkPassword, checkEmail, checkLengthPass, checkSignUp;
    float v=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        ViewGroup root =(ViewGroup) inflater.inflate(R.layout.activity_singup_tab_fragment, container, false);

        mapping(root);

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String pss = pass.getText().toString().trim();
                    if (pss.length()<8){
                        notify.setText("*Mật khẩu phải tối thiểu 8 kí tự!");
                        checkLengthPass = false;
                        notify.setTextColor(Color.RED);
                    }
                    else{
                        notify.setText("");
                        checkLengthPass = true;
                    }
                    if (pss.equals("")){
                        notify.setText("");
                        checkLengthPass = false;
                    }
                    if(!repass.getText().toString().equals("")) {
                        if (!pss.equals(repass.getText().toString())) {
                            notify.setText("*Mật khẩu không khớp");
                            notify.setTextColor(Color.RED);
                            checkPassword = false;
                        }
                        else{
                            checkPassword = true;
                        }
                    }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!emailValidator(email.getText().toString())){
                        notify.setText("*Vui lòng nhập đúng email!");
                        notify.setTextColor(Color.RED);
                        checkEmail = false;
                    }
                    else{
                        notify.setText("");
                        checkEmail = true;
                    }
                    if (email.getText().toString().trim().equals("")){
                        notify.setText("");
                        checkEmail = false;
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        repass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sRePassword = repass.getText().toString().trim();
                sPassword = pass.getText().toString().trim();

                if (pass.getText().toString().equals("") && !repass.getText().toString().equals("")){
                    notify.setText("*Vui lòng nhập mật khẩu trước!");
                    checkPassword = false;
                }
                else{
                    if (!sPassword.equals(sRePassword)){
                        notify.setText("*Mật khẩu không khớp");
                        notify.setTextColor(Color.RED);
                        checkPassword = false;
                    }
                    else{
                        notify.setText("");
                        checkPassword = true;
                    }
                    if(sRePassword.equals("")){
                        notify.setText("");
                        checkPassword = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                    sRePassword = repass.getText().toString();
            }
        });

            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sEmail = email.getText().toString().trim();
                    sPassword = pass.getText().toString().trim();
                    sRePassword = repass.getText().toString().trim();

                    if (sEmail.equals("") || sPassword.equals("") || sRePassword.equals("")) {
                        notify.setText("*Vui lòng nhập đủ thông tin!");
                        checkSignUp = false;
                    } else  if(checkEmail==true && checkPassword==true && checkLengthPass==true) {
                        notify.setText("Đăng ký thành công!");
                        checkSignUp = true;
                    }

                    if (checkSignUp){
                        notify.setTextColor(Color.GREEN);
                    }

                }

            });



        return root;
    }
    public void mapping(View root){
        email = root.findViewById(R.id.email_signUp);
        pass = root.findViewById(R.id.password_signUp);
        repass = root.findViewById(R.id.repassword_signUp);
        btnSignUp = root.findViewById(R.id.button_singup);
        notify = root.findViewById(R.id.notify_signUp);

        email.setTranslationX(0);
        pass.setTranslationX(0);
        repass.setTranslationX(0);
        btnSignUp.setTranslationX(0);

        email.setAlpha(v);
        pass.setAlpha(v);
        repass.setAlpha(v);
        btnSignUp.setAlpha(v);

        email.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(500).start();
        repass.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(500).start();
        btnSignUp.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(700).start();
    }
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
