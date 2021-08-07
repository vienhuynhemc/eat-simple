package com.vientamthuong.eatsimple.checkout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Address;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class PayAddessAdapter extends RecyclerView.Adapter<PayAddressHolder> {

   private List<Address> list;
    private RecyclerView reDiaChi;
    private List<View> views;
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

        if (position == 0){
            holder.getCheckBox().setChecked(true);
        }

        holder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    System.out.println("Check0" );
                    for (int i = 0; i <reDiaChi.getChildCount(); i++) {
                        View view = reDiaChi.getChildAt(i);
                        CheckBox checkBox1 = view.findViewById(R.id.checkbox_cart);
                        System.out.println("Check1:" + i +" SIZE:" + reDiaChi.getChildCount());
                        if (checkBox1 != holder.getCheckBox() && checkBox1.isChecked()) {
                            checkBox1.setChecked(false);
                            System.out.println("Check2" );
                            return;
                        }else {
                            System.out.println("CHECK =");
                        }
                    }

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public RecyclerView getReDiaChi() {
        return reDiaChi;
    }

    public void setReDiaChi(RecyclerView reDiaChi) {
        this.reDiaChi = reDiaChi;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }
}
