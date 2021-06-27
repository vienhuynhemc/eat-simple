package com.vientamthuong.eatsimple.homePage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.jbCrypt.BCrypt;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.loadProductByID.GetListProduct;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductConfiguration;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHandler;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHelp;
import com.vientamthuong.eatsimple.model.DanhMuc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomDanhMucAdapter extends RecyclerView.Adapter<CustomDanhMucViewHolder> {

    private int[] resource;
    private final List<DanhMuc> danhMucs;
    private Handler handler;
    private boolean check = true;

    public CustomDanhMucAdapter(int[] resources, List<DanhMuc> danhMucs) {
        this.resource = resources;
        this.danhMucs = danhMucs;
    }

    @Override
    public int getItemViewType(int position) {
        int add = 0;
        if (position == 0) return add;
        else if (position == danhMucs.size() - 1) return 2 + add;
        else return 1 + add;
    }

    @NonNull
    @Override
    public CustomDanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource[viewType], parent, false);

        CustomDanhMucViewHolder customDanhMucViewHolder = new CustomDanhMucViewHolder(view);

        customDanhMucViewHolder.getCardView().setOnClickListener(v -> {
            LoadProductHelp.getLoadProductHelp().setNum(0);
            LoadProductHelp.getLoadProductHelp().setKiem_tra_danh_muc_moi(true);
            LoadProductHelp.getLoadProductHelp().setYMIN(LoadProductConfiguration.Y_MIN);
            LoadProductHelp.getLoadProductHelp().setMa_danh_muc_hien_tai(danhMucs.get(customDanhMucViewHolder.getAdapterPosition()).getMa_danh_muc());
            GetListProduct.getData(v.getContext());
        });

        return customDanhMucViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomDanhMucViewHolder holder, int position) {
        DanhMuc danhMuc = danhMucs.get(position);
        if (danhMuc.isLoaded()) {

            if (position == 0 && check){
                check = false;
                LoadProductHelp.getLoadProductHelp().setMa_danh_muc_hien_tai(danhMuc.getMa_danh_muc());
                GetListProduct.getData(holder.getCardView().getContext());
            }

            holder.getTextView().setText(danhMuc.getTen_danh_muc());
            holder.hiddenCardViewLoading2();
            if (danhMuc.getBitmap() != null) {
                holder.getImageView().setImageBitmap(danhMuc.getBitmap());
                holder.hiddenCardViewLoading();
            }
        }
    }

    @Override
    public int getItemCount() {
        return danhMucs.size();
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
