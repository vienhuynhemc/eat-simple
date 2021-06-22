package com.vientamthuong.eatsimple.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.login.Activity_login;
import com.vientamthuong.eatsimple.model.Account;

import java.util.ArrayList;

public class Activity_detail extends AppCompatActivity {

    private static final String TAG = "Activity_detail";

    private ArrayList<String> imgList = new ArrayList<>();

    private TextView title, gia, sosao, kcal, time, contentdetail, soluong;
    private ImageView hinh;
    private Button decre, incre;
    private FloatingActionButton back, detail_add;
    private int num = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getImg();
        getView();
        init();
        event();

    }

    void event() {

        incre.setOnClickListener(v -> {
            num += 1;
            soluong.setText(String.valueOf(num));
        });
        decre.setOnClickListener(v -> {
            if (num > 1) {
                num -= 1;
                soluong.setText(String.valueOf(num));
            }
        });
        back.setOnClickListener(v -> {
            finish();
        });
        detail_add.setOnClickListener(v -> {

            try {
                Account account = DataLocalManager.getInstance().getAccount();
                Toast.makeText(this, "ĐÃ đăng nhập", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Dialog dialog = openDialogDatabase(R.layout.activity_cart_dialog_login);
                eventDialog(dialog);
            }
        });
    }
   void eventDialog(Dialog dialog){
        Button back = dialog.findViewById(R.id.dialog_lost_connection_ignore);
        Button login = dialog.findViewById(R.id.dialog_lost_connection_try);

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });
       login.setOnClickListener(v -> {

           Intent intent = new Intent(Activity_detail.this, Activity_login.class);
           intent.putExtra("Call","Activity_detail");

           startActivity(intent);

       });



    }

    Dialog openDialogDatabase(int layout) {
        final Dialog dialog = new Dialog(this);
        // ẩn title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set background diaog
        dialog.setContentView(layout);

        Window window = dialog.getWindow();

        if (window == null) {
            return null;
        } else {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            window.setAttributes(layoutParams);
            dialog.setCancelable(true);
            dialog.show();
            return dialog;
        }
    }

    void init() {

        Intent intent = getIntent();

        Product product = (Product) intent.getSerializableExtra("product");

        title.setText(product.getTen_sp());
        gia.setText(product.getGia_km() + " VND");
        time.setText(product.getThoi_gian_nau() - 5 + " - " + product.getThoi_gian_nau() + " m");
        kcal.setText(String.valueOf(product.getKcal()));
        contentdetail.setText(product.getThong_tin());


        byte[] bitmaps = intent.getByteArrayExtra("bitmap");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length);
        hinh.setImageBitmap(bitmap);

    }

    void getView() {

        title = findViewById(R.id.text_hbg);
        gia = findViewById(R.id.text_price);
        sosao = findViewById(R.id.ratingtxt);
        time = findViewById(R.id.timetxt);
        kcal = findViewById(R.id.calotxt);
        hinh = findViewById(R.id.hinh_sp);
        decre = findViewById(R.id.decre);
        incre = findViewById(R.id.incre);
        soluong = findViewById(R.id.number);
        back = findViewById(R.id.detail_back);
        contentdetail = findViewById(R.id.contentdetail);
        detail_add = findViewById(R.id.detail_add);

    }

    private void getImg() {
        Log.d(TAG, "initImgBitmap: preparing bitmaps.");

        imgList.add("https://i.imgur.com/K4Wpnri.png");
        imgList.add("https://i.imgur.com/GEONVpI.png");
        imgList.add("https://i.imgur.com/zdw5f4L.png");
        imgList.add("https://i.imgur.com/86F4qFZ.png");
        imgList.add("https://i.imgur.com/G7eVNSm.png");

        initRecylerView();
    }

    private void initRecylerView() {
        Log.d(TAG, "initRecylerView: init recycler view.");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(this, imgList);
        recyclerView.setAdapter(adapter);
    }
}