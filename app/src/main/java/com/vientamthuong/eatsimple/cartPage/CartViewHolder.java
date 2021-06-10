package com.vientamthuong.eatsimple.cartPage;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.vientamthuong.eatsimple.R;


public class CartViewHolder extends RecyclerView.ViewHolder {

     ImageView image;
     TextView title;
     TextView content;
     TextView price;
     TextView number;
     SwipeRevealLayout swipeRevealLayout;
     Button deleteLayout;
     ShimmerFrameLayout layout;
     Handler handler;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.img_cart_one_item);
        title = itemView.findViewById(R.id.cart_one_item_title);
        content = itemView.findViewById(R.id.cart_one_item_content);
        price = itemView.findViewById(R.id.cart_one_item_price);
        number = itemView.findViewById(R.id.cart_number_item);
        swipeRevealLayout = itemView.findViewById(R.id.swpe_cart_layout);
        deleteLayout = itemView.findViewById(R.id.cart_delete_one_item);
        layout = itemView.findViewById(R.id.shimmer_layout);
        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.stopShimmer();
                layout.hideShimmer();
                layout.setVisibility(View.GONE);

                image.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);
                price.setVisibility(View.VISIBLE);
                number.setVisibility(View.VISIBLE);

            }
        },10000);


    }

}
