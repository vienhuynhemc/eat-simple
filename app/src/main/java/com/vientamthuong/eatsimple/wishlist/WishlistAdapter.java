package com.vientamthuong.eatsimple.wishlist;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.vientamthuong.eatsimple.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.WishlistViewHoler> {

    Set<String> checkboxes = new HashSet<>();

    private Context context;
    private ArrayList<Wishlist> products;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public WishlistAdapter(Context context, ArrayList<Wishlist> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public WishlistViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_wishlist_item,parent,false);

        return new WishlistViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHoler holder, int position) {
        Wishlist w = products.get(position);
        if(w == null){
            return;
        }
        viewBinderHelper.bind(holder.swipeRevealLayout,w.getName());
        holder.txtName.setText(w.getName());

//        viewBinderHelper.bind(holder.swipeRevealLayout,w.getDesP());
//        holder.txtDes.setText(w.getDesP());

        viewBinderHelper.bind(holder.swipeRevealLayout,String.valueOf(w.getPriceP()));
        holder.txtPrice.setText(w.getPriceP()+" VNĐ");

        viewBinderHelper.bind(holder.swipeRevealLayout,String.valueOf(w.getImg()));
        Glide.with(context).load(w.getImg()).into(holder.img);

        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(products != null) {
            return products.size();
        }
        return 0;
    }

    public class WishlistViewHoler extends RecyclerView.ViewHolder{

        private SwipeRevealLayout swipeRevealLayout;
        private LinearLayout layoutDelete;
        private ImageView img;
        private TextView txtName, txtPrice;
        private CardView btnAdd;
        private CheckBox cbAdd;

        private boolean isCheck = false;

        ShimmerFrameLayout shimmer;

        Handler handler = new Handler();


        public WishlistViewHoler(@NonNull View itemView) {
            super(itemView);

            swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);
            layoutDelete = itemView.findViewById(R.id.swipDelete);
            btnAdd = itemView.findViewById(R.id.btnAddCart);
            cbAdd = itemView.findViewById(R.id.checkboxAdd);

            img = itemView.findViewById(R.id.imgP);
            txtName = itemView.findViewById(R.id.nameP);
            txtPrice = itemView.findViewById(R.id.priceP);


//            shimmer = itemView.findViewById(R.id.shimmer);
//
//
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    shimmer.stopShimmer();
//                    shimmer.hideShimmer();
//                    shimmer.setVisibility(View.GONE);
//
//
//
//                    txtName.setVisibility(View.VISIBLE);
//                    txtPrice.setVisibility(View.VISIBLE);
//                    txtDes.setVisibility(View.VISIBLE);
//                    img.setVisibility(View.VISIBLE);
//                    cbAdd.setVisibility(View.VISIBLE);
//                    btnAdd.setVisibility(View.VISIBLE);
//
//                }
//            },5000);


//            Animation animation = AnimationUtils.loadAnimation(context,R.anim.animation_item);
//            itemView.startAnimation(animation);

           btnAdd.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Toast.makeText(context, "Thêm vào giở hàng thành công!", Toast.LENGTH_LONG).show();
               }
           });

           cbAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   isCheck = isChecked;
               }
           });

           cbAdd.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   checkboxes.add(txtName.getText().toString());
                   if(isCheck) {
                       if (checkboxes.size() > 0) {
                           Toast.makeText(context, checkboxes.toString(), Toast.LENGTH_LONG).show();
                       }
                   }
                   else{
                       checkboxes.remove(txtName.getText().toString());
                       if (checkboxes.size() > 0) {
                           Toast.makeText(context, checkboxes.toString(), Toast.LENGTH_LONG).show();
                       }
                   }
               }
           });

        }
    }

}
