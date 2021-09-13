package com.vientamthuong.eatsimple.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.detail.Activity_detail;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.order.Order;
import com.vientamthuong.eatsimple.wishlist.TranmissionData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {

    private Context context;
    private ArrayList<RateModel> list;

    public RateAdapter(Context context, ArrayList<RateModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_profile_rate_item,parent,false);


        return new RateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {
        RateModel order = list.get(position);

        holder.time.setText("Thời gian: "+order.getDateRated().getDay()+" tháng "+order.getDateRated().getMonth()+" năm "+order.getDateRated().getYear());
        holder.cmt.setText(order.getContent());
        holder.name.setText(order.getName());

        if(order.getPriceSale() > 0){
            holder.priceSale.setText(order.getPrice()+"");
            holder.price.setText(order.getPriceSale()+" VNĐ");
        }
        else{
            holder.priceSale.setText("");
            holder.price.setText(order.getPrice()+" VNĐ");
        }

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get dish's information

                String url ="https://eat-simple-app.000webhostapp.com/dataTranmissionWishlist.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    TranmissionData dish = new TranmissionData();
                                    dish.setId(object.getString("ma_sp"));
                                    dish.setName(object.getString("ten_sp"));
                                    dish.setNumberRest(Integer.parseInt(object.getString("so_luong_con_lai")));
                                    dish.setNumberSaled(Integer.parseInt(object.getString("so_luong_ban_ra")));
                                    dish.setPrice(Integer.parseInt(object.getString("gia")));
                                    dish.setPriceSale(Integer.parseInt(object.getString("gia_km")));
                                    dish.setInformation(object.getString("thong_tin"));
                                    dish.setKcal(Integer.parseInt(object.getString("kcal")));
                                    dish.setTime(Integer.parseInt(object.getString("thoi_gian_nau")));
                                    dish.setUrl(object.getString("url"));


                                    holder.img.buildDrawingCache();
                                    Bitmap bitmap = holder.img.getDrawingCache();

                                    detail_product(dish,bitmap);



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error.toString());
                            }
                        }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("ma_sp",order.getIdDish());
                        return params;
                    }
                };
                VolleyPool.getInstance(context).addRequest(stringRequest);
            }
        });



        holder.size.setText("Size: "+ order.getNameSize());

        Glide.with(context).load(order.getUrl()).into(holder.img);

        for (int i = 0; i < 5;i++){
            if(i < order.getStar()){
                addStar(holder);
            }
            else{
                addStarBorder(holder);
            }
        }

    }
    private void addStar(RateViewHolder holder){
        View star = LayoutInflater.from(context).inflate(R.layout.activity_profile_rate_star,null,false);
        holder.star.addView(star);
    }
    private void addStarBorder(RateViewHolder holder){
        View star = LayoutInflater.from(context).inflate(R.layout.activity_rate_star_b,null,false);
        holder.star.addView(star);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RateViewHolder extends RecyclerView.ViewHolder{

        TextView cmt,time,name,price,priceSale,size;
        ImageView img;

        LinearLayout star;

        public RateViewHolder(@NonNull View itemView) {
            super(itemView);

            cmt = itemView.findViewById(R.id.activity_profile_comment);
            time = itemView.findViewById(R.id.activity_profile_rate_time);

            star = itemView.findViewById(R.id.activity_profile_rate_number);
            name = itemView.findViewById(R.id.activity_profile_rate_name);
            price = itemView.findViewById(R.id.activity_profile_rate_price);
            priceSale = itemView.findViewById(R.id.activity_profile_rate_priceSale);
            size = itemView.findViewById(R.id.activity_profile_rate_size);
            img = itemView.findViewById(R.id.activity_profile_rate_img);

        }
    }
    void detail_product(TranmissionData dish,Bitmap bitmap){

        Intent intent = new Intent(context, Activity_detail.class);

        Product product = new Product();
        product.setMa_sp(dish.getId());
        product.setTen_sp(dish.getName());
        product.setGia_km(dish.getPriceSale());
        product.setGia(dish.getPrice());
        product.setSo_luong_con_lai(dish.getNumberRest());
        product.setSo_luong_ban_ra(dish.getNumberSaled());

        product.setKcal(dish.getKcal());

        product.setThoi_gian_nau(dish.getTime());

        product.setThong_tin(dish.getInformation());

        product.setUrl(dish.getUrl());

        intent.putExtra("product",(Serializable) product );


        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] bytes = baos.toByteArray();

        intent.putExtra("bitmap",bytes);



        context.startActivity(intent);
    }


}
