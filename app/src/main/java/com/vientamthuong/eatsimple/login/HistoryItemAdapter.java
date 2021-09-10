package com.vientamthuong.eatsimple.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.order.Order;
import com.vientamthuong.eatsimple.order.OrderItem;

import java.util.ArrayList;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HostoryViewHolder> {

    private Context context;
    private ArrayList<OrderItem> list;

    public HistoryItemAdapter(Context context, ArrayList<OrderItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HostoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_fragment_hisory_item_order,parent,false);


        return new HostoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostoryViewHolder holder, int position) {
        OrderItem order = list.get(position);

        holder.number.setText("Số lượng: "+order.getSo_luong());
        holder.name.setText(order.getTen_sp());
        if(order.getGia_tri() > 0){
            holder.priceSale.setText(order.getTien()+" VNĐ");
        }
        else{
            holder.priceSale.setText("");
        }
        holder.price.setText(order.getTien()+" VNĐ");
        Glide.with(context).load(order.getUrl()).into(holder.url);
        holder.size.setText("Size: "+ order.getMa_size());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HostoryViewHolder extends RecyclerView.ViewHolder{

        ImageView url;
        TextView name,size,priceSale,price,number;

        public HostoryViewHolder(@NonNull View itemView) {
            super(itemView);

            url = itemView.findViewById(R.id.activity_profile_history_img);
            name = itemView.findViewById(R.id.activity_profile_history_name);
            size = itemView.findViewById(R.id.activity_profile_history_size);
            priceSale = itemView.findViewById(R.id.activity_profile_history_priceSale);
            price = itemView.findViewById(R.id.activity_profile_history_price);
            number = itemView.findViewById(R.id.activity_profile_history_number_dish);

        }
    }

}
