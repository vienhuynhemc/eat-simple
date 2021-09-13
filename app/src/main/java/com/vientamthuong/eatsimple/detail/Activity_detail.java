package com.vientamthuong.eatsimple.detail;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.beans.Comment;
import com.vientamthuong.eatsimple.beans.Product;
import com.vientamthuong.eatsimple.beans.Size;
import com.vientamthuong.eatsimple.cartPage.LoadCartHandler;
import com.vientamthuong.eatsimple.checkout.PayActivity;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.detailOrder.DetailOrder;
import com.vientamthuong.eatsimple.detailOrder.GetDetailOrder;
import com.vientamthuong.eatsimple.homePage.HomeMeowBottom;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductConfiguration;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHandler;
import com.vientamthuong.eatsimple.loadProductByID.LoadProductHelp;
import com.vientamthuong.eatsimple.login.Activity_login;
import com.vientamthuong.eatsimple.menuNotify.EventRing;
import com.vientamthuong.eatsimple.model.Account;
import com.vientamthuong.eatsimple.wishlist.WishlistDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_detail extends AppCompatActivity {

    private static final String TAG = "Activity_detail";

    private ArrayList<String> imgList = new ArrayList<>();

    private TextView title, gia, sosao, kcal, time,binhluan, contentdetail, soluong,gia_km,so_luong_con_lai,so_luong_ban_ra;
    private ImageView hinh,ring;
    private Button decre, incre;
    private FloatingActionButton back, detail_add,detail_cart,detail_wishlist;
    private int num = 1;
    private Intent intent;
    private LinearLayout layout;
    private Product product;
    private List<Size> sizes;
    private int indexSize;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getView();
        init();
        eventBinhLuan();
        event();
        getDataSize();


    }
    void eventBinhLuan(){

        binhluan.setOnClickListener(v -> {
            Dialog dialog = openDialogDatabase(R.layout.activity_detail_comment_dialog);
            RecyclerView recyclerView = dialog.findViewById(R.id.list_item);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            recyclerView.setLayoutManager(linearLayoutManager);

            List<Comment> comments = new ArrayList<>();
            CommentAdapter adapter = new CommentAdapter(comments);

            LoadCartHandler handler = LoadCartHandler.getInstance();
            recyclerView.setAdapter(adapter);

            handler.setCommentAdapter(adapter);
            handler.setComment(comments);
            handler.getHandler();
            GetComment.getData(this,product.getMa_sp());

        });

        String urlLoad = "https://eat-simple-app.000webhostapp.com/getSoSao.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        double so_sao = Double.parseDouble(response);
                        System.out.println("Số sao:" + response);
                        sosao.setText(String.valueOf((double) Math.round(so_sao * 10) / 10));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("ma_sp",product.getMa_sp());
                return params;
            }
        };
        VolleyPool.getInstance(this).addRequest(request);

