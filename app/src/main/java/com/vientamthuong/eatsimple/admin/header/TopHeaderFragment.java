package com.vientamthuong.eatsimple.admin.header;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.fontAwesome.FontAwesomeManager;

public class TopHeaderFragment extends Fragment {

    private TextView icon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_top_header, container, false);
        getView(view);
        init();
        return view;
    }

    private void getView(View view){
        icon = view.findViewById(R.id.icon);
    }

    private void init(){
        FontAwesomeManager.getInstance().addIcon(icon, "far", "\uf0f3", getActivity());
    }
}
