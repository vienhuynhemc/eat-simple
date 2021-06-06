package com.vientamthuong.eatsimple.admin.danhMuc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vientamthuong.eatsimple.admin.model.DanhMuc;

import java.util.List;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucViewHolder> {

    private int resource;
    private List<DanhMuc> show;
    private List<DanhMuc> root;
    private Context context;
    private FloatingActionButton floatingActionButton;

    public DanhMucAdapter(int resource, List<DanhMuc> show, List<DanhMuc> root, Context context, FloatingActionButton floatingActionButton) {
        this.resource = resource;
        this.show = show;
        this.root = root;
        this.context = context;
        this.floatingActionButton = floatingActionButton;
    }

    @NonNull
    @Override
    public DanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DanhMucViewHolder(LayoutInflater.from(parent.getContext()).inflate(resource, parent, false));
    }

    private void updateButtonXoa() {
        int count = 0;
        for (DanhMuc danhMuc : show) {
            if (danhMuc.isChonXoa()) {
                count++;
                break;
            }
        }
        if (count != 0) {
            floatingActionButton.setVisibility(View.VISIBLE);
        } else {
            floatingActionButton.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DanhMucViewHolder holder, int position) {
        DanhMuc danhMuc = show.get(position);
        holder.getChonXoa().setChecked(danhMuc.isChonXoa());
        holder.getChonXoa().setOnClickListener(v -> {
            danhMuc.setChonXoa(!danhMuc.isChonXoa());
            for (DanhMuc d : root) {
                if (d.getMaDanhMuc().equals(danhMuc.getMaDanhMuc())) {
                    d.setChonXoa(danhMuc.isChonXoa());
                    updateButtonXoa();
                    break;
                }
            }
        });
        if (danhMuc.getMaDanhMuc() != null) {
            holder.getMaDanhMuc().setText("#" + danhMuc.getMaDanhMuc());
            holder.updateMaDanhMuc();
        }
        if (danhMuc.getTenDanhMuc() != null) {
            holder.getTenDanhMuc().setText(danhMuc.getTenDanhMuc());
            holder.updateTenDanhMuc();
        }
        if (danhMuc.getNgayTao() != null) {
            holder.getNgayTao().setText(danhMuc.getNgayTao().toStringDateTypeNumberStringNumber());
            holder.updateNgayTao();
        }
        if (danhMuc.getHinh() != null) {
            holder.getHinhDaiDien().setImageBitmap(danhMuc.getHinh());
            holder.updateHinhDaiDien();
        }
        if (danhMuc.getSoSanPham() != -1) {
            holder.getThongTin().setText("Số lượng sản phẩm: " + danhMuc.getSoSanPham());
            holder.updateSl();
        }
    }

    @Override
    public int getItemCount() {
        return show.size();
    }

}
