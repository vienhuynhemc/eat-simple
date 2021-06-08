package com.vientamthuong.eatsimple.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.admin.HomePageActivity;
import com.vientamthuong.eatsimple.admin.session.DataSession;
import com.vientamthuong.eatsimple.jbCrypt.BCrypt;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.model.Account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoginTabFragment extends Fragment {

    EditText username;
    View forgotPass;
    AppCompatButton login;
    EditText pass;
    TextView notify;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_login_tab_fragment, container, false);

        username = root.findViewById(R.id.username_login);
        pass = root.findViewById(R.id.password_login);
        forgotPass = root.findViewById(R.id.forgetPassword_login);
        login = root.findViewById(R.id.btn_login);
        notify = root.findViewById(R.id.notify_login);

        // Test để làm admin
        login.setOnClickListener(v1 -> {
            String tai_khoan = username.getText().toString().trim();
            String mat_khau = pass.getText().toString().trim();
            if(tai_khoan.equals("") || mat_khau.equals("")){
                notify.setText("*Vui lòng nhập đủ thông tin!");
            }
            else {
                Map<String, String> result = new LinkedHashMap<>();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "http://eat-simple-app.000webhostapp.com/lay_tai_khoan.php",
                        response -> {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    result.put("mat_khau", jsonObject.getString("mat_khau"));
                                    result.put("ma_tai_khoan", jsonObject.getString("ma_tai_khoan"));
                                    result.put("cap_do", jsonObject.getString("cap_do"));
                                }
                                if (result.size() == 0) {
                                    System.out.println("Sai tài khoản nhân viên, admin");

                                    // kiểm tra tới tài khoản khách hàng
                                    String urlLogin = "https://eat-simple-app.000webhostapp.com/login.php";
                                    StringRequest request = new StringRequest(Request.Method.POST, urlLogin,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    if (response.trim().equals("")) {
                                                        notify.setTextColor(Color.RED);
                                                        notify.setText("*Không tồn tại tài khoản!");
                                                    } else {
                                                        try {
//                                                        Log.d("EEE",response);
                                                            JSONObject object = new JSONObject(response);
                                                            String user = object.getString("tai_khoan");
                                                            String hashPassword = object.getString("mat_khau");
                                                            String email = object.getString("email");
                                                            String name = object.getString("ten_hien_thi");
                                                            String imgLink = object.getString("link_hinh_dai_dien");
                                                            if (BCrypt.checkpw(mat_khau, hashPassword)) {
                                                                notify.setText("*Đăng nhập thành công!");
                                                                notify.setTextColor(Color.GREEN);


                                                                Intent intent = new Intent(getActivity(), com.vientamthuong.eatsimple.homePage.HomePageActivity.class);

                                                                Log.d("EEE", "Response: " + response.toString());

                                                                startActivity(intent);
                                                            } else {
                                                                notify.setTextColor(Color.RED);
                                                                notify.setText("*Không tồn tại tài khoản!");
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

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
                                            params.put("username", username.getText().toString().trim());
                                            return params;
                                        }
                                    };
                                    VolleyPool.getInstance(getActivity()).addRequest(request);
                                } else {
                                    if (BCrypt.checkpw(mat_khau, result.get("mat_khau"))) {
                                        System.out.println("dang nhap thanh cong");
                                        System.out.println(result);
                                        DataSession.getInstance().setMaTaiKhoan(result.get("ma_tai_khoan"));
                                        DataSession.getInstance().setCap_do(Integer.parseInt(result.get("cap_do")));
                                        Intent intent = new Intent();
                                        intent.setClass(getActivity(), HomePageActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);
                                    } else {
                                        System.out.println("Sai mat khau");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> {
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("tai_khoan", tai_khoan);
                        return params;
                    }
                };
                VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
            }
        });

        // hiển thị username khi đăng ký thành công!
        Intent intent = getActivity().getIntent();
        if (intent.getStringExtra("username_signup") != null){
            username.setText(intent.getStringExtra("username_signup"));
        }





        /////////////////////

//        email.setTranslationX(0);
//        pass.setTranslationX(0);
//        forgotPass.setTranslationX(0);
//        login.setTranslationX(0);
//
//        email.setAlpha(v);
//        pass.setAlpha(v);
//        forgotPass.setAlpha(v);
//        login.setAlpha(v);
//
//        email.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(300).start();
//        pass.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(500).start();
//        forgotPass.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(500).start();
//        login.animate().translationY(100).alpha(1).setDuration(800).setStartDelay(700).start();


        return root;
    }

    public void login(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String username =
            }
        });
    }
}
