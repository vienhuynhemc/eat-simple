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

public class HeaderFragment extends Fragment {

    private TextView icon;
    private TextView icon1;
    private TextView icon2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_header, container, false);
        getView(view);
        init();
        return view;
    }

    private void init(){
        FontAwesomeManager.getInstance().addIcon(icon,"fab","\uf368",getActivity());
        FontAwesomeManager.getInstance().addIcon(icon1,"far","\uf2b9",getActivity());
        FontAwesomeManager.getInstance().addIcon(icon2,"fas","\uf461",getActivity());
    }

    private void getView(View view) {
        icon = view.findViewById(R.id.icon);
        icon1 = view.findViewById(R.id.icon1);
        icon2 = view.findViewById(R.id.icon2);
    }
}
