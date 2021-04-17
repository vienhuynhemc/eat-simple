package com.vientamthuong.eatsimple;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.vientamthuong.eatsimple.thanh.DynamicAdapter;
import com.vientamthuong.eatsimple.thanh.DynamicRVModel;
import com.vientamthuong.eatsimple.thanh.ItemRVAdapter;
import com.vientamthuong.eatsimple.thanh.ItemRVModel;
import com.vientamthuong.eatsimple.thanh.LoadMore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemRVAdapter itemRVAdapter;

    List<DynamicRVModel> items = new ArrayList<>();
    DynamicAdapter dynamicAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_main);

        ArrayList<ItemRVModel> models = new ArrayList<>();

        models.add(new ItemRVModel(R.drawable.activity_introductory_icon,"Món 2"));
        models.add(new ItemRVModel(R.drawable.activity_introductory_icon,"Món 3"));
        models.add(new ItemRVModel(R.drawable.activity_introductory_icon,"Món 4"));
        models.add(new ItemRVModel(R.drawable.activity_introductory_icon,"Món 5"));
        models.add(new ItemRVModel(R.drawable.activity_introductory_icon,"Món 6"));

        recyclerView = findViewById(R.id.rv_1);
        itemRVAdapter = new ItemRVAdapter(models);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(itemRVAdapter);

        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));
        items.add(new DynamicRVModel("Hamberger"));

        RecyclerView drv = findViewById(R.id.rv_2);
        drv.setLayoutManager(new LinearLayoutManager(this));
        dynamicAdapter = new DynamicAdapter(drv,this,items);
        drv.setAdapter(dynamicAdapter);

        dynamicAdapter.setLoadMore(new LoadMore() {
            @Override
            public void onLoadMore() {
               if (items.size() <= 10){
                   items.add(null);
                   dynamicAdapter.notifyItemChanged(items.size()-1);
                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           items.remove(items.size()-1);
                           dynamicAdapter.notifyItemRemoved(items.size());
                       int index = items.size();
                    int end = index + 10;
                       for (int i = index;1<end;i++){
                           String name = UUID.randomUUID().toString();
                           DynamicRVModel item = new DynamicRVModel(name);
                           items.add(item);
                       }
                       dynamicAdapter.notifyDataSetChanged();
                       dynamicAdapter.setLoaded();

                       }
                   },4000);

               }
               else
                   Toast.makeText(DashboardActivity.this, "Đã load thành công", Toast.LENGTH_SHORT).show();
            }
        });


    }
}