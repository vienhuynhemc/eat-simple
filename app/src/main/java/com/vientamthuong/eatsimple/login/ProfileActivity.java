package com.vientamthuong.eatsimple.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.jbCrypt.BCrypt;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.model.Account;
import com.vientamthuong.eatsimple.model.AccountFirebase;
import com.vientamthuong.eatsimple.wishlist.WishlistActivity;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URI;

import gun0912.tedbottompicker.TedBottomPicker;

public class ProfileActivity extends AppCompatActivity {

    // get Account // phải tồn tại 1 account mới có thể vào trang này được.
    private Account account = DataLocalManager.getAccount();
    LinearLayout btnInfo, btnPassword;
    TextView btnSignOut, btnBack, name, email;
    ImageView img;
    View border1, border2;
    String imgAccount = account.getImgLink();
    Uri link;
    final int REQUEST_CODE_IMAGE = 1;
    LinearLayout btnWishlist;

    // dialog change img
    ImageView imageDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mapping();


        //account = new Account("","nhattruongmagtm","","nhattruongagtm@gmail.com","","","https://lovicouple.com/wp-content/uploads/2019/12/vai-avatar-cap-cho-cac-ban-de-facebook-25.jpg","School",null,"");

        // handler change password
        changePassword();

        // handler change info
        changeInfo();

        //handler sign out
        handlerSignOut();

        //handler back home page
        handlerBack();

        // handler btn wishlist
        handlerWishlist();

    }
    private void mapping(){
        btnInfo = findViewById(R.id.activity_profile_info);
        btnPassword = findViewById(R.id.activity_profile_password);
        btnBack = findViewById(R.id.btnBack);
        btnSignOut = findViewById(R.id.btnSignOut);
        name = findViewById(R.id.activity_profile_info_name);
        email = findViewById(R.id.activity_profile_info_email);
        img = findViewById(R.id.activity_profile_info_img);
        btnWishlist = findViewById(R.id.activity_profile_wishlist);
        border1 = findViewById(R.id.activity_profile_border1);
        border2 = findViewById(R.id.activity_profile_border2);

        if (account.getId().length() > 15){
            btnPassword.setVisibility(View.GONE);
            btnInfo.setVisibility(View.GONE);
            border1.setVisibility(View.GONE);
            border2.setVisibility(View.GONE);
        }
        //
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("tai_khoan").child(account.getId());
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                AccountFirebase account = snapshot.getValue(AccountFirebase.class);
                name.setText(account.getTen_hien_thi());
                email.setText(account.getEmail());
                Glide.with(ProfileActivity.this).load(account.getLink_hinh_dai_dien()).into(img);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Lỗi info", Toast.LENGTH_SHORT).show();
            }
        });

