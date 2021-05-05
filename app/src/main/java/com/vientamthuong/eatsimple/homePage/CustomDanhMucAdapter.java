package com.vientamthuong.eatsimple.homePage;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.danhMuc.DanhMuc;

import java.util.List;

public class CustomDanhMucAdapter extends RecyclerView.Adapter<CustomDanhMucViewHolder> {

    private int[] resource;
    private final List<DanhMuc> danhMucs;

    public CustomDanhMucAdapter(int[] resources, List<DanhMuc> danhMucs) {
        this.resource = resources;
        this.danhMucs = danhMucs;
    }

    @Override
    public int getItemViewType(int position) {
        int add = 0;
        if (!danhMucs.get(position).isLoaded()) {
            add = 3;
        }
        if (position == 0) return resource[add];
        else if (position == danhMucs.size() - 1) return resource[2 + add];
        else return resource[1 + add];
    }

    @NonNull
    @Override
    public CustomDanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomDanhMucViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomDanhMucViewHolder holder, int position) {
        DanhMuc danhMuc = danhMucs.get(position);
        if (danhMuc.isLoaded()) {
            holder.getTextView().setText(danhMuc.getTen_danh_muc());
            if (danhMuc.getBitmap() != null) {
                holder.getImageView().setImageBitmap(danhMuc.getBitmap());
            }
        }
    }

    @Override
    public int getItemCount() {
        return danhMucs.size();
    }
}
