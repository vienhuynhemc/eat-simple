package com.vientamthuong.eatsimple.detailOrder;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.beans.Cart;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.cartPage.GetCart;
import com.vientamthuong.eatsimple.detail.Activity_detail;
import com.vientamthuong.eatsimple.homePage.HomeMeowBottom;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.login.Activity_login;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderHolder> {

    private List<Cart> cartList;

    public DetailOrderAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @NotNull
    @Override
    public DetailOrderHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detail_order_one_item_product,parent,false);

        DetailOrderHolder holder = new DetailOrderHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DetailOrderHolder holder, int position) {

        Cart cart = cartList.get(position);

        holder.getTitle().setText(cart.getTen_sp());
        holder.getSo_sao().setText("SL: " + cart.getSo_luong());
        holder.getSize().setText("Size: " + cart.getSizes().getTen_size());
        holder.getGia().setText(cart.getGia_km() + " VND");

        holder.getCardView().setOnClickListener(v -> {

            if (cart.getBitmap() != null){
                Intent intent = new Intent(v.getContext(), Activity_detail.class);

                Product product = new Product();
                product.setMa_sp(cart.getMa_sp());
                product.setTen_sp(cart.getTen_sp());
                product.setGia_km(cart.getGia_km());
                product.setGia(cart.getGia());
                product.setSo_luong_con_lai(cart.getSo_luong_con_lai());
                product.setSo_luong_ban_ra(cart.getSo_luong_ban_ra());

                product.setKcal(cart.getKcal());

                product.setThoi_gian_nau(cart.getThoi_gian_nau());

                product.setThong_tin(cart.getThong_tin());

                product.setUrl(cart.getUrl());

                intent.putExtra("product",(Serializable) product );

                Bitmap bitmap = cart.getBitmap();

                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                byte[] bytes = baos.toByteArray();
                intent.putExtra("bitmap",bytes);

                v.getContext().startActivity(intent);
            }else {
                Toast.makeText(v.getContext(), "Từ từ thôi bạn", Toast.LENGTH_SHORT).show();
            }


        });


        holder.getButton().setOnClickListener(v -> {


            if (DataLocalManager.getAccount() != null) {

                String urlLoad = "https://eat-simple-app.000webhostapp.com/addCart.php";
                StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equals("THEM_THANH_CONG")) {
                                    Dialog dialog = openDialogDatabase(R.layout.activity_cart_dialog_add_cart_success,v.getContext());
                                    eventDialogSuccess(dialog, "Thành công", "Sản phẩm đã được thêm vào giở hàng", "Tiếp tục mua hàng", "Thanh toán ngay");
                                } else if (response.equals("HET_SAN_PHAM")) {
                                    Dialog dialog = openDialogDatabase(R.layout.activity_cart_dialog_add_cart_success,v.getContext());
                                    eventDialogSuccess(dialog, "Sin lũi", "Sản phẩm không đủ số lượng gòi", "Tiếp tục mua hàng", "Vào giỏ hàng");
                                } else if (response.equals("THEM_THAT_BAI")) {
                                    Dialog dialog = openDialogDatabase(R.layout.activity_cart_dialog_add_cart_success,v.getContext());
                                    eventDialogSuccess(dialog, "Thất bại", "Thêm sản phẩm thất bại", "Tiếp tục mua hàng", "Vào giỏ hàng");
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Dialog dialog = openDialogDatabase(R.layout.activity_cart_dialog_add_cart_success, v.getContext());
                                eventDialogSuccess(dialog, "Lỗi", "Sin lũi vì sự kiện này, hệ thống không thực hiện được", "Tiếp tục mua hàng", "Vào giỏ hàng");
                            }
                        }) {
                    @Nullable
                    @org.jetbrains.annotations.Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("ma_sp",cart.getMa_sp());
                        params.put("ma_kh",DataLocalManager.getAccount().getId());
                        params.put("ma_size", cart.getSizes().getMa_size());
                        params.put("so_luong", String.valueOf(1));

                        return params;
                    }
                };
                VolleyPool.getInstance(v.getContext()).addRequest(request);

            }
            else {
                Dialog dialog = openDialogDatabase(R.layout.activity_cart_dialog_login, v.getContext());
                eventDialog(dialog);
            }

        });

        if (cart.getBitmap() == null && ! cart.isLoadImg()){
            GetDetailOrder.getBitmapImage(cart,holder.getCardView().getContext(),this);
        }else  if (cart.getBitmap() != null && cart.isLoadImg()){
            holder.getImg().setImageBitmap(cart.getBitmap());
        }


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    void eventDialog(Dialog dialog) {
        Button back = dialog.findViewById(R.id.dialog_lost_connection_ignore);
        Button login = dialog.findViewById(R.id.dialog_lost_connection_try);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        login.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Activity_login.class);
            intent.putExtra("Call", "Activity_detail");
            v.getContext().startActivity(intent);
            dialog.dismiss();
        });

    }
    Dialog openDialogDatabase(int layout, Context context) {
        final Dialog dialog = new Dialog(context);
        // ẩn title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set background diaog
        dialog.setContentView(layout);

        Window window = dialog.getWindow();

        if (window == null) {
            return null;
        } else {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.CENTER;
            window.setAttributes(layoutParams);
            dialog.setCancelable(true);
            dialog.show();
            return dialog;
        }
    }
    void eventDialogSuccess(Dialog dialog, String title, String content, String btn1, String btn2) {
        Button back = dialog.findViewById(R.id.dialog_lost_connection_ignore);
        Button login = dialog.findViewById(R.id.dialog_lost_connection_try);
        TextView tvtitle = dialog.findViewById(R.id.dialog_lost_connection_title);
        TextView tvcontent = dialog.findViewById(R.id.content);

        back.setText(btn1);
        login.setText(btn2);
        tvtitle.setText(title);
        tvcontent.setText(content);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        login.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeMeowBottom.class);
            Bundle bundle = new Bundle();
            bundle.putString("dichuyen","cart");
            intent.putExtra("call",bundle);
            v.getContext().startActivity(intent);
        });

    }
}
