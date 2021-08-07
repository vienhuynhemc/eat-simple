package com.vientamthuong.eatsimple.checkout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Cart;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.detail.Activity_detail;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.zip.Inflater;

public class PayProductAdapter extends RecyclerView.Adapter<PayProductHolder> {

    private List<Cart> list;

    public PayProductAdapter(List<Cart> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public PayProductHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pay_one_item_product,parent,false);

        PayProductHolder holder = new PayProductHolder(view);


        holder.getLayout().setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), Activity_detail.class);

            Cart p = list.get(holder.getAdapterPosition());

            Product product = new Product();
            product.setMa_sp(p.getMa_sp());
            product.setTen_sp(p.getTen_sp());
            product.setGia_km(p.getGia_km());
            product.setGia(p.getGia());
            product.setSo_luong_con_lai(p.getSo_luong_con_lai());
            product.setSo_luong_ban_ra(p.getSo_luong_ban_ra());
            product.setKcal(p.getKcal());
            product.setThoi_gian_nau(p.getThoi_gian_nau());
            product.setThong_tin(p.getThong_tin());
            product.setUrl(p.getUrl());

            intent.putExtra("product",(Serializable) product );

          // Bitmap bitmap = p.getBitmap();

//            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
//            byte[] bytes = baos.toByteArray();
            intent.putExtra("bitmap",p.getBytes());
            v.getContext().startActivity(intent);

        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PayProductHolder holder, int position) {

    Cart cart = list.get(position);
    byte[] bitmaps = cart.getBytes();
    Bitmap bitmap = BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length);
    holder.getImg().setImageBitmap(bitmap);

    holder.getTextView().setText(cart.getTen_sp()+" S: " + cart.getSizes().getTen_size());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
