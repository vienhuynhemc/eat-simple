package com.vientamthuong.eatsimple.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.jbCrypt.BCrypt;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SingupTabFragment extends Fragment {
    // test commit
    EditText email, pass, repass, username;
    TextView notify;
    ImageView imgView;
    Button btnSignUp;
    String sRePassword = "", sEmail = "", sPassword = "", sUsername = "";
    boolean checkPassword, checkEmail, checkLengthPass, checkSignUp, checkUsername;
    String urlCheckUsername = "https://eat-simple-app.000webhostapp.com/checkUsername.php";
    String urlSignUp = "https://eat-simple-app.000webhostapp.com/signUp.php";
    float v = 0;
    int count = 100;

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
                    notify.setTextColor(Color.RED);
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

                                            // tiến hành đăng ký
                                            imgView = root.findViewById(R.id.activity_signup_img);

                                            // gửi mail
                                            sendMail(sEmail);


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
    public void sendMail(String emailTo){
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
            message.setSubject("Đăng ký tài khoản Eat Simple thành công!");
            message.setText("Chúc mừng bạn đã đăng ký tài khoản Eat Simple thành công! Đây là email chúng tôi sẽ liên hệ bạn mỗi khi có thông báo về Eat Simple, hi vọng bạn sẽ hài lòng về các món ăn của Eat Simple.");
            new SendMail().execute(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void mapping(View root) {
        email = root.findViewById(R.id.email_signUp);
        pass = root.findViewById(R.id.password_signUp);
        repass = root.findViewById(R.id.repassword_signUp);
        btnSignUp = root.findViewById(R.id.button_singup);
        notify = root.findViewById(R.id.notify_signUp);
        username = root.findViewById(R.id.username_signUp);
        imgView = root.findViewById(R.id.activity_signup_img);


    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void createAccountFireBase(String ma_tai_khoan,String email,String hinh_dai_dien, String link_hinh_dai_dien,
                                       String mat_khau,String ngay_tao, String tai_khoan,String ten_hien_thi){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("tai_khoan").child(ma_tai_khoan);

        database.child("email").setValue(email);
        database.child("hinh_dai_dien").setValue(hinh_dai_dien);
        database.child("link_hinh_dai_dien").setValue(link_hinh_dai_dien);
        database.child("mat_khau").setValue(mat_khau);
        database.child("ngay_tao").setValue(ngay_tao);
        database.child("tai_khoan").setValue(tai_khoan);
        database.child("ten_hien_thi").setValue(ten_hien_thi);
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
    private void uploadImgToFirebase(ImageView imageView, String ma_tai_khoan){
        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("tai_khoan");

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(ma_tai_khoan).child(ma_tai_khoan.substring(ma_tai_khoan.lastIndexOf("_")+1)+".jpg");

        // Get the data from an ImageView as byte
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Uri imgLink = taskSnapshot.getUploadSessionUri();
            }
        });
    }
    private void uploadImage(String ma_tai_khoan){
        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("tai_khoan");

        StorageReference mountainsRef = storageRef.child(ma_tai_khoan);
        String url = "images/avatar.png";

        Uri link = Uri.parse(url);

            mountainsRef.putFile(link).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Toast.makeText(ProfileActivity.this, "Upload successful!", Toast.LENGTH_LONG).show();
                    Log.d("signup","load anh thanh cong");

                    mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
//                            String url = uri.toString();
//                            imgAccount = url;
//                            changeImageAccount(account.getId(),url);
//                            DatabaseReference database = FirebaseDatabase.getInstance().getReference("tai_khoan").child(account.getId());
//                            database.child("link_hinh_dai_dien").setValue(url);
                        }
                    });

                }
            });
    }
    private class SendMail extends AsyncTask<Message,String,String> {

        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "y";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "n";
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

            if (s.equals("y")){
                String pass = BCrypt.hashpw(sPassword,BCrypt.gensalt());
                signUp(sUsername,pass,sEmail);
            }
            else{
                notify.setText("Không thể gửi email, Vui lòng thử lại!");
                notify.setTextColor(Color.RED);
            }
        }
    }
    private void signUp(String username, String pass, String email){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSignUp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("successful")){
                            // lưu tài khoản firebase
                            String url = "https://eat-simple-app.000webhostapp.com/getIdAccount.php";
                            StringRequest string = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            int count = Integer.parseInt(response)-1;

                                            String img = "https://firebasestorage.googleapis.com/v0/b/eat-simple.appspot.com/o/tai_khoan%2Fno_name%2Favatar-1577909_960_720.png?alt=media&token=8c4906c4-5fee-4455-b1a2-8340291dbd1f";
                                            createAccountFireBase("kh_"+count,sEmail,"tai_khoan/kh_"+count+"/"+count+".jpg",img,pass,getDateTimeNow().toString(),sUsername,"No Name");

//                                                                                Glide.with(getActivity()).load(img).into(imgView);
//                                                                                uploadImgToFirebase(imgView,"kh_"+count);
                                            uploadImage("kh_"+count);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            VolleyPool.getInstance(getActivity()).addRequest(string);

                            notify.setText("Đăng ký thành công!");
                            notify.setTextColor(Color.GREEN);
                            checkSignUp = true;

                            Intent intent = new Intent(getActivity(), Activity_login.class);
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
                params.put("username", username);
                params.put("password", pass);
                params.put("email", email);
                return params;
            }
        };
        VolleyPool.getInstance(getContext()).addRequest(stringRequest);

    }

}
