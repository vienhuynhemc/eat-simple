package com.vientamthuong.eatsimple.admin.notification;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.admin.model.ThongBaoChuong;

import java.util.List;


public class ThongBaoNoiFragmentCustomAdapter extends RecyclerView.Adapter<ThongBaoNoiFragmentCustomViewHolder> {

    private int[] resource;
    private final List<ThongBaoChuong> thongBaoChuongs;

    public ThongBaoNoiFragmentCustomAdapter(int[] resources, List<ThongBaoChuong> thongBaoChuongs) {
        this.resource = resources;
        this.thongBaoChuongs = thongBaoChuongs;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == thongBaoChuongs.size() - 1) return 1;
        else return 0;
    }

    @NonNull
    @Override
    public ThongBaoNoiFragmentCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThongBaoNoiFragmentCustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(resource[viewType], parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ThongBaoNoiFragmentCustomViewHolder holder, int position) {
        ThongBaoChuong thongBaoChuong = thongBaoChuongs.get(position);
        if (thongBaoChuong.getNgay_tao() != null) {
            holder.updateNgayTao(thongBaoChuong.getNgay_tao());
        }
        if(thongBaoChuong.getHinh_nguoi_gui()!= null){
            holder.updateHinh(thongBaoChuong.getHinh_nguoi_gui());
        }
        if(thongBaoChuong.getTen_nguoi_gui()!= null && thongBaoChuong.getNoi_dung()!= null){
            holder.updateTen(thongBaoChuong.getTen_nguoi_gui(),thongBaoChuong.getNoi_dung());
        }
    }

    @Override
    public int getItemCount() {
        return thongBaoChuongs.size();
    }
}