//        name.setText(account.getName());
//        email.setText(account.getEmail());
//        Glide.with(this).load(account.getImgLink()).into(img);
    }
    private void handlerSignOut(){
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }
    private void handlerBack(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void changeInfo(){
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogChangeInfo();
            }
        });
    }
    private void changePassword(){
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createaDialogChangePassword();
            }
        });
    }
    private void handlerWishlist(){
        btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, WishlistActivity.class);
                intent.putExtra("ma_kh",account.getId());
                startActivity(intent);
            }
        });
    }
    private void createaDialogChangePassword(){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_profile_change_pass);

        //mapping
        TextInputEditText newpass, repass;
        Button btnConfirm, btnCancel;
        TextView notify;

        newpass = dialog.findViewById(R.id.activity_profile_newpass);
        repass = dialog.findViewById(R.id.activity_profile_repass);
        btnConfirm = dialog.findViewById(R.id.activity_profile_confirm_pass);
        btnCancel = dialog.findViewById(R.id.activity_profile_cancel_pass);
        notify = dialog.findViewById(R.id.activity_profile_notify);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String npass = newpass.getText().toString().trim();
                String rpass = repass.getText().toString().trim();

                if (npass.equals("") || rpass.equals("")){
                    notify.setText("*Vui lòng nhập đầy đủ thông tin!");
                }
                else if(npass.length() <8){
                    notify.setText("*Mật khẩu phải có tối thiểu 8 ký tự!");
                }
                else if(!rpass.equals(npass)){
                    notify.setText("*Mật khẩu không khớp!");
                }
                else{
                    String url = "https://eat-simple-app.000webhostapp.com/setPasswordAccount.php";
                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("success")){
                                        notify.setText("Đổi mật khẩu thành công!");
                                        notify.setTextColor(Color.GREEN);

                                        dialog.dismiss();
                                    }
                                    else{
                                        notify.setText("*Đã xảy ra lỗi, vui lòng thử lại!");
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Nullable
                        @org.jetbrains.annotations.Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> params = new HashMap<>();
                            params.put("username",account.getUsername());
                            params.put("password", BCrypt.hashpw(npass,BCrypt.gensalt()));
                            return params;
                        }
                    };
                    VolleyPool.getInstance(ProfileActivity.this).addRequest(request);
                }
            }
        });

        dialog.show();
    }
    private void createDialogChangeInfo(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_profile_change_info);

        CardView imgChange;
        TextInputEditText name, email;
        Button btnCancel, btnChange;

        imageDisplay = dialog.findViewById(R.id.activity_profile_change_img_display);
        imgChange = dialog.findViewById(R.id.activity_profile_change_img);
        name = dialog.findViewById(R.id.activity_profile_change_name);
        email = dialog.findViewById(R.id.activity_profile_change_email);
        btnCancel = dialog.findViewById(R.id.activity_profile_change_cancel);
        btnChange = dialog.findViewById(R.id.activity_profile_change_yes);

        // load info
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("tai_khoan").child(account.getId());
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                AccountFirebase account = snapshot.getValue(AccountFirebase.class);
                Glide.with(ProfileActivity.this).load(account.getLink_hinh_dai_dien()).into(imageDisplay);
                name.setText(account.getTen_hien_thi());
                email.setText(account.getEmail());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //handler
        imgChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // thay ảnh
                getImage();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail = email.getText().toString().trim();
                String mName = name.getText().toString().trim();


                String url = "https://eat-simple-app.000webhostapp.com/changeInfo.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("successful")) {

                                    setInfo(account.getId(), mName, mEmail);


                                    // Create a storage reference from our app
                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference("tai_khoan");

                                    // Create a reference to "mountains.jpg"
                                    StorageReference mountainsRef = storageRef.child(account.getId());
                                    if (link != null) {

                                        mountainsRef.putFile(link).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                //Toast.makeText(ProfileActivity.this, "Upload successful!", Toast.LENGTH_LONG).show();

                                                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String url = uri.toString();
                                                        imgAccount = url;
                                                        changeImageAccount(account.getId(),url);
                                                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("tai_khoan").child(account.getId());
                                                        database.child("link_hinh_dai_dien").setValue(url);
                                                    }
                                                });

                                            }
                                        });

                                        Toast.makeText(ProfileActivity.this, "Thay đổi thông tin thành công!", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                    else{

                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("tai_khoan").child(account.getId());

                                        databaseReference.child("ten_hien_thi").setValue(mName);
                                        databaseReference.child("email").setValue(mEmail);

                                        Toast.makeText(ProfileActivity.this, "Thay đổi thông tin thành công!", Toast.LENGTH_SHORT).show();

                                        dialog.dismiss();
                                    }
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Nullable
                    @org.jetbrains.annotations.Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("username",account.getUsername());
                        params.put("name",mName);
                        params.put("email",mEmail);
                        return params;
                    }
                };
                VolleyPool.getInstance(ProfileActivity.this).addRequest(stringRequest);

            }
        });

        dialog.show();
    }
    private void signOut(){
        DataLocalManager.setAccount(null);
        finish();
        startActivity(new Intent(ProfileActivity.this,Activity_login.class));
    }
    private void uploadImgToFirebase(ImageView imageView,String ma_tai_khoan){
        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference("tai_khoan");

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(ma_tai_khoan).child(ma_tai_khoan.substring(ma_tai_khoan.lastIndexOf("_")+1)+".jpg");

        // Get the data from an ImageView as bytes
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
                Toast.makeText(ProfileActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Uri imgLink = taskSnapshot.getUploadSessionUri();
                imgAccount = imgLink+"";
            }
        });
    }
    private void getImage(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ProfileActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }
    private void openImagePicker(){
        TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener() {
            @Override
            public void onImageSelected(Uri uri) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    imageDisplay.setImageBitmap(bitmap);
                    link = uri;
                    Log.d("img",link+" lik");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(ProfileActivity.this)
                .setOnImageSelectedListener(listener).create();
        tedBottomPicker.show(getSupportFragmentManager());
    }
    private void setInfo(String ma_tai_khoan, String name, String email){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("tai_khoan").child(ma_tai_khoan);

        databaseReference.child("ten_hien_thi").setValue(name);
        databaseReference.child("email").setValue(email);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    link = Uri.parse(snapshot.child("link_hinh_dai_dien").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    private void changeImageAccount(String ma_tai_khoan, String link_hinh_dai_dien){
        String url = "https://eat-simple-app.000webhostapp.com/changeImageAccount.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")){
                            Log.d("profile","thay doi anh thanh cong");
                        }
                        else{
                            Log.d("profile","thay doi anh that bai");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("profile",error.toString());
                    }
                }){
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("ma_tai_khoan",ma_tai_khoan);
                params.put("link_hinh_dai_dien",link_hinh_dai_dien);
                return params;
            }
        };
        VolleyPool.getInstance(this).addRequest(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageDisplay.setImageBitmap(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}