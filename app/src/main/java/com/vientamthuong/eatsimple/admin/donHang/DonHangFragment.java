package com.vientamthuong.eatsimple.admin.donHang;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.HomePageActivity;
import com.vientamthuong.eatsimple.admin.configuration.Configuration;
import com.vientamthuong.eatsimple.admin.configuration.WebService;
import com.vientamthuong.eatsimple.admin.danhMuc.SelectAdapter;
import com.vientamthuong.eatsimple.admin.dialog.DiaLogConfirm;
import com.vientamthuong.eatsimple.admin.loadData.LoadData;
import com.vientamthuong.eatsimple.admin.model.DanhMucSanPham;
import com.vientamthuong.eatsimple.admin.model.DonHang;
import com.vientamthuong.eatsimple.admin.model.KhachHangDonHang;
import com.vientamthuong.eatsimple.admin.model.MainFragment;
import com.vientamthuong.eatsimple.admin.model.SanPham;
import com.vientamthuong.eatsimple.admin.model.SanPhamDonHang;
import com.vientamthuong.eatsimple.admin.sanPham.SanPhamAdapter;
import com.vientamthuong.eatsimple.admin.sanPham.SanPhamAddFragment;
import com.vientamthuong.eatsimple.admin.session.DataSession;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.protocol.ActivityProtocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonHangFragment extends Fragment implements MainFragment {

    private Spinner spinner;
    private List<String> listType;
    private ImageView sort;
    private EditText search;
    private RecyclerView recyclerView;
    private DonHangAdapter donHangAdapter;
    private List<DonHang> rootArray;
    private List<DonHang> showArray;
    private int nowSort;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_don_hang_home, container, false);
        getView(view);
        init();
        action();
        return view;
    }

    private void init() {
        initSpinner();
        initRecyclerView();

        // ready
        if (!LoadData.getInstance().isReadyFromMainFragment()) {
            LoadData.getInstance().setReadyFromMainFragment(true);
            HomePageActivity homePageActivity = (HomePageActivity) getActivity();
            homePageActivity.getDataMainFragment();
        }
    }

    private void initRecyclerView() {
        rootArray = new ArrayList<>();
        showArray = new ArrayList<>();
        // Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        for (int i = 0; i < 10; i++) {
            DonHang sp1 = new DonHang();
            DonHang sp2 = new DonHang();
            sp1.setTong_tien(-1);
            sp1.setTrang_thai(-1);
            sp2.setTong_tien(-1);
            sp2.setTrang_thai(-1);
            showArray.add(sp1);
            rootArray.add(sp2);
        }
        donHangAdapter = new DonHangAdapter(R.layout.admin_view_holder_item_don_hang, showArray, rootArray,
                this);
        recyclerView.setAdapter(donHangAdapter);
        donHangAdapter.notifyDataSetChanged();
    }

    private void initSpinner() {
        listType = new ArrayList<>(Arrays.asList("Ngày đặt", "Tên khách hàng", "Mã đơn hàng", "Số điện thoại", "Họ tên người nhận", "Địa chỉ", "Mã giảm giá", "Tổng tiền", "Trạng thái", "Số lượng sản phẩm"));
        SelectAdapter selectAdapter = new SelectAdapter(getActivity(), R.layout.admin_view_holder_select, listType);
        spinner.setAdapter(selectAdapter);
    }

    private void getView(View view) {
        spinner = view.findViewById(R.id.spinner);
        sort = view.findViewById(R.id.sort);
        search = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.view_chinh);
    }

    private void action() {
        sort.setOnClickListener(v -> {
            if (nowSort == Configuration.ASC) {
                nowSort = Configuration.DESC;
                sort.setScaleY(1);
            } else {
                nowSort = Configuration.ASC;
                sort.setScaleY(-1);
            }
            sort();
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (LoadData.getLoadData().isReadyFromMainFragment()) {
                    show();
                    sort();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                show();
                sort();
            }
        });
    }

    @Override
    public void getData(DatabaseReference root, DiaLogLoader diaLogLoader, List<LoadImageForView> imagesNeedLoad, AppCompatActivity appCompatActivity) {
        // Lay activity protocol
        ActivityProtocol activityProtocol = (ActivityProtocol) appCompatActivity;
        // load
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.lay_don_hang_1,
                response -> {
                    try {
                        // Hiện màn hình chờ
                        diaLogLoader.show();
                        // Làm cho 4 thằng đầu tiền full null
                        rootArray.clear();
                        for (int i = 0; i < 10; i++) {
                            DonHang sp1 = new DonHang();
                            sp1.setTrang_thai(-1);
                            sp1.setTong_tien(-1);
                            rootArray.add(sp1);
                        }
                        donHangAdapter.notifyDataSetChanged();
                        // Biến count để biết có bao nhiêu thằng
                        JSONArray jsonArray = new JSONArray(response);
                        int countDanhMuc = 0;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            countDanhMuc++;
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            DonHang donHang = new DonHang();
                            donHang.setMa_dh(jsonObject.getString("ma_dh"));
                            KhachHangDonHang khachHangDonHang = new KhachHangDonHang(jsonObject.getString("ten"),
                                    jsonObject.getString("ma_kh"), null, jsonObject.getString("hinh"));
                            donHang.setKhachHangDonHang(khachHangDonHang);
                            donHang.setNgay_tao(new DateTime(jsonObject.getString("ngay_tao")));
                            donHang.setTrang_thai(jsonObject.getInt("trang_thai"));
                            donHang.setTong_tien(jsonObject.getInt("tong_tien"));
                            donHang.setMa_giam_gia(jsonObject.getString("ma_giam_gia"));
                            donHang.setDia_chi(jsonObject.getString("dia_chi"));
                            donHang.setSo_dien_thoai(jsonObject.getString("so_dien_thoai"));
                            donHang.setTen_nguoi_nhan(jsonObject.getString("ten_nguoi_nhan"));
                            if (countDanhMuc < 11) {
                                rootArray.set(countDanhMuc - 1, new DonHang(donHang));
                            } else {
                                rootArray.add(donHang);
                            }
                            donHangAdapter.notifyDataSetChanged();
                        }
                        // Nếu như số danh mục < 4 (mặc định )thì xóa bớt
                        if (countDanhMuc < 10) {
                            int count = 0;
                            while (count < rootArray.size()) {
                                if (rootArray.get(count).getMa_dh() == null) {
                                    rootArray.remove(count);
                                } else {
                                    count++;
                                }
                            }
                        }
                        // Đưa vô imageNeedLoad
                        for (DonHang donHang : rootArray) {
                            imagesNeedLoad.add(new LoadImageForView(appCompatActivity, donHang, this, LoadDataConfiguration.DON_HANG_ADMIN));
                        }
                        // Show
                        show();
                        sort();
                        // Và giờ tải hình từ các link hình
                        if (!activityProtocol.isRunningVolley()) {
                            activityProtocol.setRunningVolley(true);
                            activityProtocol.loadImageFromIntenet();
                        }
                        // Tắt màn hình chờ
                        diaLogLoader.dismiss();
                        // data2
                        getData2( imagesNeedLoad, appCompatActivity);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
    }

    public void getData2(List<LoadImageForView> imagesNeedLoad, AppCompatActivity appCompatActivity) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.lay_don_hang_2,
                response -> {
                    JSONArray jsonArray = null;
                    try {

                        jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            SanPhamDonHang s = new SanPhamDonHang();
                            s.setTen_sp(jsonObject.getString("ten_sp"));
                            s.setTen_size(jsonObject.getString("ten_size"));
                            s.setSo_luong(jsonObject.getInt("so_luong"));
                            s.setTien(jsonObject.getInt("tien"));
                            s.setUrl(jsonObject.getString("url"));
                            String ma_dh = jsonObject.getString("ma_dh");
                            String ma_kh = jsonObject.getString("ma_kh");
                            for (DonHang donHang1 : rootArray) {
                                if (donHang1.getMa_dh() != null && donHang1.getKhachHangDonHang() != null) {
                                    if (donHang1.getMa_dh().equals(ma_dh) && donHang1.getKhachHangDonHang().getMa_kh().equals(ma_kh)) {
                                        if (donHang1.getSanPhamDonHangs() == null) {
                                            List<SanPhamDonHang> sanPhamDonHangs = new ArrayList<>();
                                            sanPhamDonHangs.add(s);
                                            donHang1.setSanPhamDonHangs(sanPhamDonHangs);
                                        } else {
                                            donHang1.getSanPhamDonHangs().add(s);
                                        }
                                        break;
                                    }
                                }
                            }
                            imagesNeedLoad.add(new LoadImageForView(appCompatActivity, s, LoadDataConfiguration.SAN_PHAM_DON_HANG_ADMIN));
                        }
                        donHangAdapter.notifyDataSetChanged();
                        this.show();
                        this.sort();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
    }

    @Override
    public void update() {
        for (DonHang sanPham : showArray) {
            for (DonHang d : rootArray) {
                if (d.getMa_dh() != null && d.getKhachHangDonHang() != null) {
                    if (sanPham.getMa_dh().equals(d.getMa_dh()) && sanPham.getKhachHangDonHang().getMa_kh().equals(d.getKhachHangDonHang().getMa_kh())) {
                        sanPham.getKhachHangDonHang().setHinh(d.getKhachHangDonHang().getHinh());
                        break;
                    }
                }
            }
        }
        donHangAdapter.notifyDataSetChanged();
    }

    public void sort() {
        String sort = spinner.getSelectedItem().toString();
        showArray.sort((o1, o2) -> {
            switch (sort) {
                case "Ngày đặt":
                    if (o1.getNgay_tao() != null) {
                        if (nowSort == Configuration.ASC) {
                            long t1 = o1.getNgay_tao().getTime();
                            long t2 = o2.getNgay_tao().getTime();
                            if (t1 == t2) return 0;
                            return t1 > t2 ? 1 : -1;
                        } else {
                            long t1 = o1.getNgay_tao().getTime();
                            long t2 = o2.getNgay_tao().getTime();
                            if (t1 == t2) return 0;
                            return t1 > t2 ? -1 : 1;
                        }
                    } else {
                        return 0;
                    }
                case "Tên khách hàng":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getKhachHangDonHang().getTen_hien_thi(), o2.getKhachHangDonHang().getTen_hien_thi());
                    } else {
                        return compareToString(o2.getKhachHangDonHang().getTen_hien_thi(), o1.getKhachHangDonHang().getTen_hien_thi());
                    }
                case "Mã đơn hàng":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getMa_dh(), o2.getMa_dh());
                    } else {
                        return compareToString(o2.getMa_dh(), o1.getMa_dh());
                    }
                case "Số điện thoại":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getSo_dien_thoai(), o2.getSo_dien_thoai());
                    } else {
                        return compareToString(o2.getSo_dien_thoai(), o1.getSo_dien_thoai());
                    }
                case "Họ tên người nhận":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getTen_nguoi_nhan(), o2.getTen_nguoi_nhan());
                    } else {
                        return compareToString(o2.getTen_nguoi_nhan(), o1.getTen_nguoi_nhan());
                    }
                case "Địa chỉ":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getDia_chi(), o2.getDia_chi());
                    } else {
                        return compareToString(o2.getDia_chi(), o1.getDia_chi());
                    }
                case "Mã giảm giá":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getMa_giam_gia(), o2.getMa_giam_gia());
                    } else {
                        return compareToString(o2.getMa_giam_gia(), o1.getMa_giam_gia());
                    }
                case "Tổng tiền":
                    if (nowSort == Configuration.ASC) {
                        return o1.getTong_tien() - o2.getTong_tien();
                    } else {
                        return o2.getTong_tien() - o1.getTong_tien();
                    }
                case "Trạng thái":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(getStringTrangThai(o1.getTrang_thai()), getStringTrangThai(o2.getTrang_thai()));
                    } else {
                        return compareToString(getStringTrangThai(o2.getTrang_thai()), getStringTrangThai(o1.getTrang_thai()));
                    }
                default:
                    if (nowSort == Configuration.ASC) {
                        return o1.getSanPhamDonHangs().size() - o2.getSanPhamDonHangs().size();
                    } else {
                        return o2.getSanPhamDonHangs().size() - o1.getSanPhamDonHangs().size();
                    }
            }
        });
        donHangAdapter.notifyDataSetChanged();
    }

    public void show() {
        String search = this.search.getText().toString().toLowerCase();
        String sort = spinner.getSelectedItem().toString();
        showArray.clear();
        for (DonHang donHang : rootArray) {
            boolean isOKe = false;
            switch (sort) {
                case "Ngày đặt":
                    if (donHang.getNgay_tao() != null) {
                        if (donHang.getNgay_tao().toStringDateTypeNumberStringNumber().toLowerCase().contains(search)) {
                            isOKe = true;
                        }
                    } else {
                        isOKe = true;
                    }
                    break;
                case "Tên khách hàng":
                    if (donHang.getKhachHangDonHang().getTen_hien_thi().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Mã đơn hàng":
                    if (donHang.getMa_dh().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Số điện thoại":
                    if (donHang.getSo_dien_thoai().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Họ tên người nhận":
                    if (donHang.getTen_nguoi_nhan().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Địa chỉ":
                    if (donHang.getDia_chi().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Mã giảm giá":
                    if (donHang.getMa_giam_gia().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Tổng tiền":
                    if (search.trim().length() == 0) {
                        isOKe = true;
                    } else {
                        String s = donHang.getTong_tien() + "";
                        if (s.contains(search)) {
                            isOKe = true;
                        }
                    }
                    break;
                case "Trạng thái":
                    if (getStringTrangThai(donHang.getTrang_thai()).toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Số lượng sản phẩm":
                    if (search.trim().length() == 0) {
                        isOKe = true;
                    } else {
                        String s = donHang.getSanPhamDonHangs().size() + "";
                        if (s.contains(search)) {
                            isOKe = true;
                        }
                    }
                    break;
            }
            if (isOKe) {
                showArray.add(new DonHang(donHang));
            }
        }
    }

    public int compareToString(String s1, String s2) {
        char[] a1 = s1.trim().toCharArray();
        char[] a2 = s2.trim().toCharArray();
        int count = 0;
        int result = 0;
        while (count < a1.length && count < a2.length) {
            int c = a1[count] - a2[count];
            if (c == 0) {
                count++;
            } else {
                result = c;
                break;
            }
        }
        return result;
    }

    public String getStringTrangThai(int trangThai) {
        String trang_thai = "";
        if (trangThai == 1) {
            trang_thai = "Đang chờ Admin duyệt";
        } else if (trangThai == 2) {
            trang_thai = "Đang chờ NV nấu nướng duyệt";
        } else if (trangThai == 3) {
            trang_thai = "Đang chờ NV giao hàng duyệt";
        } else {
            trang_thai = "Đã được giao";
        }
        return trang_thai;
    }
}