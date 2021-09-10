package com.vientamthuong.eatsimple.login;

import android.app.Fragment;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.order.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HistoryTabFragment extends Fragment {

    String idCustomer = "";
    RecyclerView recyclerView;

    public HistoryTabFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_history_tab, container, false);

        mapping(view);

        displayOrders();

        return view;
    }
    private void mapping(View view){
        recyclerView = view.findViewById(R.id.activity_profile_history);
    }
    private void displayOrders(){
        ArrayList<Order> orders = new ArrayList<>();

        if(DataLocalManager.getAccount() != null){
            idCustomer = DataLocalManager.getAccount().getId();
        }


        String url = "https://eat-simple-app.000webhostapp.com/getOrderByIdCustomer.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                Order order = new Order();

                                order.setMa_dh(object.getString("ma_dh"));
                                order.setMa_size(object.getString("ma_size"));
                                order.setDia_chi(object.getString("ma_add"));
                                order.setSo_luong(Integer.parseInt(object.getString("so_luong")));
                                order.setTen_sp(object.getString("ten_sp"));
                                order.setNgay_tao(new DateTime(object.getString("ngay_tao")));
                                order.setTien(Integer.parseInt(object.getString("tien")));
                                order.setTong_tien(Integer.parseInt(object.getString("tong_tien")));
                                order.setUrl(object.getString("url"));
                                order.setTrang_thai_don_hang(Integer.parseInt(object.getString("trang_thai_thanh_toan")));

                                orders.add(order);
                            }

                            GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
                            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
                            recyclerView.setLayoutManager(layoutManager);

                            HistoryAdapter historyAdapter = new HistoryAdapter(getContext(),orders);
                            recyclerView.setAdapter(historyAdapter);
                            historyAdapter.notifyDataSetChanged();



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("ma_kh", idCustomer);
                return params;
            }
        };
        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);

    }

}