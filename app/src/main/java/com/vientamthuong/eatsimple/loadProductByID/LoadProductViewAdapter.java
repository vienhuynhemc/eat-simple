package com.vientamthuong.eatsimple.loadProductByID;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.detail.Activity_detail;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

public class LoadProductViewAdapter extends RecyclerView.Adapter<LoadProductViewHolder> {

    private List<Product> list;

    public LoadProductViewAdapter(List<Product> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public LoadProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_product,parent,false);

        LoadProductViewHolder loadProductViewHolder = new LoadProductViewHolder(view);

        loadProductViewHolder.getLayout().setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), Activity_detail.class);

            Product p = list.get(loadProductViewHolder.getAdapterPosition());

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

            intent.putExtra("product",(Serializable) product );

            Bitmap bitmap = p.getBitmap();

            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
            byte[] bytes = baos.toByteArray();
            intent.putExtra("bitmap",bytes);

            v.getContext().startActivity(intent);

        });

        return loadProductViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LoadProductViewHolder holder, int position) {
        if (list.size() != 0){
            Product product = list.get(position);
            holder.getTitle().setText(product.getTen_sp());
            holder.getGia().setText(String.valueOf(product.getGia()) + "đ");
            holder.getGia_km().setText(String.valueOf(product.getGia_km()) +"đ");
            holder.getGia().setPaintFlags(holder.getGia().getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if (product.getBitmap() != null){
                holder.getImg().setImageBitmap(product.getBitmap());
            }else if (product.getBitmap() == null && !product.isLoadImg()) {
               GetListProduct.getBitmapImage(product,holder.getCardView().getContext(), this);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
