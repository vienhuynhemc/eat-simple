package com.vientamthuong.eatsimple.thanh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ItemRVAdapter extends RecyclerView.Adapter<ItemRVAdapter.ItemRVHolder> {

    private ArrayList<ItemRVModel> itemRVModels;
    private int row = -1;

    public ItemRVAdapter(ArrayList<ItemRVModel> itemRVHolders) {
        this.itemRVModels = itemRVHolders;
    }

    @NonNull
    @Override
    public ItemRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item,parent,false);

        ItemRVHolder itemRVHolder = new ItemRVHolder(view);

        return itemRVHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRVHolder holder, int position) {

        ItemRVModel itemRVModel = itemRVModels.get(position);
        holder.imageView.setImageResource(itemRVModel.getImage());
        holder.textView.setText(itemRVModel.getText());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row = position;
                notifyDataSetChanged();
            }
        });

        if (position == row){
            holder.linearLayout.setBackgroundResource(R.drawable.item_bg);
        }else{
            holder.linearLayout.setBackgroundResource(R.drawable.rv_layout);
        }

    }

    @Override
    public int getItemCount() {
        return itemRVModels.size();
    }

    public static class ItemRVHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageView;
        private LinearLayout linearLayout;

        public ItemRVHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_food);
            textView = itemView.findViewById(R.id.name_food);
            linearLayout = itemView.findViewById(R.id.item_linearLayout);

        }
    }
}
