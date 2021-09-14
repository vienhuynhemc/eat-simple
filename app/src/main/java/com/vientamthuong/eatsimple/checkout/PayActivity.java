package com.vientamthuong.eatsimple.checkout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.admin.configuration.WebService;
import com.vientamthuong.eatsimple.admin.session.DataSession;
import com.vientamthuong.eatsimple.beans.Address;
import com.vientamthuong.eatsimple.beans.Cart;
import com.vientamthuong.eatsimple.beans.MaGiamGia;
import com.vientamthuong.eatsimple.beans.MaGiamGiaConfiguration;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.homePage.HomeMeowBottom;
import com.vientamthuong.eatsimple.loadData.VolleyPool;
import com.vientamthuong.eatsimple.menuNotify.CustomNotify;
import com.vientamthuong.eatsimple.menuNotify.EventRing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayActivity extends AppCompatActivity {

    private RecyclerView reDiaChi, recyclerView, reSanPham;
    private AppCompatButton appgiamgia;
    private Button muangay;
    private EditText giamgia;
    private TextView tamtinh, tongtien, phivc;
    private List<Cart> carts;
    private List<Address> addresses;
    private FloatingActionButton back;
    private ImageView ring;
    private List<View> views;
    private MaGiamGia maGiamGia;
    private CardView themdiahchi;
    private int count;
    private CustomNotify notify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        getView();

        init();

        event();

    }


    void event() {
        eventBack();
        eventMaGiamGia();
        eventThemDiaChi();
        eventmuangay();

//        EventRing.getInstance().setContext(PayActivity.this);
//        EventRing.getInstance().setView(ring);
//        EventRing.getInstance().startAnim();
    }
    void eventDialog(Dialog dialog,String title,String content){
        TextView tvtitle = dialog.findViewById(R.id.dialog_lost_connection_title);
        TextView tvcontent = dialog.findViewById(R.id.content);
        AppCompatButton button = dialog.findViewById(R.id.dialog_lost_connection_ignore);

        tvtitle.setText(title);
        tvcontent.setText(content);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeMeowBottom.class);
            Bundle bundle = new Bundle();
            bundle.putString("dichuyen","cart");
            intent.putExtra("call",bundle);
            startActivity(intent);
        });


    }

    void eventmuangay() {

        muangay.setOnClickListener(v -> {

            Dialog dialogwait = openDialogDatabase(R.layout.activity_checkout_dialog_wait);

            String ma_add = getIdAdd();
            if (ma_add != null) {

                int tong_tien = Integer.parseInt(tongtien.getText().toString().trim().substring(0, tongtien.getText().toString().trim().indexOf(" VND")));
                String ma_gg = "";
                if (maGiamGia != null) {
                    ma_gg = maGiamGia.getMagg();
                }else{
                    ma_gg = "null";
                }


                String urlLoad = "https://eat-simple-app.000webhostapp.com/checkout.php";
                String finalMa_gg = ma_gg;
                StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equals("FAIL")) {
                                 //   Toast.makeText(PayActivity.this, "Lỗi hệ thống vui lòng thử lại", Toast.LENGTH_SHORT).show();
                                    dialogwait.dismiss();
                                    Dialog dialogfail = openDialogDatabase(R.layout.activity_checkout_dialog_notify);
                                    eventDialog(dialogfail,"Thanh toán thất bại","Lỗi hệ thống vui lòng thử lại sau");


                                } else {

                                    String ma_dh = response;
                                    addThongBaoCaNhan(ma_dh);
                                    for (int i = 0; i < carts.size(); i++) {
                                        String urlLoad = "https://eat-simple-app.000webhostapp.com/addChiTietDonHang.php";
                                        int finalI = i;
                                        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {

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
                                                params.put("ma_dh",ma_dh);
                                                params.put("ma_kh", DataLocalManager.getAccount().getId());
                                                params.put("ma_sp",carts.get(finalI).getMa_sp());
                                                params.put("ma_size",carts.get(finalI).getSizes().getMa_size());
                                                params.put("so_luong", String.valueOf(carts.get(finalI).getSo_luong()));
                                                params.put("tien", String.valueOf(carts.get(finalI).getGia_km()*carts.get(finalI).getSo_luong()));
                                                return params;
                                            }

                                        };
                                        VolleyPool.getInstance(PayActivity.this).addRequest(request);

                                    }
                                    dialogwait.dismiss();
                                    Dialog dialogSuccess = openDialogDatabase(R.layout.activity_checkout_dialog_notify);
                                    eventDialog(dialogSuccess,"Thanh toán thành công","Đơn hàng sẽ nhanh chóng đến tay bạn");
                                    notify = new CustomNotify("Bạn vừa có một đơn hàng mới là ",ma_dh,ring);
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
                        params.put("ma_add", ma_add);
                        params.put("ma_kh", DataLocalManager.getAccount().getId());
                        params.put("tong_tien", String.valueOf(tong_tien));
                        params.put("ma_gg", finalMa_gg);
                        params.put("time", String.valueOf(CheckoutConfiguration.TIME));
                        params.put("ngay_tao",new DateTime().toString());

                        return params;
                    }
                };
                VolleyPool.getInstance(this).addRequest(request);
            }


        });


    }

    void eventThemDiaChi() {
        themdiahchi.setOnClickListener(v -> {

            Intent intent = new Intent(PayActivity.this, AddressActivity.class);

            intent.putExtra("carts", (Serializable) carts);

            startActivity(intent);

        });
    }

    void init() {
        initProduct();
        initAddress();
        phivc.setText(MaGiamGiaConfiguration.PHIVANCHUYEN + " VND");
        views = new ArrayList<>();
        recyclerView = reDiaChi;
    }

    String getIdAdd() {

        for (int i = 0; i < reDiaChi.getChildCount(); i++) {
            View view = reDiaChi.getChildAt(i);
            CheckBox checkBox1 = view.findViewById(R.id.checkbox_cart);
            if (checkBox1.isChecked()) {
                return addresses.get(i).getMa_add();
            }
        }
        return null;

    }

    void eventMaGiamGia() {


        appgiamgia.setOnClickListener(v -> {
            if (count == 0) {
                String magg = giamgia.getText().toString().trim();
                System.out.println("MAGIAMGIA: " + magg);

                String urlLoad = "https://eat-simple-app.000webhostapp.com/getMaGiamGia.php";
                StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equals("NONE")) {
                                    Toast.makeText(PayActivity.this, "Rất tiếc mã này không có hiệu lực, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                                } else {
                                    try {

                                        maGiamGia = new MaGiamGia();

                                        JSONObject object = new JSONObject(response);

                                        maGiamGia.setMagg(object.getString("ma_mgg"));
                                        maGiamGia.setKieugg(Integer.parseInt(object.getString("kieu_mgg")));
                                        maGiamGia.setGiatri(Integer.parseInt(object.getString("gia_tri")));
                                        maGiamGia.setSolansudung(Integer.parseInt(object.getString("so_lan_su_dung")));
                                        maGiamGia.setSolansudungtoida(Integer.parseInt(object.getString("so_lan_su_dung_toi_da")));
                                        maGiamGia.setHansudung(new DateTime(object.getString("han_su_dung")));
                                        maGiamGia.setNgaytao(new DateTime(object.getString("ngay_tao")));
                                        maGiamGia.setTontai(Integer.parseInt(object.getString("tontai")));

                                        int tiencu = Integer.parseInt(tongtien.getText().toString().trim().substring(0, tongtien.getText().toString().trim().indexOf(" VND")).trim());

                                        int tienmoi = maGiamGia.convert(tiencu);

                                        if (maGiamGia.getKieugg() != 10 && !maGiamGia.getMagg().equals("null")){
                                            Toast.makeText(PayActivity.this, maGiamGia.print(), Toast.LENGTH_SHORT).show();
                                        }
                                        tongtien.setText(tienmoi + " VND");
                                        count++;

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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
                        params.put("ma_mgg", magg);
                        return params;
                    }
                };
                VolleyPool.getInstance(this).addRequest(request);
            } else {
                Toast.makeText(this, "Tham lam", Toast.LENGTH_SHORT).show();
            }

        });
    }

