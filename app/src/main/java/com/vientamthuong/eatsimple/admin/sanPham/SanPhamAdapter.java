package com.vientamthuong.eatsimple.admin.sanPham;

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
import com.vientamthuong.eatsimple.admin.configuration.WebService;
import com.vientamthuong.eatsimple.admin.danhMuc.DanhMucEditFragment;
import com.vientamthuong.eatsimple.admin.danhMuc.DanhMucFragment;
import com.vientamthuong.eatsimple.admin.danhMuc.DanhMucSession;
import com.vientamthuong.eatsimple.admin.danhMuc.DanhMucViewHolder;
import com.vientamthuong.eatsimple.admin.dialog.DiaLogConfirm;
import com.vientamthuong.eatsimple.admin.model.DanhMuc;
import com.vientamthuong.eatsimple.admin.model.SanPham;
import com.vientamthuong.eatsimple.admin.session.DataSession;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamViewHolder> {

    private int resource;
    private List<SanPham> show;
    private List<SanPham> root;
    private Context context;
    private SanPhamFragment sanPhamFragment;

    public SanPhamAdapter(int resource, List<SanPham> show, List<SanPham> root, SanPhamFragment sanPhamFragment) {
        this.resource = resource;
        this.show = show;
        this.root = root;
        this.sanPhamFragment = sanPhamFragment;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SanPhamViewHolder(LayoutInflater.from(parent.getContext()).inflate(resource, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = show.get(position);
        holder.getChonXoa().setChecked(sanPham.isChonXoa());
        holder.getChonXoa().setOnClickListener(v -> {
            sanPham.setChonXoa(!sanPham.isChonXoa());
            for (SanPham d : root) {
                if (d.getMaSanPham().equals(sanPham.getMaSanPham())) {
                    d.setChonXoa(sanPham.isChonXoa());
                    sanPhamFragment.updateButtonXoa();
                    break;
                }
            }
        });
        holder.getXoa().setOnClickListener(v -> {
            DiaLogConfirm diaLogConfirm = new DiaLogConfirm(sanPhamFragment.getActivity());
            diaLogConfirm.getTextViewTitle().setText("Xóa một sản phẩm");
            diaLogConfirm.getTextViewContent().setText("Bạn có chắc chắn rằng mình muốn xóa sản phẩm có ID: " + sanPham.getMaSanPham() + " không?");
            diaLogConfirm.getBtTry().setText("Không");
            diaLogConfirm.getBtIgnore().setText("Xóa");
            diaLogConfirm.getBtTry().setOnClickListener(v1 -> diaLogConfirm.dismiss());
            diaLogConfirm.getBtIgnore().setOnClickListener(v1 -> {
                diaLogConfirm.dismiss();
                // Xóa danh mục
                removeDanhMuc(sanPham.getMaSanPham());
                // Tạo thông báo cá nhân
                addThongBaoCaNhan(sanPham.getMaSanPham());
            });
            diaLogConfirm.show();
        });
        holder.getSua().setOnClickListener(v -> {
            SanPhamSession.getInstance().setSanPham(new SanPham(sanPham));
            FragmentTransaction fragmentTransaction = sanPhamFragment.getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.f2, new SanPhamEditFragment(), "edit");
            fragmentTransaction.commit();
        });
        if (sanPham.getMaSanPham() != null) {
            holder.getMaSp().setText("#" + sanPham.getMaSanPham());
            holder.updateSanPham();
        }
        if (sanPham.getTenSanPham() != null) {
            holder.getTenSp().setText(sanPham.getTenSanPham());
            holder.updateTenSp();
        }
        if (sanPham.getNgayTao() != null) {
            holder.getNgayTao().setText(sanPham.getNgayTao().toStringDateTypeNumberStringNumber());
            holder.updateNgayTao();
        }
        if (sanPham.getHinh() != null) {
            holder.getHinhDaiDien().setImageBitmap(sanPham.getHinh());
            holder.updateHinhDaiDien();
        }
        if (sanPham.getDanhMucSanPham() != null) {
            holder.getDm().setText("Danh mục: " + sanPham.getDanhMucSanPham().getTenDanhMuc());
            holder.updateDm();
        }
        if (sanPham.getGia() != -1) {
            String gia = "Giá gốc: " + String.format("%,.0f", (double) sanPham.getGia());
            if (sanPham.getGia_km() != -1) {
                gia += " - Giá Khuyến mãi: " + String.format("%,.0f", (double) sanPham.getGia_km());
            }
            holder.getGia().setText(gia);
            holder.updateGia();
        }
        if (sanPham.getSizes() != null) {
            holder.getSize().setText("Kích cỡ: " + sanPham.getSizeString());
            holder.updateSize();
        }
        if (sanPham.getKcal() != -1) {
            holder.getKcal().setText("Kcal: " + sanPham.getKcal());
            holder.updateKcal();
        }
        if (sanPham.getThoi_gian_nau() != -1) {
            holder.getThoi_gian_nau().setText("Thời gian nấu: " + sanPham.getThoi_gian_nau() + " phút");
            holder.updateThoiGianNau();
        }
        if (sanPham.getSo_luong_ban_ra() != -1) {
            holder.getSo_luong_ban_ra().setText("Số lượng đã bán: " + sanPham.getSo_luong_ban_ra());
            holder.updateSoLuongBanRa();
        }
        if (sanPham.getSoLuongSanPham() != -1) {
            holder.getSo_luong_san_pham().setText("Số lượng còn lại: " + sanPham.getSoLuongSanPham());
            holder.updateSoLuongSanPham();
        }
        if (sanPham.getDanhGiaSanPhams() != null) {
            holder.getSo_danh_gia().setText("Số đánh giá: " + sanPham.getDanhGiaSanPhams().size());
            holder.updateSoDanhGia();
            holder.getSao().setText("Đánh giá: " + sanPham.getSao() + " sao");
            holder.updateSoSao();
        }
    }

    private void removeDanhMuc(String ma_danh_muc) {
        // Webservice
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.xoa_mot_san_pham,
                response -> {
                    for (SanPham sanPham : root) {
                        if (sanPham.getMaSanPham().equals(ma_danh_muc)) {
                            root.remove(sanPham);
                            break;
                        }
                    }
                    Toast.makeText(sanPhamFragment.getActivity(), "Xóa thành công sản phẩm có ID là #" + ma_danh_muc, Toast.LENGTH_SHORT).show();
                    // Show
                    sanPhamFragment.show();
                    sanPhamFragment.sort();
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
        VolleyPool.getInstance(sanPhamFragment.getActivity()).addRequest(stringRequest);
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
                        map.put("noi_dung", "vừa xóa một sản phẩm có ID là");
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
        VolleyPool.getInstance(sanPhamFragment.getActivity()).addRequest(stringRequest);
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
                params.put("noi_dung", "vừa xóa một sản phẩm có ID là");
                params.put("noi_dung_quan_trong", "#" + ma_danh_muc);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(sanPhamFragment.getActivity()).addRequest(stringRequest);
    }

    @Override
    public int getItemCount() {
        return show.size();
    }
}
