package com.vientamthuong.eatsimple.cartPage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Cart> list;
    private ViewBinderHelper viewBinderHelper;
    private TextView tv_product,tv_price;
    private UpdateCart updateCart;

    public CartAdapter(List<Cart> list,TextView tv_prouct,TextView tv_price) {
        this.list = list;
        this.tv_price = tv_price;
        this.tv_product = tv_prouct;
        updateCart = new UpdateCart();

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

        Cart cartModel = list.get(position);

        viewBinderHelper = new ViewBinderHelper();

        viewBinderHelper.bind(holder.swipeRevealLayout,cartModel.getTen_sp());


//        if (holder.checkBox.isChecked()){
//            int total_product = Integer.parseInt(tv_product.getText().toString());
//            int total_price = Integer.parseInt(tv_price.getText().toString());
//            tv_product.setText(String.valueOf(total_product + cartModel.getSo_luong()));
//            tv_price.setText(String.valueOf(total_price + cartModel.getGia_km()*cartModel.getSo_luong()));
//        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int num = Integer.parseInt(String.valueOf(holder.number.getText()));
                if (isChecked){
                    int total_product = Integer.parseInt(tv_product.getText().toString());
                    int total_price = Integer.parseInt(tv_price.getText().toString());
                    tv_product.setText(String.valueOf(total_product + num));
                    tv_price.setText(String.valueOf(total_price + cartModel.getGia_km()*num));
                }else {
                    int total_product = Integer.parseInt(tv_product.getText().toString());
                    int total_price = Integer.parseInt(tv_price.getText().toString());
                    tv_product.setText(String.valueOf(total_product - num));
                    tv_price.setText(String.valueOf(total_price - cartModel.getGia_km()*num));
                }

            }

        });


//        holder.image.setImageResource(cartModel.getImage());
        holder.title.setText(cartModel.getTen_sp());
        holder.content.setText("Size: "+cartModel.getSizes().getTen_size() + " Còn lại: " + cartModel.getSo_luong_con_lai());
        holder.price.setText(cartModel.getGia_km()+"đ");
        holder.number.setText(cartModel.getSo_luong()+"");

        if (cartModel.getBitmap() == null && !cartModel.isLoadImg()){
            GetCart.getBitmapImage(cartModel,holder.cardView.getContext(),this);
        }else  if (cartModel.getBitmap() != null && cartModel.isLoadImg()){
            holder.image.setImageBitmap(cartModel.getBitmap());
        }

        holder.btn_dcre.setOnClickListener(v -> {
            int num = Integer.parseInt(String.valueOf(holder.number.getText())) - 1;
            if (num >= 1 && num <= cartModel.getSo_luong_con_lai()){
                holder.number.setText(num+"");
                if (holder.checkBox.isChecked()){
                    tv_product.setText(Integer.parseInt(tv_product.getText().toString()) - 1 +"");
                    tv_price.setText(Integer.parseInt(tv_price.getText().toString()) - cartModel.getGia_km()+"");
                }
            }

          updateCart.deleteCart(v.getContext(),list.get(holder.getAdapterPosition()).getMa_sp(),list.get(holder.getAdapterPosition()).getSizes().getMa_size(),0);

        });
        holder.btn_incre.setOnClickListener(v -> {

            int num = Integer.parseInt(String.valueOf(holder.number.getText())) + 1;
            if ( num <= cartModel.getSo_luong_con_lai()){
                holder.number.setText(num+"");
                if (holder.checkBox.isChecked()){
                    tv_product.setText(Integer.parseInt(tv_product.getText().toString()) + 1 + "");
                    tv_price.setText(Integer.parseInt(tv_price.getText().toString()) + cartModel.getGia_km()+"");
                }
            }

            updateCart.deleteCart(v.getContext(),list.get(holder.getAdapterPosition()).getMa_sp(),list.get(holder.getAdapterPosition()).getSizes().getMa_size(),1);
        });


        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 101){
                            Bundle bundle = msg.getData();
                            String re = bundle.getString("Result");

                            if (re.equals("SUCCESS")){
                                list.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                            }else {
                                Toast.makeText(v.getContext(), "Lỗi vui lòng thử lại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                };
                DeleteCart deleteCart = new DeleteCart();
                deleteCart.deleteCart(handler,v.getContext(), cartModel.getMa_sp(), cartModel.getSizes().getMa_size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