//    void eventCheck() {
//
//        for (int i = 0; i < reDiaChi.getChildCount(); i++) {
//
//            View view = reDiaChi.getChildAt(i);
//            CheckBox checkBox = view.findViewById(R.id.checkbox_cart);
//            System.out.println("Check");
//
//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if (isChecked) {
//                        System.out.println("Check0");
//                        for (int i = 0; i < reDiaChi.getChildCount(); i++) {
//                            View view = reDiaChi.getChildAt(i);
//                            CheckBox checkBox1 = view.findViewById(R.id.checkbox_cart);
//                            System.out.println("Check1");
//                            if (checkBox1 != checkBox) {
//                                checkBox1.setChecked(false);
//                                System.out.println("Check2");
//                            }
//                        }
//
//                    }
//                }
//            });
//
//
//        }
//
//    }

    void eventBack() {
        back.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), HomeMeowBottom.class);
            Bundle bundle = new Bundle();
            bundle.putString("dichuyen", "cart");
            intent.putExtra("call", bundle);
            startActivity(intent);
        });
    }

    void initAddress() {
        addresses = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        reDiaChi.setLayoutManager(linearLayoutManager);
        reDiaChi.setHasFixedSize(true);

        PayAddessAdapter adapter = new PayAddessAdapter(addresses);

        reDiaChi.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setReDiaChi(reDiaChi);


        CheckoutHandler handler = CheckoutHandler.getInstance();
        handler.setAdds(addresses);
        handler.setAdapter(adapter);
        handler.getHandler();
        GetAddress.getData(this);
    }

    void initProduct() {

        Intent integer = getIntent();

        carts = (List<Cart>) integer.getSerializableExtra("carts");

        if (carts != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            reSanPham.setLayoutManager(linearLayoutManager);
            reSanPham.setHasFixedSize(true);
            PayProductAdapter adapter = new PayProductAdapter(carts);
            reSanPham.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        int tien = 0;
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getGia_km() != 0){
                tien += carts.get(i).getGia_km()*carts.get(i).getSoluongsp();
            }else{
                tien += carts.get(i).getGia()*carts.get(i).getSoluongsp();
            }
        }
        tamtinh.setText(tien + " VND");
        tongtien.setText(tien + MaGiamGiaConfiguration.PHIVANCHUYEN + " VND");
    }

    void getView() {
        reDiaChi = findViewById(R.id.list_diachi);
        reSanPham = findViewById(R.id.list_product);
        themdiahchi = findViewById(R.id.themdiachi);
        appgiamgia = findViewById(R.id.apdung);
        giamgia = findViewById(R.id.add_giamgia);
        tamtinh = findViewById(R.id.total_product);
        tongtien = findViewById(R.id.total_price);
        back = findViewById(R.id.detail_back);
        phivc = findViewById(R.id.total_vc);
        muangay = findViewById(R.id.muangay);
        ring = findViewById(R.id.notify);
        count = 0;

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

    private void addThongBaoCaNhan(String ma_dh) {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                WebService.lay_ma_thong_bao_chuong_tiep_theo,
//                response -> {
//                    JSONArray jsonArray = null;
//                    try {
//                        String ma_thong_bao_ca_nhan = null;
//                        jsonArray = new JSONArray(response);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            ma_thong_bao_ca_nhan = "tbc_" + jsonObject.getString("ma_thong_bao_chuong");
//                            break;
//                        }
//                        System.out.println(ma_thong_bao_ca_nhan + " Ok");
//                        // Firebase
//                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                        DatabaseReference root = firebaseDatabase.getReference();
//                        DateTime nowDate = new DateTime();
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("1", "nv_1");
//                        map.put("2", "nv_2");
//                        map.put("3", "nv_3");
//                        map.put("4", "nv_4");
//                        map.put("5", "nv_5");
//                        Map<String,Object> map2 = new HashMap<>();
//                        map2.put("kieu_nguoi_gui","0");
//                        map2.put("ma_nguoi_gui",DataLocalManager.getAccount().getId());
//                        map2.put("ngay_tao",nowDate.toString());
//                        map2.put("noi_dung","vừa thanh toán đơn hàng #"+ma_dh);
//                        root.child("thong_bao_chuong").child(ma_thong_bao_ca_nhan).setValue(map);
//                        root.child("chi_tiet_thong_bao_chuong").child(ma_thong_bao_ca_nhan).setValue(map2);
//                        // Webservice
//                        addNewThongBaoCaNhanWebService(ma_thong_bao_ca_nhan, nowDate, "vừa thanh toán đơn hàng #"+ma_dh);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }, error -> {
//            System.out.println(error.toString());
//        });
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                WebService.TIME_OUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleyPool.getInstance(this).addRequest(stringRequest);
    }

    private void addNewThongBaoCaNhanWebService(String ma_thong_bao_ca_nhan, DateTime ngay_tao, String noi_dung) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.them_mot_thong_bao_chuong_moi,
                response -> {
                }, error -> {
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ma_thong_bao_chuong", ma_thong_bao_ca_nhan);
                params.put("ngay_tao", ngay_tao.toString());
                params.put("noi_dung", noi_dung);
                params.put("ma_nguoi_gui", DataLocalManager.getAccount().getId());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(this).addRequest(stringRequest);
    }
}