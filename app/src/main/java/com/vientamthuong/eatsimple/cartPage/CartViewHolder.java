package com.vientamthuong.eatsimple.cartPage;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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
     Button deleteLayout,btn_incre,btn_dcre;
     CheckBox checkBox;
     CardView cardView;
     LottieAnimationView img;
     ConstraintLayout constraintLayout;
//     ShimmerFrameLayout layout;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.img_cart_one_item);
        title = itemView.findViewById(R.id.cart_one_item_title);
        content = itemView.findViewById(R.id.cart_one_item_content);
        price = itemView.findViewById(R.id.cart_one_item_price);
        number = itemView.findViewById(R.id.cart_number_item);
        swipeRevealLayout = itemView.findViewById(R.id.swpe_cart_layout);
        deleteLayout = itemView.findViewById(R.id.cart_delete_one_item);
        btn_dcre = itemView.findViewById(R.id.cart_number_decrease_item);
        btn_incre = itemView.findViewById(R.id.cart_number_increase_item);
        checkBox = itemView.findViewById(R.id.checkbox_cart);
        cardView = itemView.findViewById(R.id.cardview_one_item);
        img =itemView.findViewById(R.id.img_cart_one_item);
        constraintLayout = itemView.findViewById(R.id.constraintLayout);



    }

}
