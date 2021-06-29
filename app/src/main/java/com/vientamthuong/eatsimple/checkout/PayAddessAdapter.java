package com.vientamthuong.eatsimple.checkout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Address;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class PayAddessAdapter extends RecyclerView.Adapter<PayAddressHolder> {

    List<Address> list;

    public PayAddessAdapter(List<Address> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public PayAddressHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_checkout_one_address,parent,false);

        PayAddressHolder holder = new PayAddressHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PayAddressHolder holder, int position) {

        Address address = list.get(position);

        holder.getTv_name().setText(address.getName());
        holder.getTv_diachi().setText(address.getDiachi());
        holder.getTv_ghichu().setText(address.getGhichu());
        holder.getTv_so_dien_thoai().setText(address.getSodienthoai());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
