package com.vientamthuong.eatsimple.admin.donHang;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.model.SanPhamDonHang;
import com.vientamthuong.eatsimple.admin.session.DataSession;

import java.util.List;

public class DonHangAdapter2 extends RecyclerView.Adapter<ViewHolderDonHang2> {

    private List<SanPhamDonHang> list;

    public DonHangAdapter2(List<SanPhamDonHang> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolderDonHang2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderDonHang2(LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_view_holder_item_don_hang_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDonHang2 holder, int position) {
        SanPhamDonHang sanPhamDonHang= list.get(position);
        holder.getHinh_sp().setImageBitmap(sanPhamDonHang.getHinh());
        holder.getTen_sp().setText(sanPhamDonHang.getTen_sp());
        holder.getMa_sp().setText("#"+sanPhamDonHang.getMa_size());
        holder.getTensize().setText("Tên size: "+ sanPhamDonHang.getTen_sp());
        holder.getNoi_dung_2().setText("Giá: "+sanPhamDonHang.getTien());
        holder.getNoi_dung_1().setText("Số lượng: "+sanPhamDonHang.getSo_luong());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
