package com.vientamthuong.eatsimple.admin.donHang;

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
import com.vientamthuong.eatsimple.admin.dialog.DiaLogConfirm;
import com.vientamthuong.eatsimple.admin.model.DonHang;
import com.vientamthuong.eatsimple.admin.model.SanPham;
import com.vientamthuong.eatsimple.admin.sanPham.SanPhamEditFragment;
import com.vientamthuong.eatsimple.admin.sanPham.SanPhamFragment;
import com.vientamthuong.eatsimple.admin.sanPham.SanPhamSession;
import com.vientamthuong.eatsimple.admin.sanPham.SanPhamViewHolder;
import com.vientamthuong.eatsimple.admin.session.DataSession;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangViewHolder> {

    private int resource;
    private List<DonHang> show;
    private List<DonHang> root;
    private Context context;
    private DonHangFragment donHangFragment;

    public DonHangAdapter(int resource, List<DonHang> show, List<DonHang> root, DonHangFragment donHangFragment) {
        this.resource = resource;
        this.show = show;
        this.root = root;
        this.donHangFragment = donHangFragment;
    }

    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonHangViewHolder(LayoutInflater.from(parent.getContext()).inflate(resource, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        DonHang donHang = show.get(position);
        holder.getXoa().setOnClickListener(v -> {
            DiaLogConfirm diaLogConfirm = new DiaLogConfirm(donHangFragment.getActivity());
            diaLogConfirm.getTextViewTitle().setText("Duyệt đơn hàng");
            diaLogConfirm.getTextViewContent().setText("Bạn có chắc chắn rằng mình muốn duyệt đơn hàng có ID: " + donHang.getMa_dh() + " của khách hàng có ID: " + donHang.getKhachHangDonHang().getMa_kh() + " không?");
            diaLogConfirm.getBtTry().setText("Không");
            diaLogConfirm.getBtIgnore().setText("Duyệt");
            diaLogConfirm.getBtTry().setOnClickListener(v1 -> diaLogConfirm.dismiss());
            diaLogConfirm.getBtIgnore().setOnClickListener(v1 -> {
                System.out.println(donHang.getSanPhamDonHangs());
                diaLogConfirm.dismiss();
                // Xóa danh mục
                removeDanhMuc(donHang.getMa_dh(), donHang.getKhachHangDonHang().getMa_kh(), donHang.getTrang_thai() + 1);
                // Tạo thông báo cá nhân
                addThongBaoCaNhan(donHang.getMa_dh());
            });
            diaLogConfirm.show();
        });
        holder.getSua().setOnClickListener(v -> {
            DonHangSession.getInstance().setDonHang(new DonHang(donHang));
            FragmentTransaction fragmentTransaction = donHangFragment.getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.f2, new DonHangDetailFragment(), "edit");
            fragmentTransaction.commit();
        });
        if (donHang.getMa_dh() != null) {
            holder.getMaDh().setText("#" + donHang.getMa_dh());
            holder.updateMaDh();
        }
        if (donHang.getKhachHangDonHang() != null) {
            if (donHang.getKhachHangDonHang().getHinh() != null) {
                holder.getHinhDaiDien().setImageBitmap(donHang.getKhachHangDonHang().getHinh());
                holder.updateHinhDaiDien();
            }
            holder.getTenKh().setText(donHang.getKhachHangDonHang().getTen_hien_thi());
            holder.updateTenKh();
        }
        if (donHang.getNgay_tao() != null) {
            holder.getNgayTao().setText(donHang.getNgay_tao().toStringDateTypeNumberStringNumber());
            holder.updateNgayTao();
        }
        if (donHang.getSo_dien_thoai() != null) {
            holder.getSdt().setText("Số điện thoại: " + donHang.getSo_dien_thoai());
            holder.updateSdt();
        }
        if (donHang.getTen_nguoi_nhan() != null) {
            holder.getNguoi_nhan().setText("Giao đến: " + donHang.getTen_nguoi_nhan());
            holder.updateNguoiNhan();
        }
        if (donHang.getDia_chi() != null) {
            holder.getDia_chi().setText("Địa chỉ: " + donHang.getDia_chi());
            holder.updateDiaChi();
        }
        if (donHang.getMa_giam_gia() != null) {
            if (donHang.getMa_giam_gia().equals("null")) {
                holder.getMgg().setText("Mã giảm giá: Không dùng");
            } else {
                holder.getMgg().setText("Mã giảm giá: "+donHang.getMa_giam_gia());
            }
            holder.updateMgg();
        }
        if (donHang.getTong_tien() != -1) {
            holder.getTien().setText("Tổng tiền: " + String.format("%,.0f", (double) donHang.getTong_tien()));
            holder.updateTien();
        }
        if (donHang.getTrang_thai() != -1) {
            String trang_thai = "";
            if (donHang.getTrang_thai() == 1) {
                trang_thai = "Đang chờ Admin duyệt";
            } else if (donHang.getTrang_thai() == 2) {
                trang_thai = "Đang chờ NV nấu nướng duyệt";
            } else if (donHang.getTrang_thai() == 3) {
                trang_thai = "Đang chờ NV giao hàng duyệt";
            } else {
                trang_thai = "Đã được giao";
            }
            holder.getTrang_thai().setText("Trạng thái: " + trang_thai);
            holder.updateTrangThai(donHang.getTrang_thai());
        }
        if (donHang.getSanPhamDonHangs() != null) {
            holder.getSo_luong_san_pham().setText("Số lượng sản phẩm: " + donHang.getSanPhamDonHangs().size());
            holder.updateSoLuongSanPham();
        }
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
                        map.put("noi_dung", "vừa duyệt một đơn hàng có ID là");
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
        VolleyPool.getInstance(donHangFragment.getActivity()).addRequest(stringRequest);
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
                params.put("noi_dung", "vừa duyệt một đơn hàng có ID là");
                params.put("noi_dung_quan_trong", "#" + ma_danh_muc);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(donHangFragment.getActivity()).addRequest(stringRequest);
    }

    private void removeDanhMuc(String ma_dh, String ma_kh, int trang_thai) {
        for(DonHang donHang: root){
            if(donHang.getKhachHangDonHang().getMa_kh().equals(ma_kh) && donHang.getMa_dh().equals(ma_dh)){
                root.remove(donHang);
                break;
            }
        }
        notifyDataSetChanged();
        donHangFragment.show();
        donHangFragment.sort();
        Toast.makeText(donHangFragment.getActivity(), "Duyệt thành công duyệt đơn hàng có ID: " + ma_dh + " của khách hàng có ID: " + ma_kh, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return show.size();
    }
}
