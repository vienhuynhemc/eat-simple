package com.vientamthuong.eatsimple.admin.danhMuc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.vientamthuong.eatsimple.admin.Configuration;
import com.vientamthuong.eatsimple.admin.HomePageActivity;
import com.vientamthuong.eatsimple.admin.loadData.LoadData;
import com.vientamthuong.eatsimple.admin.model.DanhMuc;
import com.vientamthuong.eatsimple.admin.model.MainFragment;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.protocol.ActivityProtocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DanhMucFragment extends Fragment implements MainFragment {

    private Spinner spinner;
    private ImageView sort;
    private EditText search;
    private RecyclerView recyclerView;
    private DanhMucAdapter danhMucAdapter;
    private List<DanhMuc> rootArray;
    private List<DanhMuc> showArray;
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
        });
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
        danhMucAdapter = new DanhMucAdapter(R.layout.admin_view_holder_item_danh_muc, showArray, rootArray, getActivity());
        recyclerView.setAdapter(danhMucAdapter);
        danhMucAdapter.notifyDataSetChanged();
    }

    private void initSpinner() {
        List<String> listType = new ArrayList<>(Arrays.asList("Ngày tạo", "Tên danh mục", "Mã danh mục", "Số lượng sản phẩm"));
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
                        // Nếu như chưa lớn hơn thì thay vì add vào ta thay đổi thuộc tính của nó
                        if (countDanhMuc < 10) {
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

    @Override
    public void update() {
        for (DanhMuc danhMuc : showArray) {
            for (DanhMuc d : rootArray) {
                if (danhMuc.getMaDanhMuc().equals(d.getMaDanhMuc())) {
                    danhMuc.setHinh(d.getHinh());
                    break;
                }
            }
        }
        danhMucAdapter.notifyDataSetChanged();
    }

    private void show() {
        String search = this.search.getText().toString();
        String sort = spinner.getSelectedItem().toString();
        showArray.clear();
        for(DanhMuc danhMuc : rootArray){
            showArray.add(new DanhMuc(danhMuc));
        }
        danhMucAdapter.notifyDataSetChanged();
    }

}
