package com.vientamthuong.eatsimple.admin.header;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.Configuration;
import com.vientamthuong.eatsimple.fontAwesome.FontAwesomeManager;
import com.vientamthuong.eatsimple.login.activity_login;

public class HeaderFragment extends Fragment {

    private TextView icon;
    private TextView iconHome;
    private TextView iconDanhMuc;
    private TextView iconMaGiamGia;
    private TextView iconLogout;
    private int nowSelect;

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
        iconLogout.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), activity_login.class);
            getActivity().finish();
            startActivity(intent);
        });
    }

    private void init() {
        FontAwesomeManager.getInstance().addIcon(icon, "fas", "\uf6d5", getActivity());
        FontAwesomeManager.getInstance().addIcon(iconHome, "fas", "\uf815", getActivity());
        FontAwesomeManager.getInstance().addIcon(iconDanhMuc, "fas", "\uf1b3", getActivity());
        FontAwesomeManager.getInstance().addIcon(iconMaGiamGia, "fas", "\uf02a", getActivity());
        FontAwesomeManager.getInstance().addIcon(iconLogout, "fas", "\uf2f5", getActivity());
    }

    private void moveTo(View view) {
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
                break;
            case Configuration.DANH_MUC:
                iconDanhMuc.setTextColor(getActivity().getColor(R.color.white));
                iconDanhMuc.setBackgroundResource(R.drawable.admin_background_select);
                break;
            case Configuration.MA_GIAM_GIA:
                iconMaGiamGia.setTextColor(getActivity().getColor(R.color.white));
                iconMaGiamGia.setBackgroundResource(R.drawable.admin_background_select);
                break;
        }
    }

    private void resetView() {
        iconHome.setTextColor(getActivity().getColor(R.color.color_admin_main));
        iconHome.setBackground(null);
        iconDanhMuc.setTextColor(getActivity().getColor(R.color.color_admin_main));
        iconDanhMuc.setBackground(null);
        iconMaGiamGia.setTextColor(getActivity().getColor(R.color.color_admin_main));
        iconMaGiamGia.setBackground(null);
    }

    private void getView(View view) {
        icon = view.findViewById(R.id.icon);
        iconHome = view.findViewById(R.id.icon_home);
        iconDanhMuc = view.findViewById(R.id.icon_danhMuc);
        iconMaGiamGia = view.findViewById(R.id.icon_MaGiamGia);
        iconLogout = view.findViewById(R.id.icon_logout);
    }
}
