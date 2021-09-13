package com.vientamthuong.eatsimple.admin.homePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.HomePageActivity;
import com.vientamthuong.eatsimple.admin.loadData.LoadData;
import com.vientamthuong.eatsimple.admin.model.MainFragment;
import com.vientamthuong.eatsimple.admin.model.ThongBaoCaNhanTrangChu;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomePageFragment extends Fragment implements MainFragment {

    private RecyclerView recyclerView;
    private List<ThongBaoCaNhanTrangChu> thongBaoCaNhanTrangChus;
    private HomePageAdapter homePageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_home_page, container, false);
        getView(view);
        init();
        return view;
    }

    private void init() {
        thongBaoCaNhanTrangChus = new ArrayList<>();
        // Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        for (int i = 0; i < 10; i++) {
            thongBaoCaNhanTrangChus.add(new ThongBaoCaNhanTrangChu());
        }
        homePageAdapter = new HomePageAdapter(R.layout.admin_view_holder_home_page, getActivity(), thongBaoCaNhanTrangChus);
        recyclerView.setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();
        // ready
        if (!LoadData.getInstance().isReadyFromMainFragment()) {
            LoadData.getInstance().setReadyFromMainFragment(true);
            HomePageActivity homePageActivity = (HomePageActivity) getActivity();
            homePageActivity.getDataMainFragment();
        }
    }

    private void getView(View view) {
        recyclerView = view.findViewById(R.id.recyclerview);
    }

    @Override
    public void getData(DatabaseReference root, DiaLogLoader diaLogLoader, List<LoadImageForView> imagesNeedLoad, AppCompatActivity appCompatActivity) {
        // load
        DatabaseReference databaseReferenceDanhMuc = root.child("thong_bao_ca_nhan");
        databaseReferenceDanhMuc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Hiện màn hình chờ
                diaLogLoader.show();
                // Làm cho 10 thằng đầu tiền full null
                thongBaoCaNhanTrangChus.clear();
                for (int i = 0; i < 10; i++) {
                    thongBaoCaNhanTrangChus.add(new ThongBaoCaNhanTrangChu());
                }
                homePageAdapter.notifyDataSetChanged();
                // Biến count để biết có bao nhiêu thằng
                int countDanhMuc = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    countDanhMuc++;
                    ThongBaoCaNhanTrangChu thongBaoCaNhanTrangChu = new ThongBaoCaNhanTrangChu();
                    thongBaoCaNhanTrangChu.setNgay_tao(new DateTime(dataSnapshot.child("ngay_tao").getValue().toString()));
                    thongBaoCaNhanTrangChu.setNoi_dung(dataSnapshot.child("noi_dung").getValue().toString());
                    thongBaoCaNhanTrangChu.setNoi_dung_quan_trong(dataSnapshot.child("noi_dung_quan_trong").getValue().toString());
                    thongBaoCaNhanTrangChu.setType(Integer.parseInt(dataSnapshot.child("type").getValue().toString()));
                    thongBaoCaNhanTrangChu.setMa_nv_nhan(dataSnapshot.child("nv_nhan").getValue().toString());
                    thongBaoCaNhanTrangChu.setMa_nv_thuc_hien(dataSnapshot.child("nv_thuc_hien").getValue().toString());
                    // Nếu như chưa lớn hơn thì thay vì add vào ta thay đổi thuộc tính của nó
                    if (countDanhMuc < 11) {
                        thongBaoCaNhanTrangChus.set(countDanhMuc - 1, new ThongBaoCaNhanTrangChu(thongBaoCaNhanTrangChu));
                    } else {
                        thongBaoCaNhanTrangChus.add(thongBaoCaNhanTrangChu);
                    }
                    homePageAdapter.notifyDataSetChanged();
                }
                // Nếu như số danh mục < 4 (mặc định )thì xóa bớt
                if (countDanhMuc < 10) {
                    int count = 0;
                    while (count < thongBaoCaNhanTrangChus.size()) {
                        if (thongBaoCaNhanTrangChus.get(count).getMa_nv_thuc_hien() == null) {
                            thongBaoCaNhanTrangChus.remove(count);
                        } else {
                            count++;
                        }
                    }
                }
                // Tải dữ liệu từ firebase về thành công
                // Tắt màn hình chờ
                diaLogLoader.dismiss();
                // thêm tên
                root.child("tai_khoan").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        diaLogLoader.show();
                        for (DataSnapshot d : snapshot.getChildren()) {
                            for (ThongBaoCaNhanTrangChu t : thongBaoCaNhanTrangChus) {
                                if (d.getKey().equals(t.getMa_nv_nhan())) {
                                    t.setTen_nv_nhan(d.child("ten_hien_thi").getValue().toString());
                                }
                                if (d.getKey().equals(t.getMa_nv_thuc_hien())) {
                                    t.setTen_nv_thuc_hien(d.child("ten_hien_thi").getValue().toString());
                                }
                                homePageAdapter.notifyDataSetChanged();
                            }
                        }
                        diaLogLoader.dismiss();
                        // Thêm cấp độ
                        root.child("nhan_vien").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                diaLogLoader.show();
                                for (DataSnapshot d : snapshot.getChildren()) {
                                    for (ThongBaoCaNhanTrangChu t : thongBaoCaNhanTrangChus) {
                                        if (d.getKey().equals(t.getMa_nv_nhan())) {
                                            t.setCap_do_nv_nhan(getCapDo(d.child("cap_do").getValue().toString()));
                                        }
                                        if (d.getKey().equals(t.getMa_nv_thuc_hien())) {
                                            t.setCap_do_nv_thuc_hien(getCapDo(d.child("cap_do").getValue().toString()));
                                        }
                                        homePageAdapter.notifyDataSetChanged();
                                    }
                                }
                                diaLogLoader.dismiss();
                                thongBaoCaNhanTrangChus.sort((o1, o2) -> {
                                    long t1 = o1.getNgay_tao().getTime();
                                    long t2 = o2.getNgay_tao().getTime();
                                    if (t1 == t2) return 0;
                                    return t1 > t2 ? -1 : 1;
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(appCompatActivity, "Lỗi tải dữ liệu từ firebase !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCapDo(String s) {
        if (s.equals("0")) {
            return "Admin";
        } else if (s.equals("1")) {
            return "Nhân viên nấu nướng";
        } else {
            return "Nhân viên giao hàng";
        }
    }

    @Override
    public void update() {

    }
}
