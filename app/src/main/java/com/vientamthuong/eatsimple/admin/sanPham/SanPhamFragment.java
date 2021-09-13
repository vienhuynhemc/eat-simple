package com.vientamthuong.eatsimple.admin.sanPham;

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
import com.google.firebase.database.FirebaseDatabase;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.HomePageActivity;
import com.vientamthuong.eatsimple.admin.configuration.Configuration;
import com.vientamthuong.eatsimple.admin.configuration.WebService;
import com.vientamthuong.eatsimple.admin.danhMuc.DanhMucAdapter;
import com.vientamthuong.eatsimple.admin.danhMuc.DanhMucAddFragment;
import com.vientamthuong.eatsimple.admin.danhMuc.SelectAdapter;
import com.vientamthuong.eatsimple.admin.dialog.DiaLogConfirm;
import com.vientamthuong.eatsimple.admin.loadData.LoadData;
import com.vientamthuong.eatsimple.admin.model.DanhGiaSanPham;
import com.vientamthuong.eatsimple.admin.model.DanhMuc;
import com.vientamthuong.eatsimple.admin.model.DanhMucSanPham;
import com.vientamthuong.eatsimple.admin.model.MainFragment;
import com.vientamthuong.eatsimple.admin.model.SanPham;
import com.vientamthuong.eatsimple.admin.model.Size;
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

public class SanPhamFragment extends Fragment implements MainFragment {

