package com.vientamthuong.eatsimple.admin.header;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.notification.ThongBaoNoiFragment;
import com.vientamthuong.eatsimple.admin.session.DataSession;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.fontAwesome.FontAwesomeManager;
import com.vientamthuong.eatsimple.loadData.LoadDataConfiguration;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.protocol.ActivityProtocol;

import java.util.List;

public class TopHeaderFragment extends Fragment {

    private TextView icon;
    private CardView cardViewIcon;
    private CardView tenHienThiLottie;
    private CardView capDoLottie;
    private CardView hinhDaiDienLottie;
    private TextView tenHienThi;
    private TextView capDo;
    private ImageView hinhDaiDien;
    private ConstraintLayout constraintLayout;
    // Thông báo nổi
    private ThongBaoNoiFragment thongBaoNoiFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_top_header, container, false);
        getView(view);
        init();
        action();
        return view;
    }

    private void getView(View view) {
        icon = view.findViewById(R.id.icon);
        cardViewIcon = view.findViewById(R.id.cardViewIcon);
        tenHienThiLottie = view.findViewById(R.id.ten_hien_thi_lottie);
        tenHienThi = view.findViewById(R.id.ten_hien_thi);
        capDoLottie = view.findViewById(R.id.cap_do_lottie);
        capDo = view.findViewById(R.id.cap_do);
        hinhDaiDienLottie = view.findViewById(R.id.hinh_dai_dien_lottie);
        hinhDaiDien = view.findViewById(R.id.hinh_dai_dien);
        constraintLayout = view.findViewById(R.id.layout);
    }

    public void getData(DatabaseReference root, DiaLogLoader diaLogLoader, List<LoadImageForView> imagesNeedLoad, AppCompatActivity appCompatActivity) {
        // Lay activity protocol
        ActivityProtocol activityProtocol = (ActivityProtocol) appCompatActivity;
        // Load hình cho top header
        DatabaseReference databaseHomePage = root.child("tai_khoan").child(DataSession.getInstance().getMa_tai_khoan());
        databaseHomePage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Hiện màn hình chờ
                diaLogLoader.show();
                imagesNeedLoad.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals("link_hinh_dai_dien")) {
                        String url = dataSnapshot.getValue().toString();
                        LoadImageForView loadImageForView = new LoadImageForView(url, appCompatActivity, hinhDaiDien, LoadDataConfiguration.TOP_HEADER_ADMIN, hinhDaiDienLottie);
                        imagesNeedLoad.add(loadImageForView);
                    } else if (dataSnapshot.getKey().equals("ten_hien_thi")) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(constraintLayout);
                        constraintSet.clear(R.id.hinh_dai_dien_lottie, ConstraintSet.END);
                        constraintSet.connect(R.id.hinh_dai_dien_lottie, ConstraintSet.END, R.id.ten_hien_thi, ConstraintSet.START, 10);
                        constraintSet.applyTo(constraintLayout);
                        tenHienThi.setVisibility(View.VISIBLE);
                        tenHienThi.setText(dataSnapshot.getValue().toString());
                        tenHienThiLottie.setVisibility(View.GONE);
                        setNameCapDo();
                    }
                }
                // Tải dữ liệu từ firebase về thành công
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

    private void setNameCapDo() {
        capDo.setVisibility(View.VISIBLE);
        capDoLottie.setVisibility(View.GONE);
        String sCapDo = "";
        switch (DataSession.getInstance().getCap_do()) {
            case 0:
                sCapDo = "Admin";
                break;
            case 1:
                sCapDo = "Nhân viên nấu nướng";
                break;
            case 2:
                sCapDo = "Nhân viên giao hàng";
                break;
        }
        capDo.setText(sCapDo);
    }

    private void init() {
        FontAwesomeManager.getInstance().addIcon(icon, "far", "\uf0f3", getActivity());
        // Tạo thông báo nổi fragment
        initThongBaoNoi();
    }

    private void initThongBaoNoi() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        thongBaoNoiFragment = new ThongBaoNoiFragment(getActivity().findViewById(R.id.thong_bao_mui_ten),
                getActivity().findViewById(R.id.thong_bao_cardView));
        fragmentTransaction.replace(R.id.thong_bao_frame, thongBaoNoiFragment, "thong-bao-noi");
        fragmentTransaction.commit();
    }

    private void action(){
        cardViewIcon.setOnClickListener(v -> thongBaoNoiFragment.handleShowHide());
    }

}
