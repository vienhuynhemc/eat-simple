package com.vientamthuong.eatsimple.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.jbCrypt.BCrypt;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingupTabFragment extends Fragment {
    // test commit
    EditText email, pass, repass, username;
    TextView notify;
    Button btnSignUp;
    String sRePassword = "", sEmail = "", sPassword = "", sUsername = "";
    boolean checkPassword, checkEmail, checkLengthPass, checkSignUp, checkUsername;
    String urlCheckUsername = "https://eat-simple-app.000webhostapp.com/checkUsername.php";
    String urlSignUp = "https://eat-simple-app.000webhostapp.com/signUp.php";
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_singup_tab_fragment, container, false);

        mapping(root);


        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pss = pass.getText().toString().trim();
                if (pss.length() < 8) {
                    notify.setText("*Mật khẩu phải tối thiểu 8 kí tự!");
                    checkLengthPass = false;
                    checkSignUp = false;
                    notify.setTextColor(Color.RED);
                } else {
                    notify.setText("");
                    checkLengthPass = true;
                }
                if (pss.equals("")) {
                    notify.setText("");
                    checkLengthPass = false;
                }
                if (!repass.getText().toString().equals("")) {
                    if (!pss.equals(repass.getText().toString())) {
                        notify.setText("*Mật khẩu không khớp");
                        notify.setTextColor(Color.RED);
                        checkSignUp = false;
                        checkPassword = false;
                    } else {
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
                if (!emailValidator(email.getText().toString())) {
                    notify.setText("*Vui lòng nhập đúng email!");
                    notify.setTextColor(Color.RED);
                    checkSignUp = false;
                    checkEmail = false;
                } else {
                    notify.setText("");
                    checkEmail = true;
                }
                if (email.getText().toString().trim().equals("")) {
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

                if (pass.getText().toString().equals("") && !repass.getText().toString().equals("")) {
                    notify.setText("*Vui lòng nhập mật khẩu trước!");
                    checkSignUp = false;
                    checkPassword = false;
                } else {
                    if (!sPassword.equals(sRePassword)) {
                        checkSignUp = false;
                        notify.setText("*Mật khẩu không khớp");
                        notify.setTextColor(Color.RED);
                        checkPassword = false;
                    } else {
                        notify.setText("");
                        checkPassword = true;
                    }
                    if (sRePassword.equals("")) {
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

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String user = username.getText().toString();
                char[] u = user.toCharArray();

                if (user.equals("")) {
                    notify.setTextColor(Color.RED);
                    checkUsername = false;
                    checkSignUp = false;
//                    notify.setText("*Vui lòng nhập username!");
                } else if ((int) u[0] >= 65 && (int) u[0] <= 90 || (int) u[0] >= 97 && (int) u[0] <= 122) {
                    if (user.length() < 12) {
                        notify.setText("*Username phải tối thiểu 12 kí tự");
                        notify.setTextColor(Color.RED);
                        checkSignUp = false;
                        checkUsername = false;
                    } else {
                        notify.setText("");
                        checkUsername = true;
                    }
                } else {
                    notify.setText("*Username phải bắt đầu bằng một kí tự chữ!");
                    notify.setTextColor(Color.RED);
                    checkSignUp = false;
                    checkUsername = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String user = username.getText().toString();
                if (user.equals("")) {
                    notify.setTextColor(Color.RED);
                    notify.setText("");
                    checkUsername = false;
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sUsername = username.getText().toString().trim();
                sEmail = email.getText().toString().trim();
                sPassword = pass.getText().toString().trim();
                sRePassword = repass.getText().toString().trim();

                if (sEmail.equals("") || sPassword.equals("") || sRePassword.equals("") || sUsername.equals("")) {
                    notify.setText("*Vui lòng nhập đủ thông tin!");
                    checkSignUp = false;
                } else if (checkEmail == true && checkPassword == true && checkLengthPass == true && checkUsername == true) {
                    // check email
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCheckUsername,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                        if (response.trim().equals("true")){
                                            notify.setText("*Tên tài khoản đã tồn tại!");
                                            notify.setTextColor(Color.RED);
                                            checkUsername = false;
                                            checkSignUp = false;
                                            Log.d("EEE",response);
                                        }
                                        else{
                                            //BCrypt.
                                            String pass = BCrypt.hashpw(sPassword,BCrypt.gensalt());
                                            // tiến hành đăng ký
                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSignUp,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            if (response.trim().equals("successful")){
                                                                notify.setText("Đăng ký thành công!");
                                                                notify.setTextColor(Color.GREEN);
                                                                checkSignUp = true;

                                                                Intent intent = new Intent(getActivity(),activity_login.class);
                                                                intent.putExtra("username_signup",sUsername);
                                                                startActivity(intent);
                                                            }
                                                            else {
                                                                notify.setText("*Lỗi kết nối! Vui lòng thử lại!");
                                                                notify.setTextColor(Color.RED);
                                                                checkSignUp = true;
                                                            }
                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {

                                                        }
                                                    }) {
                                                @Nullable
                                                @org.jetbrains.annotations.Nullable
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    HashMap<String, String> params = new HashMap<>();
                                                    params.put("username", sUsername);
                                                    params.put("password", pass);
                                                    params.put("email", sEmail);
                                                    return params;
                                                }
                                            };
                                            VolleyPool.getInstance(getContext()).addRequest(stringRequest);


                                            Log.d("EEE", response);
                                        }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                        @Nullable
                        @org.jetbrains.annotations.Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("username", sUsername);
                            return params;
                        }
                    };
                    VolleyPool.getInstance(getContext()).addRequest(stringRequest);
                }

                if (checkSignUp) {
                    notify.setTextColor(Color.GREEN);
                }
            }

        });


        return root;
    }

    public void checkUsername(String username) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCheckUsername,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", username);
                return params;
            }
        };
        VolleyPool.getInstance(getContext()).addRequest(stringRequest);

    }

    public void signUp(String email, String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSignUp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        VolleyPool.getInstance(getContext()).addRequest(stringRequest);

    }

    public void mapping(View root) {
        email = root.findViewById(R.id.email_signUp);
        pass = root.findViewById(R.id.password_signUp);
        repass = root.findViewById(R.id.repassword_signUp);
        btnSignUp = root.findViewById(R.id.button_singup);
        notify = root.findViewById(R.id.notify_signUp);
        username = root.findViewById(R.id.username_signUp);

//        email.setTranslationX(0);
//        pass.setTranslationX(0);
//        repass.setTranslationX(0);
//        btnSignUp.setTranslationX(0);
//
//        email.setAlpha(v);
//        pass.setAlpha(v);
//        repass.setAlpha(v);
//        btnSignUp.setAlpha(v);
//
//        email.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(300).start();
//        pass.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(500).start();
//        repass.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(500).start();
//        btnSignUp.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(700).start();
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
