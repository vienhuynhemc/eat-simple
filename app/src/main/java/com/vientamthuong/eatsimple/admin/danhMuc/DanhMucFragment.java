package com.vientamthuong.eatsimple.admin.danhMuc;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.configuration.Configuration;
import com.vientamthuong.eatsimple.admin.HomePageActivity;
import com.vientamthuong.eatsimple.admin.configuration.WebService;
import com.vientamthuong.eatsimple.admin.dialog.DiaLogConfirm;
import com.vientamthuong.eatsimple.admin.loadData.LoadData;
import com.vientamthuong.eatsimple.admin.model.DanhMuc;
import com.vientamthuong.eatsimple.admin.model.MainFragment;
import com.vientamthuong.eatsimple.admin.session.DataSession;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.protocol.ActivityProtocol;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DanhMucFragment extends Fragment implements MainFragment {

    private Spinner spinner;
    private List<String> listType;
    private ImageView sort;
    private EditText search;
    private RecyclerView recyclerView;
    private DanhMucAdapter danhMucAdapter;
    private List<DanhMuc> rootArray;
    private List<DanhMuc> showArray;
    private FloatingActionButton buttonThem;
    private FloatingActionButton buttonXoa;
    private FloatingActionButton buttonAll;
    private int nowSort;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_danh_muc_home, container, false);
        getView(view);
        init();
        action();
        return view;
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
                for (DanhMuc danhMuc : showArray) {
                    danhMuc.setChonXoa(false);
                    for (DanhMuc d : rootArray) {
                        if (d.getMaDanhMuc().equals(danhMuc.getMaDanhMuc())) {
                            d.setChonXoa(danhMuc.isChonXoa());
                            break;
                        }
                    }
                }
                buttonXoa.setVisibility(View.GONE);
                buttonAll.setImageResource(R.drawable.admin_fragment_danh_muc_done_all);
            } else {
                for (DanhMuc danhMuc : showArray) {
                    danhMuc.setChonXoa(true);
                    for (DanhMuc d : rootArray) {
                        if (d.getMaDanhMuc().equals(danhMuc.getMaDanhMuc())) {
                            d.setChonXoa(danhMuc.isChonXoa());
                            break;
                        }
                    }
                }
                buttonXoa.setVisibility(View.VISIBLE);
                buttonAll.setImageResource(R.drawable.admin_fragment_danh_muc_clear_all);
            }
            danhMucAdapter.notifyDataSetChanged();
        });
        buttonThem.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.f2, new DanhMucAddFragment(), "add");
            fragmentTransaction.commit();
        });
        buttonXoa.setOnClickListener(v -> {
            List<String> ma_danh_mucs = new ArrayList<>();
            for (DanhMuc danhMuc : showArray) {
                if (danhMuc.isChonXoa()) {
                    ma_danh_mucs.add(danhMuc.getMaDanhMuc());
                }
            }
            DiaLogConfirm diaLogConfirm = new DiaLogConfirm(getActivity());
            diaLogConfirm.getTextViewTitle().setText("Xóa nhiều danh mục");
            diaLogConfirm.getTextViewContent().setText("Bạn có chắc chắn rằng mình muốn xóa " + ma_danh_mucs.size() + " danh mục đã chọn không?");
            diaLogConfirm.getBtTry().setText("Không");
            diaLogConfirm.getBtIgnore().setText("Xóa");
            diaLogConfirm.getBtTry().setOnClickListener(v1 -> diaLogConfirm.dismiss());
            diaLogConfirm.getBtIgnore().setOnClickListener(v1 -> {
                diaLogConfirm.dismiss();
                for (String ma_danh_muc : ma_danh_mucs) {
                    // Firebase
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference root = firebaseDatabase.getReference();
                    root.child("danh_muc").child(ma_danh_muc).child("ton_tai").setValue("1");
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
                    VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
                }
                Toast.makeText(getActivity(), "Xóa thành công " + ma_danh_mucs.size() + " danh mục", Toast.LENGTH_SHORT).show();
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
                        map.put("noi_dung", "vừa xóa " + ma_danh_mucs.size() + " danh mục có ID lần lượt là ");
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
                params.put("noi_dung", "vừa xóa " + ma_danh_mucs.size() + " danh mục có ID lần lượt là ");
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
            showArray.add(new DanhMuc(null, null, null, -1, false, null, null));
            rootArray.add(new DanhMuc(null, null, null, -1, false, null, null));
        }
        danhMucAdapter = new DanhMucAdapter(R.layout.admin_view_holder_item_danh_muc, showArray, rootArray,
                this);
        recyclerView.setAdapter(danhMucAdapter);
        danhMucAdapter.notifyDataSetChanged();
    }

    private void initSpinner() {
        listType = new ArrayList<>(Arrays.asList("Ngày tạo", "Tên danh mục", "Mã danh mục", "Số lượng sản phẩm"));
        SelectAdapter selectAdapter = new SelectAdapter(getActivity(), R.layout.admin_view_holder_select, listType);
        spinner.setAdapter(selectAdapter);
    }

    @Override
    public void getData(DatabaseReference root, DiaLogLoader diaLogLoader, List<LoadImageForView> imagesNeedLoad, AppCompatActivity appCompatActivity) {
        // Lay activity protocol
        ActivityProtocol activityProtocol = (ActivityProtocol) appCompatActivity;
        // load
        DatabaseReference databaseReferenceDanhMuc = root.child("danh_muc");
        databaseReferenceDanhMuc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Hiện màn hình chờ
                diaLogLoader.show();
                // Làm cho 4 thằng đầu tiền full null
                rootArray.clear();
                for (int i = 0; i < 10; i++) {
                    rootArray.add(new DanhMuc(null, null, null, -1, false, null, null));
                }
                danhMucAdapter.notifyDataSetChanged();
                // Biến count để biết có bao nhiêu thằng
                int countDanhMuc = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int ton_tai = Integer.parseInt(dataSnapshot.child("ton_tai").getValue().toString());
                    if (ton_tai == 0) {
                        countDanhMuc++;
                        DanhMuc danhMuc = new DanhMuc(dataSnapshot.getKey(),
                                dataSnapshot.child("ten").getValue().toString(),
                                new DateTime(dataSnapshot.child("ngay_tao").getValue().toString()),
                                -1, false,
                                dataSnapshot.child("hinh").getValue().toString(),
                                null
                        );
                        danhMuc.setHinh_fb(dataSnapshot.child("hinh_fb").getValue().toString());
                        // Nếu như chưa lớn hơn thì thay vì add vào ta thay đổi thuộc tính của nó
                        if (countDanhMuc < 11) {
                            rootArray.set(countDanhMuc - 1, new DanhMuc(danhMuc));
                        } else {
                            rootArray.add(danhMuc);
                        }
                        danhMucAdapter.notifyDataSetChanged();
                    }
                }
                // Nếu như số danh mục < 4 (mặc định )thì xóa bớt
                if (countDanhMuc < 10) {
                    int count = 0;
                    while (count < rootArray.size()) {
                        if (rootArray.get(count).getMaDanhMuc() == null) {
                            rootArray.remove(count);
                        } else {
                            count++;
                        }
                    }
                }
                // Tải dữ liệu từ firebase về thành công
                // Đưa vô imageNeedLoad
                for (DanhMuc danhMuc : rootArray) {
                    imagesNeedLoad.add(new LoadImageForView(appCompatActivity, danhMuc, LoadDataConfiguration.DANH_MUC_ADMIN));
                }
                // Show
                show();
                sort();
                // Đếm số sản phẩm
                countSl();
                // Và giờ tải hình từ các link hình
                if (!activityProtocol.isRunningVolley()) {
                    activityProtocol.setRunningVolley(true);
                    activityProtocol.loadImageFromIntenet();
                }
                // Tắt màn hình chờ
                diaLogLoader.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(appCompatActivity, "Lỗi tải dữ liệu từ firebase !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void countSl() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.get_so_san_pham_danh_muc,
                response -> {
                    try {
                        Map<String, Integer> map = new HashMap<>();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String ma_danh_muc = jsonObject.getString("ma_danh_muc");
                            int so_luong = jsonObject.getInt("so_luong");
                            if(map.containsKey(ma_danh_muc)){
                                map.put(ma_danh_muc,map.get(ma_danh_muc)+so_luong);
                            }else{
                                map.put(ma_danh_muc,so_luong);
                            }
                        }
                        updateSl(map);
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

    private void updateSl(Map<String, Integer> map) {
        for (DanhMuc danhMuc : rootArray) {
            danhMuc.setSoSanPham(-1);
        }
        for (DanhMuc danhMuc : showArray) {
            danhMuc.setSoSanPham(-1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String ma_danh_muc = entry.getKey();
            int sl = entry.getValue();
            for (DanhMuc danhMuc : rootArray) {
                if (danhMuc.getMaDanhMuc().equals(ma_danh_muc)) {
                    danhMuc.setSoSanPham(sl);
                    break;
                }
            }
            for (DanhMuc danhMuc : showArray) {
                if (danhMuc.getMaDanhMuc().equals(ma_danh_muc)) {
                    danhMuc.setSoSanPham(sl);
                    danhMucAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
        for (DanhMuc danhMuc : rootArray) {
            if (danhMuc.getSoSanPham() == -1) {
                danhMuc.setSoSanPham(0);
            }
        }
        for (DanhMuc danhMuc : showArray) {
            if (danhMuc.getSoSanPham() == -1) {
                danhMuc.setSoSanPham(0);
            }
        }
        danhMucAdapter.notifyDataSetChanged();
    }

    @Override
    public void update() {
        for (DanhMuc danhMuc : showArray) {
            for (DanhMuc d : rootArray) {
                if (d.getMaDanhMuc() != null && danhMuc.getMaDanhMuc().equals(d.getMaDanhMuc())) {
                    danhMuc.setHinh(d.getHinh());
                    break;
                }
            }
        }
        danhMucAdapter.notifyDataSetChanged();
    }

    private void show() {
        String search = this.search.getText().toString().toLowerCase();
        String sort = spinner.getSelectedItem().toString();
        showArray.clear();
        for (DanhMuc danhMuc : rootArray) {
            boolean isOKe = false;
            switch (sort) {
                case "Ngày tạo":
                    if (danhMuc.getNgayTao() != null) {
                        if (danhMuc.getNgayTao().toStringDateTypeNumberStringNumber().toLowerCase().contains(search)) {
                            isOKe = true;
                        }
                    } else {
                        isOKe = true;
                    }
                    break;
                case "Tên danh mục":
                    if (danhMuc.getTenDanhMuc().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Mã danh mục":
                    if (danhMuc.getMaDanhMuc().toLowerCase().contains(search)) {
                        isOKe = true;
                    }
                    break;
                case "Số lượng sản phẩm":
                    try {
                        int a = Integer.parseInt(search);
                        if (a == danhMuc.getSoSanPham()) {
                            isOKe = true;
                        }
                    } catch (Exception e) {
                        isOKe = false;
                    }
                    break;
            }
            if (isOKe) {
                showArray.add(new DanhMuc(danhMuc));
            }
        }
        updateButtonXoa();
    }

    public void updateButtonXoa() {
        int count = 0;
        for (DanhMuc danhMuc : showArray) {
            if (danhMuc.isChonXoa()) {
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
                case "Tên danh mục":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getTenDanhMuc(), o2.getTenDanhMuc());
                    } else {
                        return compareToString(o2.getTenDanhMuc(), o1.getTenDanhMuc());
                    }
                case "Mã danh mục":
                    if (nowSort == Configuration.ASC) {
                        return compareToString(o1.getMaDanhMuc(), o2.getMaDanhMuc());
                    } else {
                        return compareToString(o2.getMaDanhMuc(), o1.getMaDanhMuc());
                    }
                default:
                    if (nowSort == Configuration.ASC) {
                        return o1.getSoSanPham() - o2.getSoSanPham();
                    } else {
                        return o2.getSoSanPham() - o1.getSoSanPham();
                    }
            }
        });
        danhMucAdapter.notifyDataSetChanged();
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
