package com.vientamthuong.eatsimple.loadProductByID;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.detail.Activity_detail;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoadProductViewAdapter extends RecyclerView.Adapter<LoadProductViewHolder> implements Filterable {

    private List<Product> list;
    private List<Product> listOld;

    public LoadProductViewAdapter(List<Product> list) {

        this.list = list;
        this.listOld = list;
    }

    @NonNull
    @NotNull
    @Override
    public LoadProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_product_1,parent,false);

        LoadProductViewHolder loadProductViewHolder = new LoadProductViewHolder(view);

        loadProductViewHolder.getCardView().setOnClickListener(v -> {

            if (list.get(loadProductViewHolder.getAdapterPosition()).getBitmap() != null){
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

                product.setUrl(p.getUrl());

                intent.putExtra("product",(Serializable) product );

                Bitmap bitmap = p.getBitmap();

                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                byte[] bytes = baos.toByteArray();
                intent.putExtra("bitmap",bytes);

                v.getContext().startActivity(intent);
            }else {
                Toast.makeText(v.getContext(), "Từ từ thôi bạn", Toast.LENGTH_SHORT).show();
            }



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
//            GetSoSao.getData(holder.getCardView().getContext(), product.getMa_sp(), holder.getSo_sao(),this);

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

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String search = constraint.toString();

                if (search.isEmpty()){
                    list = listOld;

                }else {
                    List<Product> list1 = new ArrayList<>();

                    for (Product p : listOld){

                       try {
                           int gia = Integer.parseInt(search);
                           if (p.getGia_km() < gia){
                               list1.add(p);
                           }
                       }catch (Exception e){
                           if (p.getTen_sp().toLowerCase().contains(search.toLowerCase())){
                               list1.add(p);
                           }
                       }
                    }
                    list = list1;
                }
                System.out.println("FILTER: " + list.size());
                FilterResults results = new FilterResults();
                results.values = list;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
