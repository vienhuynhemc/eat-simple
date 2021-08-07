package com.vientamthuong.eatsimple.menuNotify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.admin.configuration.Configuration;
import com.vientamthuong.eatsimple.beans.ThongBao;
import com.vientamthuong.eatsimple.cartPage.CartAdapter;
import com.vientamthuong.eatsimple.login.Activity_login;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotifyPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifyPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotifyPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotifyPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotifyPageFragment newInstance(String param1, String param2) {
        NotifyPageFragment fragment = new NotifyPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ImageView filter;
    private RecyclerView reList;
    private NotifiAdapter adapter;
    private List<ThongBao> list;
    private AppCompatButton dangnhap;
    private int nowSort;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view ;

             if (DataLocalManager.getAccount() != null){
               view = inflater.inflate(R.layout.fragment_notify_page, container, false);
                 getView(view);
                 init();
                 event();
             }else {
                 view = inflater.inflate(R.layout.fragment_cart_page, container, false);
                 getView2(view);
                 eventdangnhap();
             }
        return view;

    }

    void event(){
        filter.setOnClickListener(v -> {

                if (nowSort == Configuration.ASC) {
                    nowSort = Configuration.DESC;
                    filter.setScaleY(1);
                } else {
                    nowSort = Configuration.ASC;
                    filter.setScaleY(-1);
                }
                sort();


        });
    }
    void sort(){
        list.sort((o1, o2) -> {

            if (o1.getNgay_tao() != null) {
                if (nowSort == Configuration.ASC) {
                    return (int) (o1.getNgay_tao().getTime() - o2.getNgay_tao().getTime());
                } else {
                    return (int) (o2.getNgay_tao().getTime() - o1.getNgay_tao().getTime());
                }
            } else {
                return 0;
            }

        });
        adapter.notifyDataSetChanged();
    }

    void getView2(View v){
        dangnhap = v.findViewById(R.id.dialog_lost_connection_try);
    }
    void eventdangnhap(){
        dangnhap.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), Activity_login.class));
        });
    }

    private void init(){

        DataLocalManager.setRing(false);

        adapter = new NotifiAdapter(list);
        adapter.setContext(getContext());
        reList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        reList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        NotifyHandler notifyHandler = NotifyHandler.getInstance();
        notifyHandler.setAdapter(adapter);
        notifyHandler.setAdds(list);
        notifyHandler.getHandler();
        GetNotify.getData(getContext());
    }

    private void getView(View view){

        filter = view.findViewById(R.id.filter);
        reList = view.findViewById(R.id.list_notify);
        list = new ArrayList<>();

    }

}