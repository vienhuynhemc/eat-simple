package com.vientamthuong.eatsimple.wishlist;

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

import java.util.ArrayList;

public class ItemInCheckBoxAdapter extends RecyclerView.Adapter<ItemInCheckBoxAdapter.ItemInCheckBoxWishlist> {

    private ArrayList<Wishlist> products = new ArrayList<>();
    private Context context;

    public ItemInCheckBoxAdapter(ArrayList<Wishlist> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemInCheckBoxWishlist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_wishlist_item_incheckbox,parent,false);
        return new ItemInCheckBoxWishlist(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemInCheckBoxWishlist holder, int position) {
        Wishlist w = products.get(position);
        if(w == null){
            return;
        }
        Glide.with(context).load(w.getImg()).into(holder.imgItem);
        holder.txtName.setText(w.getName());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    public class ItemInCheckBoxWishlist extends RecyclerView.ViewHolder{

        private ImageView imgItem;
        private TextView txtName;

        public ItemInCheckBoxWishlist(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.img_wishlit_checkbox);
            txtName = itemView.findViewById(R.id.name_item_checkbox);

        }
    }
}
