package com.vientamthuong.eatsimple.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.vientamthuong.eatsimple.R;

import java.util.ArrayList;

public class Activity_detail extends AppCompatActivity {

    private static final String TAG = "Activity_detail";

    private ArrayList<String> imgList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getImg();
    }

    private void getImg(){
        Log.d(TAG, "initImgBitmap: preparing bitmaps.");

        imgList.add("https://i.imgur.com/K4Wpnri.png");
        imgList.add("https://i.imgur.com/GEONVpI.png");
        imgList.add("https://i.imgur.com/zdw5f4L.png");
        imgList.add("https://i.imgur.com/86F4qFZ.png");
        imgList.add("https://i.imgur.com/G7eVNSm.png");

        initRecylerView();
    }

    private void initRecylerView(){
        Log.d(TAG, "initRecylerView: init recycler view.");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(this, imgList);
        recyclerView.setAdapter(adapter);
    }
}