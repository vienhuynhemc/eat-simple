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
        if (position == 0) return resource[0];
        else if (position == danhMucs.size() - 1) return resource[2];
        else return resource[1];
    }

    @NonNull
    @Override
    public CustomDanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomDanhMucViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomDanhMucViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return danhMucs.size();
    }
}
