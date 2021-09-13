package com.vientamthuong.eatsimple.login;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RateTabFragment extends Fragment {

    RecyclerView recyclerView;



    public RateTabFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rate_tab, container, false);

        recyclerView = view.findViewById(R.id.activity_profile_rate);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        String url = "https://eat-simple-app.000webhostapp.com/getRatesByIdCustomer.php";
        ArrayList<RateModel> rates = new ArrayList<>();
        ArrayList<RateModel> reverse = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for(int i = 0; i < array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                RateModel rate = new RateModel();
                                rate.setName(object.getString("ten_sp"));
                                rate.setNameSize(object.getString("ten_size"));
                                rate.setDateRated(new DateTime(object.getString("ngay_danh_gia")));
                                rate.setUrl(object.getString("url"));
                                rate.setPrice(Integer.parseInt(object.getString("gia")));
                                rate.setPriceSale(Integer.parseInt(object.getString("gia_km")));
                                rate.setStar(Integer.parseInt(object.getString("so_sao")));
                                rate.setContent(object.getString("noi_dung"));

                                rates.add(rate);

                            }
                            if(rates.size() == 0){
//                                none.setVisibility(View.VISIBLE);
                            }
                            else{
//                                none.setVisibility(View.GONE);
                            }

                            RateAdapter adapter = new RateAdapter(getContext(),rates);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("ma_kh", DataLocalManager.getAccount().getId());
                return params;
            }
        };

        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);

        return view;
    }
}