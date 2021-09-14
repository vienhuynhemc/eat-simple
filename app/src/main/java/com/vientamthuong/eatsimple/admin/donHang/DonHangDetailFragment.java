package com.vientamthuong.eatsimple.admin.donHang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;

import java.util.ArrayList;
import java.util.List;

public class DonHangDetailFragment extends Fragment {

    private CardView buttonBack;
    private DonHangAdapter2 donHangAdapter2;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_don_hang_add, container, false);
        getView(view);
        action();
        return view;
    }

    private void getView(View view) {
        buttonBack = view.findViewById(R.id.buttonBack);
        recyclerView = view.findViewById(R.id.view_child);
        // Layout manager
        donHangAdapter2 = new DonHangAdapter2(DonHangSession.getInstance().getDonHang().getSanPhamDonHangs());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(donHangAdapter2);
    }

    private void action() {
        buttonBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            List<Fragment> fragments = fragmentManager.getFragments();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragments.get(fragments.size() - 1));
            fragmentTransaction.commit();
        });
    }
}
