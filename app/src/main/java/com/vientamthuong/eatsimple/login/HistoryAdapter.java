package com.vientamthuong.eatsimple.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.detail.RecyclerviewAdapter;
import com.vientamthuong.eatsimple.order.Order;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HostoryViewHolder> {

    private Context context;
    private ArrayList<Order> list;

    public HistoryAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HostoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_profile_hostory_item,parent,false);


        return new HostoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostoryViewHolder holder, int position) {
        Order order = list.get(position);

        holder.total.setText(order.getTong_tien()+" VNĐ");
        holder.time.setText("Thời gian: "+order.getNgay_tao().getDay()+" tháng "+order.getNgay_tao().getMonth()+" năm "+order.getNgay_tao().getYear());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(linearLayoutManager);

        HistoryItemAdapter adapter = new HistoryItemAdapter(context,order.getItem());
        System.out.println(order.getItem().toString());
        holder.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HostoryViewHolder extends RecyclerView.ViewHolder{

        TextView total,time;
        RecyclerView recyclerView;

        public HostoryViewHolder(@NonNull View itemView) {
            super(itemView);

            total = itemView.findViewById(R.id.activity_profile_history_total_p);
            time = itemView.findViewById(R.id.activity_profile_history_time);

            recyclerView = itemView.findViewById(R.id.activity_profile_hostory_recyler);


        }
    }

}