    private Spinner spinner;
    private List<String> listType;
    private ImageView sort;
    private EditText search;
    private RecyclerView recyclerView;
    private SanPhamAdapter sanPhamAdapter;
    private List<SanPham> rootArray;
    private List<SanPham> showArray;
    private FloatingActionButton buttonThem;
    private FloatingActionButton buttonXoa;
    private FloatingActionButton buttonAll;
    private int nowSort;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_san_pham_home, container, false);
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
            SanPham sp1 = new SanPham();
            SanPham sp2 = new SanPham();
            sp1.setChonXoa(false);
            sp1.setGia(-1);
            sp1.setGia_km(-1);
            sp1.setKcal(-1);
            sp1.setSo_luong_ban_ra(-1);
            sp1.setThoi_gian_nau(-1);
            sp2.setChonXoa(false);
            sp2.setGia(-1);
            sp2.setGia_km(-1);
            sp2.setKcal(-1);
            sp2.setSo_luong_ban_ra(-1);
            sp2.setThoi_gian_nau(-1);
            showArray.add(sp1);
            rootArray.add(sp2);
        }
        sanPhamAdapter = new SanPhamAdapter(R.layout.admin_view_holder_item_san_pham, showArray, rootArray,
                this);
        recyclerView.setAdapter(sanPhamAdapter);
        sanPhamAdapter.notifyDataSetChanged();
    }

    private void initSpinner() {
        listType = new ArrayList<>(Arrays.asList("Ngày tạo", "Tên sản phẩm", "Mã sản phẩm", "Tên danh mục", "Giá", "Giá khuyến mãi", "Size", "Kcal", "Thời gian nấu", "Số đánh giá", "Số sao", "Số lượng bán ra", "Số lượng sản phẩm"));
        SelectAdapter selectAdapter = new SelectAdapter(getActivity(), R.layout.admin_view_holder_select, listType);
        spinner.setAdapter(selectAdapter);
    }

    private void getView(View view) {
        spinner = view.findViewById(R.id.spinner);
        sort = view.findViewById(R.id.sort);
        search = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.view_chinh);
        buttonThem = view.findViewById(R.id.buttonThem);
        buttonXoa = view.findViewById(R.id.buttonXoa);
        buttonAll = view.findViewById(R.id.buttonAll);
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
        buttonAll.setOnClickListener(v -> {
            if (buttonXoa.getVisibility() == View.VISIBLE) {
                for (SanPham sanPham : showArray) {
                    sanPham.setChonXoa(false);
                    for (SanPham sa : rootArray) {
                        if (sa.getMaSanPham().equals(sanPham.getMaSanPham())) {
                            sa.setChonXoa(sanPham.isChonXoa());
                            break;
                        }
                    }
                }
                buttonXoa.setVisibility(View.GONE);
                buttonAll.setImageResource(R.drawable.admin_fragment_danh_muc_done_all);
            } else {
                for (SanPham sanPham : showArray) {
                    sanPham.setChonXoa(true);
                    for (SanPham d : rootArray) {
                        if (d.getMaSanPham().equals(sanPham.getMaSanPham())) {
                            d.setChonXoa(sanPham.isChonXoa());
                            break;
                        }
                    }
                }
                buttonXoa.setVisibility(View.VISIBLE);
                buttonAll.setImageResource(R.drawable.admin_fragment_danh_muc_clear_all);
            }
            sanPhamAdapter.notifyDataSetChanged();
        });
        buttonThem.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.f2, new SanPhamAddFragment(), "add");
            fragmentTransaction.commit();
        });
        buttonXoa.setOnClickListener(v -> {
            List<String> ma_danh_mucs = new ArrayList<>();
            for (SanPham sanPham : showArray) {
                if (sanPham.isChonXoa()) {
                    ma_danh_mucs.add(sanPham.getMaSanPham());
                }
            }
            DiaLogConfirm diaLogConfirm = new DiaLogConfirm(getActivity());
            diaLogConfirm.getTextViewTitle().setText("Xóa nhiều danh mục");
            diaLogConfirm.getTextViewContent().setText("Bạn có chắc chắn rằng mình muốn xóa " + ma_danh_mucs.size() + " sản phẩm đã chọn không?");
            diaLogConfirm.getBtTry().setText("Không");
            diaLogConfirm.getBtIgnore().setText("Xóa");
            diaLogConfirm.getBtTry().setOnClickListener(v1 -> diaLogConfirm.dismiss());
            diaLogConfirm.getBtIgnore().setOnClickListener(v1 -> {
                diaLogConfirm.dismiss();
                for (String ma_danh_muc : ma_danh_mucs) {
                    // Webservice
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            WebService.xoa_mot_san_pham,
                            response -> {
                                for (SanPham sanPham : rootArray) {
                                    if (sanPham.getMaSanPham().equals(ma_danh_muc)) {
                                        rootArray.remove(sanPham);
                                        break;
                                    }
                                }
                                // Show
                                show();
                                sort();
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
                    VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
                }
                Toast.makeText(getActivity(), "Xóa thành công " + ma_danh_mucs.size() + " sản phẩm", Toast.LENGTH_SHORT).show();
                // Tạo thông báo cá nhân
                addNewThongBaoCaNhan(ma_danh_mucs);
            });
            diaLogConfirm.show();
        });
    }

    private void addNewThongBaoCaNhan(List<String> ma_danh_mucs) {
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
                        map.put("noi_dung", "vừa xóa " + ma_danh_mucs.size() + " sản phẩm có ID lần lượt là ");
                        String noi_dung_quan_trong = "";
                        for (String s : ma_danh_mucs) {
                            noi_dung_quan_trong += "#" + s + ", ";
                        }
                        noi_dung_quan_trong = noi_dung_quan_trong.trim().substring(0, noi_dung_quan_trong.length() - 1);
                        map.put("noi_dung_quan_trong", noi_dung_quan_trong);
                        root.child("thong_bao_ca_nhan").child(ma_thong_bao_ca_nhan).setValue(map);
                        // Webservice
                        addNewThongBaoCaNhanWebService(ma_thong_bao_ca_nhan, nowDate, ma_danh_mucs, noi_dung_quan_trong);
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
        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
    }

    private void addNewThongBaoCaNhanWebService(String ma_thong_bao_ca_nhan, DateTime ngay_tao, List<String> ma_danh_mucs, String noi_dung_quan_trong) {
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
                params.put("noi_dung", "vừa xóa " + ma_danh_mucs.size() + " sản phẩm có ID lần lượt là ");
                params.put("noi_dung_quan_trong", noi_dung_quan_trong);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
    }

    @Override
    public void getData(DatabaseReference root, DiaLogLoader diaLogLoader, List<LoadImageForView> imagesNeedLoad, AppCompatActivity appCompatActivity) {
        // Lay activity protocol
        ActivityProtocol activityProtocol = (ActivityProtocol) appCompatActivity;
        // load
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.lay_san_pham_1,
                response -> {
                    try {
                        // Hiện màn hình chờ
                        diaLogLoader.show();
                        // Làm cho 4 thằng đầu tiền full null
                        rootArray.clear();
                        for (int i = 0; i < 10; i++) {
                            SanPham sp1 = new SanPham();
                            sp1.setChonXoa(false);
                            sp1.setGia(-1);
                            sp1.setGia_km(-1);
                            sp1.setKcal(-1);
                            sp1.setSo_luong_ban_ra(-1);
                            sp1.setThoi_gian_nau(-1);
                            rootArray.add(sp1);
                        }
                        sanPhamAdapter.notifyDataSetChanged();
                        // Biến count để biết có bao nhiêu thằng
                        JSONArray jsonArray = new JSONArray(response);
                        int countDanhMuc = 0;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            countDanhMuc++;
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            SanPham sanPham = new SanPham();
                            sanPham.setMaSanPham(jsonObject.getString("ma_sp"));
                            sanPham.setTenSanPham(jsonObject.getString("ten_sp"));
                            sanPham.setHinh_fb(jsonObject.getString("hinh"));
                            sanPham.setKcal(jsonObject.getInt("kcal"));
                            sanPham.setSo_luong_ban_ra(jsonObject.getInt("so_luong_ban_ra"));
                            sanPham.setThoi_gian_nau(jsonObject.getInt("thoi_gian_nau"));
                            sanPham.setGia(jsonObject.getInt("gia"));
                            sanPham.setGia_km(jsonObject.getInt("gia_km"));
                            DanhMucSanPham danhMucSanPham = new DanhMucSanPham(jsonObject.getString("ten_danh_muc"), jsonObject.getString("ma_danh_muc"));
                            sanPham.setDanhMucSanPham(danhMucSanPham);
                            sanPham.setThong_tin(jsonObject.getString("thong_tin"));
                            sanPham.setUrl(jsonObject.getString("url"));
                            sanPham.setNgayTao(new DateTime(jsonObject.getString("ngay_tao")));
                            if (countDanhMuc < 11) {
                                rootArray.set(countDanhMuc - 1, new SanPham(sanPham));
                            } else {
                                rootArray.add(sanPham);
                            }
                            sanPhamAdapter.notifyDataSetChanged();
                        }
                        // Nếu như số danh mục < 4 (mặc định )thì xóa bớt
                        if (countDanhMuc < 10) {
                            int count = 0;
                            while (count < rootArray.size()) {
                                if (rootArray.get(count).getMaSanPham() == null) {
                                    rootArray.remove(count);
                                } else {
                                    count++;
                                }
                            }
                        }
                        // Đưa vô imageNeedLoad
                        for (SanPham sanPham : rootArray) {
                            imagesNeedLoad.add(new LoadImageForView(appCompatActivity, sanPham, LoadDataConfiguration.SAN_PHAM_ADMIN));
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
                        // size
                        getDataSize(diaLogLoader);
                        // Đánh giá
                        getDanhGia(diaLogLoader);
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

    public void getDataSize( DiaLogLoader diaLogLoader) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.lay_san_pham_2,
                response -> {
                    try {
                        // Hiện màn hình chờ
                        diaLogLoader.show();
                        // Biến count để biết có bao nhiêu thằng
                        JSONArray jsonArray = new JSONArray(response);
                        HashMap<String, List<Size>> map = new HashMap<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String ma_sp = jsonObject.getString("ma_sp");
                            Size size = new Size(jsonObject.getString("ma_size"), jsonObject.getString("ten_size"), jsonObject.getInt("so_luong_con_lai"));
                            if (map.containsKey(ma_sp)) {
                                List<Size> list = map.get(ma_sp);
                                list.add(size);
                                map.put(ma_sp, list);
                            } else {
                                List<Size> list = new ArrayList<>();
                                list.add(size);
                                map.put(ma_sp, list);
                            }
                        }
                        for (Map.Entry<String, List<Size>> e : map.entrySet()) {
                            for (SanPham s : rootArray) {
                                if (s.getMaSanPham().equals(e.getKey())) {
                                    s.setSizes(e.getValue());
                                    break;
                                }
                            }
                        }
                        sanPhamAdapter.notifyDataSetChanged();
                        // Show
                        show();
                        sort();
                        // Tắt màn hình chờ
                        diaLogLoader.dismiss();
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

    public void getDanhGia( DiaLogLoader diaLogLoader) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.lay_san_pham_3,
                response -> {
                    try {
                        // Hiện màn hình chờ
                        diaLogLoader.show();
                        // Biến count để biết có bao nhiêu thằng
                        JSONArray jsonArray = new JSONArray(response);
                        HashMap<String, List<DanhGiaSanPham>> map = new HashMap<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String ma_sp = jsonObject.getString("ma_sp");
                            DanhGiaSanPham danhGiaSanPham = new DanhGiaSanPham(jsonObject.getString("ma_danh_gia"),jsonObject.getInt("so_sao"),jsonObject.getString("ma_size"));
                            if (map.containsKey(ma_sp)) {
                                List<DanhGiaSanPham> list = map.get(ma_sp);
                                list.add(danhGiaSanPham);
                                map.put(ma_sp, list);
                            } else {
                                List<DanhGiaSanPham> list = new ArrayList<>();
                                list.add(danhGiaSanPham);
                                map.put(ma_sp, list);
                            }
                        }
                        for (Map.Entry<String, List<DanhGiaSanPham>> e : map.entrySet()) {
                            for (SanPham s : rootArray) {
                                if (s.getMaSanPham().equals(e.getKey())) {
                                    s.setDanhGiaSanPhams(e.getValue());
                                    break;
                                }
                            }
                        }
                        for(SanPham s : rootArray){
                            if(s.getDanhGiaSanPhams() == null){
                                s.setDanhGiaSanPhams(new ArrayList<>());
                            }
                        }
                        sanPhamAdapter.notifyDataSetChanged();
                        // Show
                        show();
                        sort();
                        // Tắt màn hình chờ
                        diaLogLoader.dismiss();
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
        for (SanPham sanPham : showArray) {
            for (SanPham d : rootArray) {
                if (d.getMaSanPham() != null && sanPham.getMaSanPham().equals(d.getMaSanPham())) {
                    sanPham.setHinh(d.getHinh());
                    break;
                }
            }
        }
        sanPhamAdapter.notifyDataSetChanged();
    }

    public void updateButtonXoa() {
        int count = 0;
        for (SanPham sanPham : showArray) {
            if (sanPham.isChonXoa()) {
                count++;
                break;
            }
        }
        if (count != 0) {
            buttonXoa.setVisibility(View.VISIBLE);
            buttonAll.setImageResource(R.drawable.admin_fragment_danh_muc_clear_all);
        } else {
            buttonXoa.setVisibility(View.GONE);
            buttonAll.setImageResource(R.drawable.admin_fragment_danh_muc_done_all);
        }
    }

    public void sort() {
        String sort = spinner.getSelectedItem().toString();
        showArray.sort((o1, o2) -> {
            switch (sort) {
                case "Ngày tạo":
                    if (o1.getNgayTao() != null) {
                        if (nowSort == Configuration.ASC) {
                            return (int) (o1.getNgayTao().getTime() - o2.getNgayTao().getTime());
                        } else {
                            return (int) (o2.getNgayTao().getTime() - o1.getNgayTao().getTime());
                        }
                    } else {
                        return 0;
                    }
                case "Tên sản phẩm":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getTenSanPham(), o2.getTenSanPham());
                    } else {
                        return compareToString(o2.getTenSanPham(), o1.getTenSanPham());
                    }
                case "Mã sản phẩm":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getMaSanPham(), o2.getMaSanPham());
                    } else {
                        return compareToString(o2.getMaSanPham(), o1.getMaSanPham());
                    }
                case "Tên danh mục":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getDanhMucSanPham().getTenDanhMuc(), o2.getDanhMucSanPham().getTenDanhMuc());
                    } else {
                        return compareToString(o2.getDanhMucSanPham().getTenDanhMuc(), o1.getDanhMucSanPham().getTenDanhMuc());
                    }
                case "Giá":
                    if (nowSort == Configuration.ASC) {
                        return o1.getGia() - o2.getGia();
                    } else {
                        return o2.getGia() - o1.getGia();
                    }
                case "Giá khuyến mãi":
                    if (nowSort == Configuration.ASC) {
                        return o1.getGia_km() - o2.getGia_km();
                    } else {
                        return o2.getGia_km() - o1.getGia_km();
                    }
                case "Kcal":
                    if (nowSort == Configuration.ASC) {
                        return o1.getKcal() - o2.getKcal();
                    } else {
                        return o2.getKcal() - o1.getKcal();
                    }
                case "Thời gian nấu":
                    if (nowSort == Configuration.ASC) {
                        return o1.getThoi_gian_nau() - o2.getThoi_gian_nau();
                    } else {
                        return o2.getThoi_gian_nau() - o1.getThoi_gian_nau();
                    }
                case "Số đánh giá":
                    if (nowSort == Configuration.ASC) {
                        return o1.getDanhGiaSanPhams().size() - o2.getDanhGiaSanPhams().size();
                    } else {
                        return o2.getDanhGiaSanPhams().size() - o1.getDanhGiaSanPhams().size();
                    }
                case "Số sao":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getSizeString(), o2.getSizeString());
                    } else {
                        return compareToString(o2.getSizeString(), o1.getSizeString());
                    }
                case "Size":
                    if (nowSort == Configuration.ASC) {
                        return (int) (o1.getSaoInt() - o2.getSaoInt());
                    } else {
                        return (int) (o2.getSaoInt() - o1.getSaoInt());
                    }
                case "Số lượng bán ra":
                    if (nowSort == Configuration.ASC) {
                        return o1.getSo_luong_ban_ra() - o2.getSo_luong_ban_ra();
                    } else {
                        return o2.getSo_luong_ban_ra() - o1.getSo_luong_ban_ra();
                    }
                default:
                    if (nowSort == Configuration.ASC) {
                        return o1.getSoLuongSanPham() - o2.getSoLuongSanPham();
                    } else {
                        return o2.getSoLuongSanPham() - o1.getSoLuongSanPham();
                    }
            }
        });
        sanPhamAdapter.notifyDataSetChanged();
    }

    public void show() {
        String search = this.search.getText().toString().toLowerCase();
        String sort = spinner.getSelectedItem().toString();
        showArray.clear();
        for (SanPham sanPham : rootArray) {
            boolean isOKe = false;
            switch (sort) {
                case "Ngày tạo":
                    if (sanPham.getNgayTao() != null) {
                        if (sanPham.getNgayTao().toStringDateTypeNumberStringNumber().toLowerCase().contains(search)) {
                            isOKe = true;
                        }
                    } else {
                        isOKe = true;
                    }
                    break;
                case "Tên sản phẩm":
                    if (sanPham.getTenSanPham().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Mã sản phẩm":
                    if (sanPham.getMaSanPham().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Tên danh mục":
                    if (sanPham.getDanhMucSanPham().getTenDanhMuc().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Giá":
                    if (search.trim().length() == 0) {
                        isOKe = true;
                    } else {
                        String s = String.format("%,.0f", (double) sanPham.getGia()) + "";
                        if (s.contains(search)) {
                            isOKe = true;
                        }
                    }
                    break;
                case "Giá khuyến mãi":
                    if (search.trim().length() == 0) {
                        isOKe = true;
                    } else {
                        String s = String.format("%,.0f", (double) sanPham.getGia_km()) + "";
                        if (s.contains(search)) {
                            isOKe = true;
                        }
                    }
                    break;
                case "Kcal":
                    if (search.trim().length() == 0) {
                        isOKe = true;
                    } else {
                        String s = sanPham.getKcal() + "";
                        if (s.contains(search)) {
                            isOKe = true;
                        }
                    }
                    break;
                case "Thời gian nấu":
                    if (search.trim().length() == 0) {
                        isOKe = true;
                    } else {
                        String s = sanPham.getThoi_gian_nau() + "";
                        if (s.contains(search)) {
                            isOKe = true;
                        }
                    }
                    break;
                case "Số đánh giá":
                    if (search.trim().length() == 0) {
                        isOKe = true;
                    } else {
                        String s = sanPham.getDanhGiaSanPhams().size() + "";
                        if (s.contains(search)) {
                            isOKe = true;
                        }
                    }
                    break;
                case "Số sao":
                    if (search.trim().length() == 0) {
                        isOKe = true;
                    } else {
                        String s = String.format("%,.1f", sanPham.getSaoInt()) + "";
                        if (s.contains(search)) {
                            isOKe = true;
                        }
                    }
                    break;
                case "Size":
                    if (sanPham.getSizeString().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Số lượng bán ra":
                    if (search.trim().length() == 0) {
                        isOKe = true;
                    } else {
                        String s = sanPham.getSo_luong_ban_ra() + "";
                        if (s.contains(search)) {
                            isOKe = true;
                        }
                    }
                    break;
                case "Số lượng sản phẩm":
                    if (search.trim().length() == 0) {
                        isOKe = true;
                    } else {
                        String s = sanPham.getSoLuongSanPham() + "";
                        if (s.contains(search)) {
                            isOKe = true;
                        }
                    }
                    break;
            }
            if (isOKe) {
                showArray.add(new SanPham(sanPham));
            }
        }
        updateButtonXoa();
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


}