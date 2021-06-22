package com.vientamthuong.eatsimple.homePage;

import android.os.Bundle;
import android.os.Handler;
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
import com.vientamthuong.eatsimple.model.DanhMuc;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.protocol.ActivityProtocol;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePageDanhMucFragment extends Fragment {

    // List danh muc
    private List<DanhMuc> danhMucs;
    private RecyclerView recyclerViewDanhMuc;
    private CustomDanhMucAdapter customDanhMucAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homme_page_danh_muc, container, false);
        getView(view);
        init();
        return view;
    }

    public void update() {
        customDanhMucAdapter.notifyDataSetChanged();
    }

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
                danhMucs.clear();
                for (int i = 0; i < 4; i++) {
                    danhMucs.add(new DanhMuc(null, null, null));
                }
                customDanhMucAdapter.notifyDataSetChanged();
                // Biến count để biết có bao nhiêu thằng
                int countDanhMuc = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int ton_tai = Integer.parseInt(dataSnapshot.child("ton_tai").getValue().toString());
                    if (ton_tai == 0) {
                        countDanhMuc++;
                        DanhMuc danhMuc = new DanhMuc(dataSnapshot.getKey());
                        danhMuc.setHinh(Objects.requireNonNull(dataSnapshot.child("hinh").getValue()).toString());
                        danhMuc.setTen_danh_muc(Objects.requireNonNull(dataSnapshot.child("ten").getValue()).toString());
                        // Nếu như chưa lớn hơn thì thay vì add vào ta thay đổi thuộc tính của nó
                        if (countDanhMuc < 5) {
                            danhMucs.get(countDanhMuc - 1).setTen_danh_muc(danhMuc.getTen_danh_muc());
                            danhMucs.get(countDanhMuc - 1).setHinh(danhMuc.getHinh());
                            danhMucs.get(countDanhMuc - 1).setMa_danh_muc(danhMuc.getMa_danh_muc());
                        } else {
                            danhMucs.add(danhMuc);
                        }
                        customDanhMucAdapter.notifyDataSetChanged();
                    }
                }
                // Nếu như số danh mục < 4 (mặc định )thì xóa bớt
                if (countDanhMuc < 4) {
                    int count = 0;
                    while (count < danhMucs.size()) {
                        if (!danhMucs.get(count).isLoaded()) {
                            danhMucs.remove(count);
                        } else {
                            count++;
                        }
                    }
                }
                // Tải dữ liệu từ firebase về thành công
                // Đưa vô imageNeedLoad
                for (DanhMuc danhMuc : danhMucs) {
                    imagesNeedLoad.add(new LoadImageForView(appCompatActivity, danhMuc, LoadDataConfiguration.IMAGE_DANH_MUC));
                }
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

    private void init() {
        danhMucs = new ArrayList<>();
        // Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerViewDanhMuc.setLayoutManager(linearLayoutManager);
        recyclerViewDanhMuc.setHasFixedSize(true);
        // adapter
        // Resource
        int[] resources = {R.layout.activity_home_page_custom_danh_muc_first,
                R.layout.activity_home_page_custom_danh_muc,
                R.layout.activity_home_page_custom_danh_muc_last};
        // Tạo 4 object loader ban đầu
        // Cho full tam số là null
        // Khi nạp dữ liệu từ fire base thì trải qua các bước
        // 1. Thay vì clear ta xóa hết để lại 4 thằng đầu xong cho full thuộc tính nó là null
        for (int i = 0; i < 4; i++) {
            danhMucs.add(new DanhMuc(null, null, null));
        }
        // 2. Sau đó cứ có dữ liệu thì lần lượt thay thế 4 ông này , nếu như có ít hơn 4 thì ta xóa
        // Ngược lại nhiều hơn 4 thì thêm vào
        // Nhưng ở trường hợp thiếu khi xóa đi thì lúc nạp vào ta vẫn phải làm sao để có được 4 thằng
        customDanhMucAdapter = new CustomDanhMucAdapter(resources, danhMucs);
        recyclerViewDanhMuc.setAdapter(customDanhMucAdapter);
        customDanhMucAdapter.notifyDataSetChanged();
    }

    private void getView(View view) {
        // recyclerview danh mục
        recyclerViewDanhMuc = view.findViewById(R.id.activity_home_page_list_danh_muc);
    }
    public void setHander(Handler hander){
        customDanhMucAdapter.setHandler(hander);
    }

}
