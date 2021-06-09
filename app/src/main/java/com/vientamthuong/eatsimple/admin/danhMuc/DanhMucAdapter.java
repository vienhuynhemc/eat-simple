package com.vientamthuong.eatsimple.admin.danhMuc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.WebService;
import com.vientamthuong.eatsimple.admin.dialog.DiaLogConfirm;
import com.vientamthuong.eatsimple.admin.model.DanhMuc;
import com.vientamthuong.eatsimple.admin.session.DataSession;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                // Xóa danh mục
                removeDanhMuc(danhMuc.getMaDanhMuc());
                // Tạo thông báo cá nhân
                addThongBaoCaNhan(danhMuc.getMaDanhMuc());
            });
            diaLogConfirm.show();
        });
        holder.getSua().setOnClickListener(v -> {
            DanhMucSession.getInstance().setDanhMuc(new DanhMuc(danhMuc));
            FragmentTransaction fragmentTransaction = danhMucFragment.getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.f2, new DanhMucEditFragment(), "edit");
            fragmentTransaction.commit();
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

    private void removeDanhMuc(String ma_danh_muc) {
        // Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference root = firebaseDatabase.getReference();
        root.child("danh_muc").child(ma_danh_muc).child("ton_tai").setValue("1");
        Toast.makeText(danhMucFragment.getActivity(), "Xóa thành công: " + ma_danh_muc, Toast.LENGTH_SHORT).show();
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
                params.put("ma_danh_muc", ma_danh_muc);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(danhMucFragment.getActivity()).addRequest(stringRequest);
    }

    private void addThongBaoCaNhan(String ma_danh_muc) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                WebService.lay_ma_thong_bao_ca_nhan_tiep_theo,
                response -> {
                    JSONArray jsonArray = null;
                    try {
                        String ma_thong_bao_ca_nhan = null;
                        jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ma_thong_bao_ca_nhan = "ma_thong_bao_ca_nhan_" + jsonObject.getString("ma_thong_bao_ca_nhan");
                            break;
                        }
                        System.out.println(ma_thong_bao_ca_nhan + " Ok");
                        // Firebase
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference root = firebaseDatabase.getReference();
                        DateTime nowDate = new DateTime();
                        Map<String, Object> map = new HashMap<>();
                        map.put("nv_thuc_hien", DataSession.getInstance().getMa_tai_khoan());
                        map.put("nv_nhan", "");
                        map.put("ngay_tao", nowDate.toString());
                        map.put("type", "0");
                        map.put("noi_dung", "vừa xóa một danh mục có ID là");
                        map.put("noi_dung_quan_trong", "#" + ma_danh_muc);
                        root.child("thong_bao_ca_nhan").child(ma_thong_bao_ca_nhan).setValue(map);
                        // Webservice
                        addNewThongBaoCaNhanWebService(ma_thong_bao_ca_nhan, nowDate, ma_danh_muc);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            System.out.println(error.toString());
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(danhMucFragment.getActivity()).addRequest(stringRequest);
    }

    private void addNewThongBaoCaNhanWebService(String ma_thong_bao_ca_nhan, DateTime ngay_tao, String ma_danh_muc) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.them_mot_thong_bao_ca_nhan_moi,
                response -> {
                }, error -> {
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ma_thong_bao_ca_nhan", ma_thong_bao_ca_nhan);
                params.put("nv_thuc_hien", DataSession.getInstance().getMa_tai_khoan());
                params.put("nv_nhan", "");
                params.put("ngay_tao", ngay_tao.toString());
                params.put("type", "0");
                params.put("noi_dung", "vừa xóa một danh mục có ID là");
                params.put("noi_dung_quan_trong", "#" + ma_danh_muc);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(danhMucFragment.getActivity()).addRequest(stringRequest);
    }

}
