package com.vientamthuong.eatsimple.admin.header;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.configuration.Configuration;
import com.vientamthuong.eatsimple.admin.danhMuc.DanhMucFragment;
import com.vientamthuong.eatsimple.admin.dialog.DiaLogConfirm;
import com.vientamthuong.eatsimple.admin.homePage.HomePageFragment;
import com.vientamthuong.eatsimple.admin.loadData.LoadData;
import com.vientamthuong.eatsimple.admin.maGiamGia.MaGiamGiaFragment;
import com.vientamthuong.eatsimple.admin.model.MainFragment;
import com.vientamthuong.eatsimple.admin.thongTinCaNhan.ThongTinCaNhanFragment;
import com.vientamthuong.eatsimple.diaLog.DiaLogLoader;
import com.vientamthuong.eatsimple.fontAwesome.FontAwesomeManager;
import com.vientamthuong.eatsimple.loadData.LoadImageForView;
import com.vientamthuong.eatsimple.login.activity_login;

import java.util.List;

public class HeaderFragment extends Fragment {

    private TextView icon;
    private TextView iconHome;
    private TextView iconDanhMuc;
    private TextView iconMaGiamGia;
    private TextView iconLogout;
    private TextView iconThongTinCaNhan;
    private CardView cardViewLogout;
    private int nowSelect;
    private MainFragment mainFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_header, container, false);
        getView(view);
        init();
        handleSelect(getArguments().getInt("data"));
        action();
        return view;
    }

    private void action() {
        iconHome.setOnClickListener(v -> moveTo(iconHome));
        iconDanhMuc.setOnClickListener(v -> moveTo(iconDanhMuc));
        iconMaGiamGia.setOnClickListener(v -> moveTo(iconMaGiamGia));
        iconThongTinCaNhan.setOnClickListener(v -> moveTo(iconThongTinCaNhan));
        cardViewLogout.setOnClickListener(v -> {
            actionLogout();
        });
    }

    public void getData(DatabaseReference root, DiaLogLoader diaLogLoader, List<LoadImageForView> imagesNeedLoad, AppCompatActivity appCompatActivity) {
        mainFragment.getData(root, diaLogLoader, imagesNeedLoad, appCompatActivity);
    }

    private void init() {
        FontAwesomeManager.getInstance().addIcon(icon, "fas", "\uf6d5", getActivity());
        FontAwesomeManager.getInstance().addIcon(iconHome, "fas", "\uf815", getActivity());
        FontAwesomeManager.getInstance().addIcon(iconDanhMuc, "fas", "\uf1b3", getActivity());
        FontAwesomeManager.getInstance().addIcon(iconMaGiamGia, "fas", "\uf02a", getActivity());
        FontAwesomeManager.getInstance().addIcon(iconLogout, "fas", "\uf2f5", getActivity());
        FontAwesomeManager.getInstance().addIcon(iconThongTinCaNhan, "fas", "\uf505", getActivity());
    }

    private void actionLogout() {
        DiaLogConfirm diaLogConfirm = new DiaLogConfirm(getActivity());
        diaLogConfirm.getTextViewTitle().setText("Đăng xuất");
        diaLogConfirm.getTextViewContent().setText("Bạn có chắc muốn đăng xuất, nếu chỉ là ấn nhầm thì xin hãy chọn \"Không\"");
        diaLogConfirm.getBtTry().setText("Không");
        diaLogConfirm.getBtIgnore().setText("Có");
        diaLogConfirm.getBtTry().setOnClickListener(v -> diaLogConfirm.dismiss());
        diaLogConfirm.getBtIgnore().setOnClickListener(v -> {
            diaLogConfirm.dismiss();
            LoadData.getInstance().reset();
            Intent intent = new Intent();
            intent.setClass(getActivity(), activity_login.class);
            getActivity().finish();
            startActivity(intent);
        });
        diaLogConfirm.show();
    }

    private void moveTo(View view) {
        LoadData.getInstance().setReadyFromMainFragment(false);
        int nextSelect = -1;
        switch (view.getId()) {
            case R.id.icon_home:
                if (nowSelect != Configuration.HOME) {
                    nextSelect = Configuration.HOME;
                }
                break;
            case R.id.icon_danhMuc:
                if (nowSelect != Configuration.DANH_MUC) {
                    nextSelect = Configuration.DANH_MUC;
                }
                break;
            case R.id.icon_MaGiamGia:
                if (nowSelect != Configuration.MA_GIAM_GIA) {
                    nextSelect = Configuration.MA_GIAM_GIA;
                }
                break;
            case R.id.icon_ThongTinCaNhan:
                if (nowSelect != Configuration.THONG_TIN_CA_NHAN) {
                    nextSelect = Configuration.THONG_TIN_CA_NHAN;
                }
                break;
        }
        if (nextSelect != -1) {
            handleSelect(nextSelect);
        }
    }

    private void handleSelect(int select) {
        resetView();
        nowSelect = select;
        switch (select) {
            case Configuration.HOME:
                iconHome.setTextColor(getActivity().getColor(R.color.white));
                iconHome.setBackgroundResource(R.drawable.admin_background_select);
                HomePageFragment homePageFragment = new HomePageFragment();
                replaceFragment(homePageFragment);
                mainFragment = homePageFragment;
                break;
            case Configuration.DANH_MUC:
                iconDanhMuc.setTextColor(getActivity().getColor(R.color.white));
                iconDanhMuc.setBackgroundResource(R.drawable.admin_background_select);
                DanhMucFragment danhMucFragment = new DanhMucFragment();
                replaceFragment(danhMucFragment);
                mainFragment = danhMucFragment;
                break;
            case Configuration.MA_GIAM_GIA:
                iconMaGiamGia.setTextColor(getActivity().getColor(R.color.white));
                iconMaGiamGia.setBackgroundResource(R.drawable.admin_background_select);
                MaGiamGiaFragment maGiamGiaFragment = new MaGiamGiaFragment();
                replaceFragment(maGiamGiaFragment);
                mainFragment = maGiamGiaFragment;
                break;
            case Configuration.THONG_TIN_CA_NHAN:
                iconThongTinCaNhan.setTextColor(getActivity().getColor(R.color.white));
                iconThongTinCaNhan.setBackgroundResource(R.drawable.admin_background_select);
                ThongTinCaNhanFragment thongTinCaNhanFragment = new ThongTinCaNhanFragment();
                replaceFragment(thongTinCaNhanFragment);
                mainFragment = thongTinCaNhanFragment;
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.f2, fragment);
        fragmentTransaction.commit();
    }

    private void resetView() {
        iconHome.setTextColor(getActivity().getColor(R.color.color_admin_main));
        iconHome.setBackground(null);
        iconDanhMuc.setTextColor(getActivity().getColor(R.color.color_admin_main));
        iconDanhMuc.setBackground(null);
        iconMaGiamGia.setTextColor(getActivity().getColor(R.color.color_admin_main));
        iconMaGiamGia.setBackground(null);
        iconThongTinCaNhan.setTextColor(getActivity().getColor(R.color.color_admin_main));
        iconThongTinCaNhan.setBackground(null);
    }

    private void getView(View view) {
        icon = view.findViewById(R.id.icon);
        iconHome = view.findViewById(R.id.icon_home);
        iconDanhMuc = view.findViewById(R.id.icon_danhMuc);
        iconMaGiamGia = view.findViewById(R.id.icon_MaGiamGia);
        iconLogout = view.findViewById(R.id.icon_logout);
        cardViewLogout = view.findViewById(R.id.log_out);
        iconThongTinCaNhan = view.findViewById(R.id.icon_ThongTinCaNhan);
    }

    public void update() {
        mainFragment.update();
    }
}
