package com.vientamthuong.eatsimple.checkout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Cart;

import org.jetbrains.annotations.NotNull;

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