//        int so_sao = 0;
//        for (int i = 0;i<= comments.size();i++){
//            so_sao += comments.get(i).getSosao();
//        }
//
//        tv.setText(String.valueOf((double) Math.round((so_sao/comments.size()) * 10) / 10));

    }

    public void getDataSize(){

        String urlLoad = "https://eat-simple-app.000webhostapp.com/getMaSize.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0;i<jsonArray.length();i++){

                                JSONObject object = jsonArray.getJSONObject(i);

                                @SuppressLint("ResourceType") View view = getLayoutInflater().inflate(R.layout.activity_cart_button_size, null, false);
                                AppCompatButton button = view.findViewById(R.id.dialog_lost_connection_try);
                                button.setText(object.getString("ten_size"));

                                if (i == 0){
                                    button.setBackgroundResource(R.color.color_main);
                                    // button.setTextColor(R.color.white);
                                }
                                button.setOnClickListener(v -> {
                                    eventButton(button);
                                });
                                sizes.add(new Size(object.getString("ma_size"),object.getString("ten_size")));
                                layout.addView(view);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("ma_sp", product.getMa_sp());
                System.out.println("MÃ SP : "+ product.getMa_sp());
                return params;
            }
        };
        VolleyPool.getInstance(this).addRequest(request);
    }

    void eventButton(AppCompatButton button){

        for (int i =0;i<layout.getChildCount();i++){

            View view = layout.getChildAt(i);
            AppCompatButton button1 = view.findViewById(R.id.dialog_lost_connection_try);

            if (button == button1){
                button.setBackgroundResource(R.color.color_main);
                indexSize = i;
            }else {
                button1.setBackgroundResource(R.color.white);
            }
        }

    }



    void event() {
        EventRing.getInstance().setView(ring);
        EventRing.getInstance().startAnim();

        incre.setOnClickListener(v -> {
            num += 1;
            soluong.setText(String.valueOf(num));
        });
        decre.setOnClickListener(v -> {
            if (num > 1) {
                num -= 1;
                soluong.setText(String.valueOf(num));
            }
        });
        back.setOnClickListener(v -> {
            finish();
        });
        detail_add.setOnClickListener(v -> {
            if (DataLocalManager.getAccount() != null) {

                String urlLoad = "https://eat-simple-app.000webhostapp.com/addCart.php";
                StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equals("THEM_THANH_CONG")) {
                                    Dialog dialog = openDialogDatabase(R.layout.activity_cart_dialog_add_cart_success);
                                    eventDialogSuccess(dialog, "Thành công", "Sản phẩm đã được thêm vào giở hàng", "Tiếp tục mua hàng", "Thanh toán ngay");
                                } else if (response.equals("HET_SAN_PHAM")) {
                                    Dialog dialog = openDialogDatabase(R.layout.activity_cart_dialog_add_cart_success);
                                    eventDialogSuccess(dialog, "Sin lũi", "Sản phẩm không đủ số lượng gòi", "Tiếp tục mua hàng", "Vào giỏ hàng");
                                } else if (response.equals("THEM_THAT_BAI")) {
                                    Dialog dialog = openDialogDatabase(R.layout.activity_cart_dialog_add_cart_success);
                                    eventDialogSuccess(dialog, "Thất bại", "Thêm sản phẩm thất bại", "Tiếp tục mua hàng", "Vào giỏ hàng");
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Dialog dialog = openDialogDatabase(R.layout.activity_cart_dialog_add_cart_success);
                                eventDialogSuccess(dialog, "Lỗi", "Sin lũi vì sự kiện này, hệ thống không thực hiện được", "Tiếp tục mua hàng", "Vào giỏ hàng");
                            }
                        }) {
                    @Nullable
                    @org.jetbrains.annotations.Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("ma_sp",product.getMa_sp());
                        params.put("ma_kh",DataLocalManager.getAccount().getId());
                        params.put("ma_size", String.valueOf(sizes.get(indexSize).getMa_size()));
                        params.put("so_luong", String.valueOf(soluong.getText()));

                        System.out.println("ma_sp"+product.getMa_sp() + "ma_kh"+DataLocalManager.getAccount().getId() +"ma_size" + String.valueOf(sizes.get(indexSize).getMa_size()));

                        return params;
                    }
                };
                VolleyPool.getInstance(this).addRequest(request);

            } else {
                Dialog dialog = openDialogDatabase(R.layout.activity_cart_dialog_login);
                eventDialog(dialog);
            }
        });
        detail_cart.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeMeowBottom.class);
            Bundle bundle = new Bundle();
            bundle.putString("dichuyen","cart");
            intent.putExtra("call",bundle);
            startActivity(intent);
        });
        detail_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataLocalManager.getAccount()!= null){
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("yeu_thich").child(DataLocalManager.getAccount().getId());
//               if (database.child(product.getMa_sp()+"_"+sizes.get(indexSize).getMa_size()) == null){
                    WishlistDAO dao = new WishlistDAO(Activity_detail.this);
                    dao.insertToWishlist(DataLocalManager.getAccount().getId(),product.getMa_sp(),sizes.get(indexSize).getMa_size());

                    DatabaseReference d = database.child(product.getMa_sp()+"_"+sizes.get(indexSize).getMa_size());

                    d.child("PriceS").setValue(product.getGia_km());
                    d.child("id").setValue(product.getMa_sp());
                    d.child("idCustomer").setValue(DataLocalManager.getAccount().getId());
                    d.child("img").setValue(product.getUrl());
                    d.child("name").setValue(product.getTen_sp());
                    d.child("nameSize").setValue(sizes.get(indexSize).getTen_size());
                    d.child("priceP").setValue(product.getGia());
                    d.child("size").setValue(sizes.get(indexSize).getMa_size());
//               }


                  //  insertToWishlist(DataLocalManager.getAccount().getId(),product.getMa_sp(),sizes.get(indexSize).getMa_size());
                    Log.d("SSS", "idAccount: "+DataLocalManager.getAccount().getId()+",idDish: "+product.getMa_sp()+", idSize: "+ sizes.get(indexSize).getMa_size()+", number: "+soluong.getText().toString());
                    // detail_wishlist.setBackgroundColor(Color.rgb(255,113,134));
                }
                else{
                    Toast.makeText(Activity_detail.this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            startActivity(intent);
        });

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
            Intent intent = new Intent(Activity_detail.this, Activity_login.class);
            intent.putExtra("Call", "Activity_detail");
            startActivity(intent);
            dialog.dismiss();
        });

    }

    Dialog openDialogDatabase(int layout) {
        final Dialog dialog = new Dialog(this);
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

    void init() {
        intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");

        title.setText(product.getTen_sp());
        gia.setText(product.getGia_km() + " VND");
        time.setText(product.getThoi_gian_nau() - 5 + " - " + product.getThoi_gian_nau() + " m");
        kcal.setText(String.valueOf(product.getKcal()));
        contentdetail.setText(product.getThong_tin());

        byte[] bitmaps = intent.getByteArrayExtra("bitmap");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length);
        hinh.setImageBitmap(bitmap);

        int km = ((product.getGia()-product.getGia_km())*100/(product.getGia()));
        gia_km.setText(String.valueOf(km +"%"));

        so_luong_con_lai.setText("Số lượng còn lại: " + product.getSo_luong_con_lai());
        so_luong_ban_ra.setText("Số lượng đã bán: " + product.getSo_luong_ban_ra());

    }

    void getView() {

        title = findViewById(R.id.text_hbg);
        gia = findViewById(R.id.text_price);
        sosao = findViewById(R.id.ratingtxt);
        time = findViewById(R.id.timetxt);
        kcal = findViewById(R.id.calotxt);
        hinh = findViewById(R.id.hinh_sp);
        decre = findViewById(R.id.decre);
        incre = findViewById(R.id.incre);
        soluong = findViewById(R.id.number);
        back = findViewById(R.id.detail_back);
        contentdetail = findViewById(R.id.contentdetail);
        detail_add = findViewById(R.id.detail_add);
        layout = findViewById(R.id.list_size);
        sizes = new ArrayList<>();
        gia_km = findViewById(R.id.text_price_km);
        gia_km.setPaintFlags(gia_km.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        detail_cart = findViewById(R.id.detail_cart);
        detail_wishlist = findViewById(R.id.detail_wishlist);
        ring = findViewById(R.id.notify);
        so_luong_con_lai = findViewById(R.id.so_luong_con_lai);
        so_luong_ban_ra = findViewById(R.id.so_luong_ban_ra);
        binhluan = findViewById(R.id.binhluan);
    }

}