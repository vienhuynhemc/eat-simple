package com.vientamthuong.eatsimple.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.admin.HomePageActivity;
import com.vientamthuong.eatsimple.admin.session.DataSession;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.homePage.HomeMeowBottom;
import com.vientamthuong.eatsimple.jbCrypt.BCrypt;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.model.Account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class LoginTabFragment extends Fragment {

    EditText username;
    View forgotPass;
    AppCompatButton login;
    EditText pass;
    TextView notify;
    float v = 0;
    String codeRD;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // khởi tạo
        FacebookSdk.sdkInitialize(getActivity());
        AppEventsLogger.activateApp(getActivity());



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_login_tab_fragment, container, false);

        username = root.findViewById(R.id.username_login);
        pass = root.findViewById(R.id.password_login);
        forgotPass = root.findViewById(R.id.forgetPassword_login);
        login = root.findViewById(R.id.btn_login);
        notify = root.findViewById(R.id.notify_login);

        // login google
        loginGoogle(root);


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

                                                                getAccount(tai_khoan);

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

        // hiển thị username khi thay đổi mật khẩu thành công!
        Intent intent1 = getActivity().getIntent();
        if (intent1.getStringExtra("account_forgot") != null){
            username.setText(intent1.getStringExtra("account_forgot"));
        }
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().trim().equals("")){
                    notify.setText("*Vui lòng nhập username!");
                    notify.setTextColor(Color.RED);
                }
                else {
                    // toa dialog
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.fragment_login_forgot_password);

                    TextInputEditText email_forgot = dialog.findViewById(R.id.email_forgot);
                    Button btnSend = dialog.findViewById(R.id.btnYes_forgot_send);
                    Button btnCancel = dialog.findViewById(R.id.btnNo_forgot_send);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    String urlLogin = "https://eat-simple-app.000webhostapp.com/login.php";
                    StringRequest request = new StringRequest(Request.Method.POST, urlLogin, new
                            Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.trim().equals("")){
                                        notify.setText("*Tài khoản không tồn tại trong hệ thống!");
                                    }
                                    else{
                                        try {
                                            JSONObject object = new JSONObject(response);
                                            Account account = new Account();
                                            String user = object.getString("tai_khoan");
                                            String hashPassword = object.getString("mat_khau");
                                            String email = object.getString("email");
                                            String name = object.getString("ten_hien_thi");
                                            String imgLink = object.getString("link_hinh_dai_dien");
                                            account.setName(name);
                                            account.setImgLink(imgLink);
                                            account.setEmail(email);
                                            account.setUsername(user);
                                            account.setPassword(hashPassword);

                                            email_forgot.setText(account.getEmail());

                                            dialog.show();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("EEE", error.toString());
                                }
                            }){
                        @Nullable
                        @org.jetbrains.annotations.Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> params = new HashMap<>();
                            params.put("username",username.getText().toString().trim());
                            return params;
                        }
                    };

                    btnSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            codeRD = randomCode();

