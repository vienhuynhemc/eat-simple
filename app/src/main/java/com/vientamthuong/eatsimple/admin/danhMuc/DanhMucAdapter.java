package com.vientamthuong.eatsimple.admin.danhMuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.model.DanhMuc;

import java.util.List;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucViewHolder> {

    private int resource;
    private List<DanhMuc> show;
    private List<DanhMuc> root;
    private Context context;

    public DanhMucAdapter(int resource, List<DanhMuc> show, List<DanhMuc> root, Context context) {
        this.resource = resource;
        this.show = show;
        this.root = root;
        this.context = context;
    }

    @NonNull
    @Override
    public DanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DanhMucViewHolder(LayoutInflater.from(parent.getContext()).inflate(resource, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucViewHolder holder, int position) {
        DanhMuc danhMuc = show.get(position);
        holder.getChonXoa().setChecked(danhMuc.isChonXoa());
        holder.getChonXoa().setOnClickListener(v -> {
            danhMuc.setChonXoa(!danhMuc.isChonXoa());
            if (danhMuc.isChonXoa()) {
                holder.getChonXoa().setButtonTintList(context.getColorStateList(R.color.color_admin_main));
            } else {
                holder.getChonXoa().setButtonTintList(context.getColorStateList(R.color.color_admin_xam));
            }
//            for (DanhMuc d : root) {
//                if (d.getMaDanhMuc().equals(danhMuc.getMaDanhMuc())) {
//                    d.setChonXoa(danhMuc.isChonXoa());
//                    break;
//                }
//            }
        });
    }

    @Override
    public int getItemCount() {
        return show.size();
    }

}
