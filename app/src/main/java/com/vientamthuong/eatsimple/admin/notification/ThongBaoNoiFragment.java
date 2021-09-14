package com.vientamthuong.eatsimple.admin.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.vientamthuong.eatsimple.admin.model.ThongBaoChuong;
import com.vientamthuong.eatsimple.admin.session.DataSession;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.protocol.ActivityProtocol;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ThongBaoNoiFragment extends Fragment {

    private TextView thong_bao_noi_mui_ten;
    private CardView thong_bao_noi_card_view;
    private TextView textViewNotFound;
    private boolean isShow;
    private RecyclerView recyclerView;
    private List<ThongBaoChuong> thongBaoChuongs;
    private ThongBaoNoiFragmentCustomAdapter thongBaoNoiFragmentCustomAdapter;
    // Biến để xem dừng của firebase
    private int count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_thong_bao_noi, container, false);
        getView(view);
        init();
        return view;
    }

    private void getView(View view) {
        thong_bao_noi_mui_ten = getActivity().findViewById(R.id.thong_bao_mui_ten);
        thong_bao_noi_card_view = getActivity().findViewById(R.id.thong_bao_cardView);
        textViewNotFound = view.findViewById(R.id.textView_not_found);
        recyclerView = view.findViewById(R.id.views);
    }

    private void init() {
        thongBaoChuongs = new ArrayList<>();
        // Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        int[] resources = {R.layout.admin_fragment_thong_bao_noi_view_holder, R.layout.admin_fragment_thong_bao_noi_view_holder_end};
        for (int i = 0; i < 10; i++) {
            thongBaoChuongs.add(new ThongBaoChuong(null, null, null, null));
        }
        thongBaoNoiFragmentCustomAdapter = new ThongBaoNoiFragmentCustomAdapter(resources, thongBaoChuongs);
        recyclerView.setAdapter(thongBaoNoiFragmentCustomAdapter);
        thongBaoNoiFragmentCustomAdapter.notifyDataSetChanged();

        // Sẵn sàng
        if (!LoadData.getInstance().isReadyFromThongBaoNoi()) {
            LoadData.getInstance().setReadyFromThongBaoNoi(true);
            // Tới đây thì mọi thứ đã sẵn sàng cho header và ta tải dữ liệu cho header
            HomePageActivity homePageActivity = (HomePageActivity) getActivity();
            homePageActivity.getDataHeader();
        }
    }

    public void handleShowHide() {
        if (isShow) {
            isShow = false;
            thong_bao_noi_card_view.setVisibility(View.GONE);
            thong_bao_noi_mui_ten.setVisibility(View.GONE);
        } else {
            isShow = true;
            thong_bao_noi_card_view.setVisibility(View.VISIBLE);
            thong_bao_noi_mui_ten.setVisibility(View.VISIBLE);
        }
    }

    public void getData(DatabaseReference root, DiaLogLoader diaLogLoader, List<LoadImageForView> imagesNeedLoad, AppCompatActivity appCompatActivity) {
        // Lay activity protocol
        ActivityProtocol activityProtocol = (ActivityProtocol) appCompatActivity;
        // load
        DatabaseReference thong_bao_chuong = root.child("thong_bao_chuong");
        thong_bao_chuong.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Hiện màn hình chờ và đến khi nào thằng cuối cùng xong thì tắt
                diaLogLoader.show();
                // Làm cho 10 thằng đầu tiền full null
                thongBaoChuongs.clear();
                count = 0;
                for (int i = 0; i < 10; i++) {
                    thongBaoChuongs.add(new ThongBaoChuong(null, null, null, null));
                }
                thongBaoNoiFragmentCustomAdapter.notifyDataSetChanged();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String ma_thong_bao_chuong = dataSnapshot.getKey();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getValue().toString().equals(DataSession.getInstance().getMa_tai_khoan())) {
                            // Điền các thông tin trong bảng chi_tiet_thong_bao_chuong
                            count++;
                            int index = 0;
                            for (ThongBaoChuong thongBaoChuong : thongBaoChuongs) {
                                if (thongBaoChuong.getMa_thong_bao_chuong() == null) {
                                    break;
                                } else {
                                    index++;
                                }
                            }
                            if (index == thongBaoChuongs.size()) {
                                thongBaoChuongs.add(new ThongBaoChuong(null, null, null, null));
                            }
                            thongBaoChuongs.get(index).setMa_thong_bao_chuong(ma_thong_bao_chuong);
                            break;
                        }
                    }
                }
                // Nếu ko có 1 thằng thì dừng
                if (count == 0) {
                    diaLogLoader.dismiss();
                    thongBaoChuongs.clear();
                    thongBaoNoiFragmentCustomAdapter.notifyDataSetChanged();
                    notFound();
                } else {
                    found();
                    // Nếu ít hơn 10 thì xóa
                    if (count < 10) {
                        int c = 0;
                        while (c < thongBaoChuongs.size()) {
                            if (thongBaoChuongs.get(c).getMa_thong_bao_chuong() == null) {
                                thongBaoChuongs.remove(c);
                            } else {
                                c++;
                            }
                        }
                    }
                    thongBaoNoiFragmentCustomAdapter.notifyDataSetChanged();
                    // Xóa xong thì duyệt qua và tải dữ liệu cho nó
                    for (ThongBaoChuong thongBaoChuong : thongBaoChuongs) {
                        fillDataChi_tiet_thong_bao_chuong(root, diaLogLoader, imagesNeedLoad, appCompatActivity, thongBaoChuong);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(appCompatActivity, "Lỗi tải dữ liệu từ firebase !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fillDataChi_tiet_thong_bao_chuong(DatabaseReference root,
                                                  DiaLogLoader diaLogLoader,
                                                  List<LoadImageForView> imagesNeedLoad,
                                                  AppCompatActivity appCompatActivity,
                                                  ThongBaoChuong thongBaoChuong) {
        DatabaseReference chi_tiet_thong_bao_chuong = root.child("chi_tiet_thong_bao_chuong");
        chi_tiet_thong_bao_chuong.child(thongBaoChuong.getMa_thong_bao_chuong()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                thongBaoChuong.setKieu_nguoi_gui(Integer.parseInt(snapshot.child("kieu_nguoi_gui").getValue().toString()));
                thongBaoChuong.setMa_nguoi_gui(snapshot.child("ma_nguoi_gui").getValue().toString());
                thongBaoChuong.setNgay_tao(new DateTime(snapshot.child("ngay_tao").getValue().toString()));
                thongBaoChuong.setNoi_dung(snapshot.child("noi_dung").getValue().toString());
                thongBaoNoiFragmentCustomAdapter.notifyDataSetChanged();
                fillDataKhachHang(root, diaLogLoader, imagesNeedLoad, appCompatActivity, thongBaoChuong);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void fillDataKhachHang(DatabaseReference root,
                                  DiaLogLoader diaLogLoader,
                                  List<LoadImageForView> imagesNeedLoad,
                                  AppCompatActivity appCompatActivity,
                                  ThongBaoChuong thongBaoChuong) {
        DatabaseReference tai_khoan = root.child("tai_khoan");
        tai_khoan.child(thongBaoChuong.getMa_nguoi_gui()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String url = snapshot.child("link_hinh_dai_dien").getValue().toString();
                String ten_hien_thi = snapshot.child("ten_hien_thi").getValue().toString();
                thongBaoChuong.setUrl(url);
                thongBaoChuong.setTen_nguoi_gui(ten_hien_thi);
                thongBaoNoiFragmentCustomAdapter.notifyDataSetChanged();
                // Tắt màn hình chờ nếu đủ điều kiện
                int valueDiffNull = 0;
                for (ThongBaoChuong thongBaoChuong : thongBaoChuongs) {
                    if (thongBaoChuong.getUrl() != null) {
                        valueDiffNull++;
                    }
                }
                if (valueDiffNull == count) {
                    diaLogLoader.dismiss();
                    // Sắp xếp lại theo giảm dần ngày
                    thongBaoChuongs.sort((o1, o2) -> {
                        long t1 = o1.getNgay_tao().getTime();
                        long t2 = o2.getNgay_tao().getTime();
                        if (t1 == t2) return 0;
                        return t1 > t2 ? -1 : 1;
                    });
                    // Xong từ firebase thì h tải hình từ internet về nào :v
                    ActivityProtocol activityProtocol = (ActivityProtocol) appCompatActivity;
                    for (ThongBaoChuong thongBaoChuong : thongBaoChuongs) {
                        imagesNeedLoad.add(new LoadImageForView(appCompatActivity, thongBaoChuong, LoadDataConfiguration.IMAGE_THONG_BAO_CHUONG));
                    }
                    if (!activityProtocol.isRunningVolley()) {
                        activityProtocol.setRunningVolley(true);
                        activityProtocol.loadImageFromIntenet();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void found() {
        textViewNotFound.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void notFound() {
        recyclerView.setVisibility(View.INVISIBLE);
        textViewNotFound.setVisibility(View.VISIBLE);
    }


    public void update() {
        thongBaoNoiFragmentCustomAdapter.notifyDataSetChanged();
    }
}