//                                                    ProgressDialog progressDialog = new ProgressDialog(getContext());
//                                                    progressDialog.show();
//                                                    progressDialog.setContentView(R.layout.fragment_login_progress_dialog);
//                                                    progressDialog.setCancelable(false);
//                                                    progressDialog.getWindow().setLayout(500,500);

                            // gửi mail
                             sendMail(email_forgot.getText().toString().trim(),codeRD);
                        }
                    });

                    VolleyPool.getInstance(getContext()).addRequest(request);
                }
            }
        });

        callbackManager = CallbackManager.Factory.create();

        loginButton =  root.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("AAA",object.toString());
                        try {
                            Account account = new Account();
                            String name = object.getString("name");
                            String id = object.getString("id");
                            String avatar = "https://graph.facebook.com/"+id+"/picture?type=large";
                            account.setName(name);
                            account.setId(id);
                            account.setImgLink(avatar);
                            account.setEmail("profile.facebook@gmail.com");

                            createAccountAPI(account.getId(),account.getName(),account.getImgLink(),account.getEmail());

                            DataLocalManager.setAccounts(account);

                            Log.d("SSS", DataLocalManager.getAccount().toString());

                            startActivity(new Intent(getActivity(), HomeMeowBottom.class));

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                Bundle bundle= new Bundle();
                bundle.putString("fields","gender, name, id, first_name, last_name");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();

//                if (DataLocalManager.getAccount()!= null){
//                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                }

                Log.d("AAA","Login successful!");
                //  Log.d("AAA",account.getName());
            }

            @Override
            public void onCancel() {
                Log.d("AAA","Login canceled!");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("AAA","Login error!");
            }
        });

        return root;
    }
    public void sendMail(String emailTo,String codeM){
        String sEmail = "eatsimple2021@gmail.com";
        String sPassword = "iacjphdbzujyglyy";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sEmail,sPassword);
            }
        });


        try {
            // create content
            Message message = new MimeMessage(session);
            // send mail
            message.setFrom(new InternetAddress(sEmail));

            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailTo));
            message.setSubject("Xác nhận tài khoản Eat Simple");
            message.setText("Mã xác thực của bạn là: "+codeM);
            new SendMail().execute(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private class SendMail extends AsyncTask<Message,String,String> {

        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "error";
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),"Vui lòng đợi...","Đang gửi...",true,false);

        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            progressDialog.dismiss();

            if (s.equals("success")){
                String url = "https://eat-simple-app.000webhostapp.com/createCodeForgotPassword.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            createDialogCode();
                        }
                        else{
                            Toast.makeText(getContext(), "Không thể tạo mã", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public HashMap<String, String> getParams() {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("username", username.getText().toString().trim());
                        params.put("randomCode", codeRD);
                        params.put("dateNow",getDateTimeNow()+"");
                        return params;
                    }
                };
                VolleyPool.getInstance(getContext()).addRequest(stringRequest);
            }
            else{
                Toast.makeText(getActivity(), "Send Error!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static String randomCode() {
        String s = "";
        Random rd = new Random();
        String text = "abcdefghiklmnopqrstuvwxyzABCDEFGHIKLMNOPQSTUVWXYZ0123456789";
        char[] ch = text.toCharArray();
        for(int i = 0; i < 6;i++) {
            int num = rd.nextInt(text.length());
            s += ch[num];
        }
        return s;
    }
    public void createDialogCode(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_dialog_enter_code);
        TextInputEditText code = dialog.findViewById(R.id.fragment_dialog_input_code_forgot);
        Button btnYes = dialog.findViewById(R.id.btn_confirm_code);
        Button btnNo = dialog.findViewById(R.id.btnNo_code);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encode = code.getText().toString().trim();
                String url = "https://eat-simple-app.000webhostapp.com/checkCodeForgotPassword.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onResponse(String response) {
                                    try {
                                        JSONObject object = new JSONObject(response);
                                        String codeServer = object.getString("ma_quen_mat_khau");
                                        String dateServer = object.getString("han_su_dung_ma_qmk");

                                        if (encode.equals(codeServer)){
                                            if(checkDate(getDateTimeNow(),getDate(dateServer))) {
                                                createDialogSetPassword();
                                            }
                                            else{
                                                Toast.makeText(getActivity(), "Mã đã hết hạn!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else{
                                            Toast.makeText(getActivity(), "Mã xác thực không đúng!", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Lỗi...", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Nullable
                    @org.jetbrains.annotations.Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("username",username.getText().toString().trim());
                        return params;
                    }
                };
                VolleyPool.getInstance(getContext()).addRequest(stringRequest);
            }
        });
        dialog.show();
    }

    public DateTime getDateTimeNow(){
       DateTime now = new DateTime();

        java.util.Date time = Calendar.getInstance().getTime();
        System.out.println(time);

        Calendar date = Calendar.getInstance();

        int day = Integer.parseInt(date.get(Calendar.DATE)+"");
        int month = Integer.parseInt(date.get(Calendar.MONTH)+"");
        int year = Integer.parseInt(date.get(Calendar.YEAR)+"");

        String times = time.toString().split(" ")[3];
        String[] t = times.split(":");
        int hour = Integer.parseInt(t[0]+"");
        int minute = Integer.parseInt(t[1]+"");
        int second = Integer.parseInt(t[2]+"");


       now.setDay(day);
       now.setMonth(month);
       now.setYear(year);
       now.setHour(hour);
       now.setMinute(minute);
       now.setSecond(second);

       return now;
    }
    public DateTime getDate(String dateNow){
        String[] dateTime = dateNow.split(" ");
        String date = dateTime[0];
        String time = dateTime[1];

        int day = Integer.parseInt(date.split("-")[2]);
        int month = Integer.parseInt(date.split("-")[1]);
        int year = Integer.parseInt(date.split("-")[0]);

        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);
        int second = Integer.parseInt(time.split(":")[2]);

        DateTime d = new DateTime();
        d.setDay(day);
        d.setMonth(month);
        d.setYear(year);
        d.setHour(hour);
        d.setMinute(minute);
        d.setSecond(second);

        return d;
    }
    public static boolean checkDate(DateTime now, DateTime before){
        long a = before.getDay()*1440+before.getMonth()*43200 + now.getYear()*15758000+ before.getHour()*60+before.getMinute()+before.getSecond()/60;
        long b = now.getDay()*1440+now.getMonth()*43200 + now.getYear()*15758000+ now.getHour()*60+now.getMinute()+now.getSecond()/60;
        if(b - a <= 3){
            return true;
        }
        return false;
    }
    public void createDialogSetPassword(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_login_change_pass);

        TextInputEditText pass = dialog.findViewById(R.id.newPass_login_forgot);
        TextInputEditText rePass = dialog.findViewById(R.id.rePass_login_forgot);
        Button btnExist = dialog.findViewById(R.id.btnNo_exist_forgot_form);
        Button btnConfirm = dialog.findViewById(R.id.btnNo_confirm_forgot_form);

        btnExist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p = pass.getText().toString().trim();
                String rp = rePass.getText().toString().trim();

                if (p.equals("") || rp.equals("")){
                    Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    if (!p.equals(rp)) {
                        Toast.makeText(getActivity(), "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                    } else if (p.length()< 8){
                        Toast.makeText(getActivity(), "Mật khẩu phải tối thiểu 8 kí tự!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // tạo mật khẩu mới
                        String url = "https://eat-simple-app.000webhostapp.com/setPasswordAccount.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.trim().equals("success")){

                                            // thông báo thành công
                                            Dialog dialog = new Dialog(getActivity());
                                            dialog.setContentView(R.layout.fragment_login_forgot_success);
                                            dialog.setCancelable(false);

                                            Button btnExit = dialog.findViewById(R.id.btn_exit_forgot);

                                            btnExit.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                    Intent intent = new Intent(getActivity(),Activity_login.class);
                                                    intent.putExtra("account_forgot",username.getText().toString());
                                                    startActivity(intent);
                                                }
                                            });

                                            dialog.show();

                                        }
                                        else if(response.trim().equals("fail")){
                                            Toast.makeText(getActivity(), "Không thể đổi mật khẩu!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity(), "Lỗi đổi mật khẩu!", Toast.LENGTH_SHORT).show();
                                    }
                                }){
                            @Nullable
                            @org.jetbrains.annotations.Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> params = new HashMap<>();
                                params.put("username",username.getText().toString().trim());
                                params.put("password",BCrypt.hashpw(p,BCrypt.gensalt()));
                                return params;
                            }
                        };
                        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
                    }
                }
            }
        });

        dialog.show();
    }
    private void getAccount(String username){
        String url ="https://eat-simple-app.000webhostapp.com/getAccount.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            String id = object.getString("ma_tai_khoan");
                            String username = object.getString("tai_khoan");
                            String password = object.getString("mat_khau");
                            String email = object.getString("email");
                            String forgotCode = object.getString("ma_quen_mat_khau");
                            String avatar = object.getString("hinh_dai_dien");
                            String img = object.getString("link_hinh_dai_dien");
                            String name = object.getString("ten_hien_thi");
                            String date = object.getString("ngay_tao");
                            String expireDate = object.getString("han_su_dung_ma_qmk");

                            Account account = new Account();
                            account.setId(id);
                            account.setUsername(username);
                            account.setPassword(password);
                            account.setEmail(email);
                            account.setForgotPasswordId(forgotCode);
                            account.setImg(avatar);
                            account.setImgLink(img);
                            account.setName(name);
                            account.setExpireDateCode(expireDate);
                            account.setDateCreated(getDate(date));

                            Log.d("ZZZ",account.toString());
                            //Log.d("CCC",response);

                            if(account.getUsername() != null){
                                DataLocalManager.setAccounts(account);
                            }

                            if (DataLocalManager.getAccount()!= null) {

                                Intent intent;
                                Bundle bundle = getArguments();
                                if (bundle != null)
                                {
                                    String route = bundle.getString("Call");
                                    if(route.equals("Activity_detail")){
                                        System.out.println("NHận đc back");
                                        getActivity().finish();
                                    }
                                }else{
                                    intent = new Intent(getActivity(), HomeMeowBottom.class);

                                    //Log.d("CCC", "Response: " + response.toString());

                                    startActivity(intent);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("username",username);
                return params;
            }
        };
        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);

            Account account = new Account();
            account.setId(acc.getId());
            account.setImgLink(acc.getPhotoUrl()+"");
            account.setName(acc.getDisplayName());
            account.setEmail(acc.getEmail());

            createAccountAPI(account.getId(),account.getName(),account.getImgLink(),account.getEmail());

            DataLocalManager.setAccounts(account);

            startActivity(new Intent(getActivity(), HomeMeowBottom.class));

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void loginGoogle(View root){
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = root.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }
    private void createAccountAPI(String id,String name,String img,String email){
        String url = "https://eat-simple-app.000webhostapp.com/createAccountAPI.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {
                            Toast.makeText(getActivity(), "create success", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "error create", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "error volley create", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("ma_tai_khoan",id);
                params.put("ten_hien_thi",name);
                params.put("link_hinh_dai_dien",img);
                params.put("email",email);
                return params;
            }
        };
        VolleyPool.getInstance(getContext()).addRequest(request);

        // add account firebase
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("tai_khoan");

        database.child(id).child("email").setValue(email);
        database.child(id).child("hinh_dai_dien").setValue("tai_khoan/"+id+"/"+id+".jpg");
        database.child(id).child("link_hinh_dai_dien").setValue(img);
        database.child(id).child("mat_khau").setValue("null");
        database.child(id).child("ngay_tao").setValue("null");
        database.child(id).child("tai_khoan").setValue(id);
        database.child(id).child("ten_hien_thi").setValue(name);
    }

}
