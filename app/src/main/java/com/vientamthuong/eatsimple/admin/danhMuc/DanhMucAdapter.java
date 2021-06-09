package com.vientamthuong.eatsimple.admin.danhMuc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vientamthuong.eatsimple.admin.WebService;
import com.vientamthuong.eatsimple.admin.dialog.DiaLogConfirm;
import com.vientamthuong.eatsimple.admin.model.DanhMuc;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucViewHolder> {

    private int resource;
    private List<DanhMuc> show;
    private List<DanhMuc> root;
    private Context context;
    private DanhMucFragment danhMucFragment;

    public DanhMucAdapter(int resource, List<DanhMuc> show, List<DanhMuc> root, DanhMucFragment danhMucFragment) {
        this.resource = resource;
        this.show = show;
        this.root = root;
        this.danhMucFragment = danhMucFragment;
    }

    @NonNull
    @Override
    public DanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DanhMucViewHolder(LayoutInflater.from(parent.getContext()).inflate(resource, parent, false));
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
                    danhMucFragment.updateButtonXoa();
                    break;
                }
            }
        });
        holder.getXoa().setOnClickListener(v -> {
            DiaLogConfirm diaLogConfirm = new DiaLogConfirm(danhMucFragment.getActivity());
            diaLogConfirm.getTextViewTitle().setText("Xóa một danh mục");
            diaLogConfirm.getTextViewContent().setText("Bạn có chắc chắn rằng mình muốn xóa danh mục có ID: " + danhMuc.getMaDanhMuc() + " không?");
            diaLogConfirm.getBtTry().setText("Không");
            diaLogConfirm.getBtIgnore().setText("Xóa");
            diaLogConfirm.getBtTry().setOnClickListener(v1 -> diaLogConfirm.dismiss());
            diaLogConfirm.getBtIgnore().setOnClickListener(v1 -> {
                diaLogConfirm.dismiss();
                // Firebase
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference root = firebaseDatabase.getReference();
                root.child("danh_muc").child(danhMuc.getMaDanhMuc()).child("ton_tai").setValue("1");
                Toast.makeText(danhMucFragment.getActivity(), "Xóa thành công: " + danhMuc.getMaDanhMuc(), Toast.LENGTH_SHORT).show();
                // Webservice
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        WebService.xoa_mot_danh_muc,
                        response -> {
                        }, error -> {
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("ma_danh_muc", danhMuc.getMaDanhMuc());
                        return params;
                    }
                };
                VolleyPool.getInstance(danhMucFragment.getActivity()).addRequest(stringRequest);
            });
            diaLogConfirm.show();
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
