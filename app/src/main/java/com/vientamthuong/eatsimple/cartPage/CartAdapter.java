package com.vientamthuong.eatsimple.cartPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.vientamthuong.eatsimple.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<CartModel> list;
    private ViewBinderHelper viewBinderHelper;

    public CartAdapter(List<CartModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart_one_item,parent,false);

        Animation animation = AnimationUtils.loadAnimation(parent.getContext(),R.anim.activity_cart_load_item);
        view.startAnimation(animation);

        CartViewHolder cartViewHolder = new CartViewHolder(view);

        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        CartModel cartModel = list.get(position);

        viewBinderHelper = new ViewBinderHelper();

        viewBinderHelper.bind(holder.swipeRevealLayout,cartModel.getTitle());

        holder.image.setImageResource(cartModel.getImage());
        holder.title.setText(cartModel.getTitle());
        holder.content.setText(cartModel.getContent());
        holder.price.setText(cartModel.getPrice()+"");
        holder.number.setText(cartModel.getNumber()+"");


        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
