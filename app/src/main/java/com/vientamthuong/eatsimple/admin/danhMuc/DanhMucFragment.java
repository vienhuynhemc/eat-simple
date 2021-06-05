package com.vientamthuong.eatsimple.admin.danhMuc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.Configuration;
import com.vientamthuong.eatsimple.admin.model.DanhMuc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DanhMucFragment extends Fragment {

    private Spinner spinner;
    private ImageView sort;
    private EditText search;
    private RecyclerView recyclerView;
    private DanhMucAdapter danhMucAdapter;
    private List<DanhMuc> root;
    private List<DanhMuc> show;
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
    }

    private void initRecyclerView() {
        root = new ArrayList<>();
        show = new ArrayList<>();
        // Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        for (int i = 0; i < 10; i++) {
            show.add(new DanhMuc(null, null, null, -1, false, null, null));
            root.add(new DanhMuc(null, null, null, -1, false, null, null));
        }
        danhMucAdapter = new DanhMucAdapter(R.layout.admin_view_holder_item_danh_muc, show, root, getActivity());
        recyclerView.setAdapter(danhMucAdapter);
        danhMucAdapter.notifyDataSetChanged();
    }

    private void initSpinner() {
        List<String> listType = new ArrayList<>(Arrays.asList("Ngày tạo", "Tên danh mục", "Mã danh mục", "Số lượng sản phẩm"));
        SelectAdapter selectAdapter = new SelectAdapter(getActivity(), R.layout.admin_view_holder_select, listType);
        spinner.setAdapter(selectAdapter);
    }
}
