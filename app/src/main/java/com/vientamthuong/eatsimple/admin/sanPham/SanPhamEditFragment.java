package com.vientamthuong.eatsimple.admin.sanPham;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.configuration.Configuration;
import com.vientamthuong.eatsimple.admin.configuration.WebService;
import com.vientamthuong.eatsimple.admin.danhMuc.SelectAdapter;
import com.vientamthuong.eatsimple.admin.dialog.DiaLogConfirm;
import com.vientamthuong.eatsimple.admin.loadData.LoadData;
import com.vientamthuong.eatsimple.admin.model.DanhMuc;
import com.vientamthuong.eatsimple.admin.model.DanhMucSanPham;
import com.vientamthuong.eatsimple.admin.model.SanPham;
import com.vientamthuong.eatsimple.admin.model.Size;
import com.vientamthuong.eatsimple.admin.session.DataSession;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SanPhamEditFragment extends Fragment {

    private CardView buttonBack;
    private CardView buttonCamera;
    private CardView buttonStorage;
    private ImageView hinhSanPham;
    private Spinner chon_danh_muc;
    private FloatingActionButton done;

    // List danh mục
    private List<DanhMucSanPham> danhMucSanPhams;
    private String ma_dm;

    // Giá khuyễn mãi
    private EditText gia_km;
    private TextView thieu_gia_km;

    // Giá
    private EditText gia;
    private TextView thieu_gia;

    // Size
    private CheckBox nho;
    private CheckBox vua;
    private CheckBox to;

    // kcal
    private EditText kcal;
    private TextView thieu_kcal;

    // thoi gian nau
    private EditText thoi_gian_nau;
    private TextView thieu_thoi_gian_nau;

    // noi dung
    private EditText noi_dung;
    private TextView thieu_noi_dung;


    // ten sp
    private EditText ten_sp;
    private TextView thieu_ten_sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_san_pham_add, container, false);
        getView(view);
        init();
        action();
        getData();
        return view;
    }

    private void init() {
        SanPham sanPham = SanPhamSession.getInstance().getSanPham();
        hinhSanPham.setImageBitmap(sanPham.getHinh());
        ten_sp.setText(sanPham.getTenSanPham());
        gia.setText(sanPham.getGia()+"");
        gia_km.setText(sanPham.getGia_km()+"");
        thoi_gian_nau.setText(sanPham.getThoi_gian_nau()+"");
        kcal.setText(sanPham.getKcal()+"");
        noi_dung.setText(sanPham.getThong_tin());
        for (Size size : sanPham.getSizes()) {
            if (size.getMa_size().equals("size_0")) {
                nho.setChecked(true);
            } else if (size.getMa_size().equals("size_1")) {
                vua.setChecked(true);
            } else if (size.getMa_size().equals("size_2")) {
                to.setChecked(true);
            }
        }
    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.lay_all_dm,
                response -> {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(response);
                        List<DanhMucSanPham> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            list.add(new DanhMucSanPham(jsonObject.getString("ten_dm"), jsonObject.getString("ma_dm")));
                        }
                        this.danhMucSanPhams = list;
                        List<String> listType = new ArrayList<>();
                        for (DanhMucSanPham danhMucSanPham : danhMucSanPhams) {
                            listType.add(danhMucSanPham.getTenDanhMuc());
                        }
                        SelectAdapter selectAdapter = new SelectAdapter(getActivity(), R.layout.admin_view_holder_select, listType);
                        chon_danh_muc.setAdapter(selectAdapter);
                        for(int i =0 ; i < this.danhMucSanPhams.size();i++){
                            if(this.danhMucSanPhams.get(i).getMaDanhMuc().equals(SanPhamSession.getInstance().getSanPham().getDanhMucSanPham().getMaDanhMuc())){
                                chon_danh_muc.setSelection(i);
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
    }

    private void getView(View view) {
        buttonBack = view.findViewById(R.id.buttonBack);
        buttonCamera = view.findViewById(R.id.buttonCamera);
        buttonStorage = view.findViewById(R.id.buttonStorage);
        hinhSanPham = view.findViewById(R.id.hinhDanhMuc);
        chon_danh_muc = view.findViewById(R.id.chon_danh_muc);
        done = view.findViewById(R.id.buttonThem);
        gia = view.findViewById(R.id.gia);
        gia_km = view.findViewById(R.id.gia_km);
        thieu_gia = view.findViewById(R.id.thieu_gia);
        thieu_gia_km = view.findViewById(R.id.thieu_gia_km);
        nho = view.findViewById(R.id.nho);
        vua = view.findViewById(R.id.vua);
        to = view.findViewById(R.id.to);
        kcal = view.findViewById(R.id.kcal);
        thieu_kcal = view.findViewById(R.id.thieu_kcal);
        thoi_gian_nau = view.findViewById(R.id.thoi_gian_nau);
        thieu_thoi_gian_nau = view.findViewById(R.id.thieu_thoi_gian_nau);
        ten_sp = view.findViewById(R.id.ten_sp);
        thieu_ten_sp = view.findViewById(R.id.thieu_ten_sp);
        noi_dung = view.findViewById(R.id.noi_dung);
        thieu_noi_dung = view.findViewById(R.id.thieu_noi_dung);
    }

    private void action() {
        gia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    thieu_gia.setVisibility(View.VISIBLE);
                } else {
                    thieu_gia.setVisibility(View.GONE);
                }
            }
        });
        gia_km.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    thieu_gia_km.setVisibility(View.VISIBLE);
                } else {
                    thieu_gia_km.setVisibility(View.GONE);
                }
            }
        });
        kcal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    thieu_kcal.setVisibility(View.VISIBLE);
                } else {
                    thieu_kcal.setVisibility(View.GONE);
                }
            }
        });
        thoi_gian_nau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    thieu_thoi_gian_nau.setVisibility(View.VISIBLE);
                } else {
                    thieu_thoi_gian_nau.setVisibility(View.GONE);
                }
            }
        });
        noi_dung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    thieu_noi_dung.setVisibility(View.VISIBLE);
                } else {
                    thieu_noi_dung.setVisibility(View.GONE);
                }
            }
        });
        ten_sp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    thieu_ten_sp.setVisibility(View.VISIBLE);
                } else {
                    thieu_ten_sp.setVisibility(View.GONE);
                }
            }
        });
        buttonBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            List<Fragment> fragments = fragmentManager.getFragments();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragments.get(fragments.size() - 1));
            fragmentTransaction.commit();
        });
        buttonCamera.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent, Configuration.CAMERA_ACTION_CODE);
            } else {
                Toast.makeText(getActivity(), "Máy của bạn không hỗ trợ", Toast.LENGTH_SHORT).show();
            }
        });
        buttonStorage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent, Configuration.STORAGE_SELECT_PHOTO);
            } else {
                Toast.makeText(getActivity(), "Máy của bạn không hỗ trợ", Toast.LENGTH_SHORT).show();
            }
        });
        done.setOnClickListener(v -> {
            String gia = this.gia.getText().toString();
            String gia_km = this.gia_km.getText().toString();
            boolean size_nho = this.nho.isChecked();
            boolean size_vua = this.vua.isChecked();
            boolean size_to = this.to.isChecked();
            String kcal = this.kcal.getText().toString();
            String thoi_gian_nau = this.thoi_gian_nau.getText().toString();
            String noi_dung = this.noi_dung.getText().toString();
            String ten_sp = this.ten_sp.getText().toString();
            if (gia.length() == 0 || gia_km.length() == 0 || (!size_nho && !size_vua && !size_to) ||
                    kcal.length() == 0 || thoi_gian_nau.length() == 0 || noi_dung.length() == 0 || ten_sp.length() == 0
            ) {
                Toast.makeText(getActivity(), "Thiếu thông tin", Toast.LENGTH_SHORT).show();
            } else {
                DiaLogConfirm diaLogConfirm = new DiaLogConfirm(getActivity());
                diaLogConfirm.getTextViewTitle().setText("Chỉnh sửa phẩm");
                diaLogConfirm.getTextViewContent().setText("Bạn có chắc chắn rằng mình muốn lưu các thay đổi?");
                diaLogConfirm.getBtTry().setText("Không");
                diaLogConfirm.getBtIgnore().setText("Lưu");
                diaLogConfirm.getBtTry().setOnClickListener(v1 -> diaLogConfirm.dismiss());
                diaLogConfirm.getBtIgnore().setOnClickListener(v1 -> {
                    diaLogConfirm.dismiss();
                    SanPham sanPham = SanPhamSession.getInstance().getSanPham();
                    addToFirebase(sanPham.getMaSanPham(), gia, gia_km, size_nho, size_vua, size_to, kcal, thoi_gian_nau, noi_dung, ten_sp);
                    // add thong báo
                    addThongBaoCaNhan(sanPham.getMaSanPham());
                });
                diaLogConfirm.show();
            }
        });
        chon_danh_muc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ma_dm = danhMucSanPhams.get(position).getMaDanhMuc();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void addToFirebase(String ma_sp, String gia, String gia_km, boolean size_nho, boolean size_vua, boolean size_to, String kcal, String thoi_gian_nau, String noi_dung, String ten_sp) {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Tải dữ liệu lên server");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference root = firebaseStorage.getReference();
        StorageReference riversRef = root.child("san_pham/" + ma_sp + "/" + ma_sp + ".png");
        hinhSanPham.setDrawingCacheEnabled(true);
        hinhSanPham.buildDrawingCache();
        Bitmap bitmap = hinhSanPham.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        riversRef.putBytes(data).addOnSuccessListener(taskSnapshot -> {
            riversRef.getDownloadUrl().addOnSuccessListener(uri -> {
                // clear img
                hinhSanPham.destroyDrawingCache();
                hinhSanPham.setDrawingCacheEnabled(false);
                // hide dialog
                pd.dismiss();
                // webservice
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        WebService.edit_san_pham,
                        response -> {
                        }, error -> {
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        SanPham sanPham = SanPhamSession.getInstance().getSanPham();
                        Map<String, String> params = new HashMap<>();
                        params.put("ma_sp", ma_sp);
                        params.put("ten_sp", ten_sp);
                        params.put("url", uri.toString());
                        params.put("kcal", kcal);
                        params.put("thoi_gian_nau", thoi_gian_nau);
                        params.put("ma_danh_muc", ma_dm);
                        params.put("thong_tin", noi_dung);
                        params.put("gia", gia);
                        params.put("ton_tai","1");
                        params.put("gia_km", gia_km);
                        if (size_nho) {
                            params.put("nho", "size_0");
                            params.put("slnho", sanPham.getSoLuongConLaiTheoSize("size_0")+"");
                        }
                        if (size_vua) {
                            params.put("vua", "size_1");
                            params.put("slvua", sanPham.getSoLuongConLaiTheoSize("size_1")+"");
                        }
                        if (size_to) {
                            params.put("to", "size_2");
                            params.put("slto", sanPham.getSoLuongConLaiTheoSize("size_2")+"");
                        }
                        return params;
                    }
                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        WebService.TIME_OUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
                // Success
                buttonBack.callOnClick();
                Toast.makeText(getActivity(), "Lưu thay đổi thành công", Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(e -> {

        }).addOnProgressListener(snapshot -> {
            double p = (100.00 * snapshot.getBytesTransferred()) / (double) snapshot.getTotalByteCount();
            pd.setMessage("Tiến độ: " + (int) p + " %");
        });
    }

    private void addThongBaoCaNhan(String ma_danh_muc) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                WebService.lay_ma_thong_bao_ca_nhan_tiep_theo,
                response -> {
                    JSONArray jsonArray = null;
                    try {
                        String ma_thong_bao_ca_nhan = null;
                        jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ma_thong_bao_ca_nhan = "ma_thong_bao_ca_nhan_" + jsonObject.getString("ma_thong_bao_ca_nhan");
                            break;
                        }
                        System.out.println(ma_thong_bao_ca_nhan + " Ok");
                        // Firebase
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference root = firebaseDatabase.getReference();
                        DateTime nowDate = new DateTime();
                        Map<String, Object> map = new HashMap<>();
                        map.put("nv_thuc_hien", DataSession.getInstance().getMa_tai_khoan());
                        map.put("nv_nhan", "");
                        map.put("ngay_tao", nowDate.toString());
                        map.put("type", "0");
                        map.put("noi_dung", "vừa thay đổi thông tin một sản phẩm có ID là");
                        map.put("noi_dung_quan_trong", "#" + ma_danh_muc);
                        root.child("thong_bao_ca_nhan").child(ma_thong_bao_ca_nhan).setValue(map);
                        // Webservice
                        addNewThongBaoCaNhanWebService(ma_thong_bao_ca_nhan, nowDate, ma_danh_muc);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            System.out.println(error.toString());
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
    }

    private void addNewThongBaoCaNhanWebService(String ma_thong_bao_ca_nhan, DateTime ngay_tao, String ma_danh_muc) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.them_mot_thong_bao_ca_nhan_moi,
                response -> {
                }, error -> {
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ma_thong_bao_ca_nhan", ma_thong_bao_ca_nhan);
                params.put("nv_thuc_hien", DataSession.getInstance().getMa_tai_khoan());
                params.put("nv_nhan", "");
                params.put("ngay_tao", ngay_tao.toString());
                params.put("type", "0");
                params.put("noi_dung", "vừa thay đổi thông tin một sản phẩm có ID là");
                params.put("noi_dung_quan_trong", "#" + ma_danh_muc);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                WebService.TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {
            if (requestCode == Configuration.CAMERA_ACTION_CODE) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                hinhSanPham.setImageBitmap(bitmap);
            } else if (requestCode == Configuration.STORAGE_SELECT_PHOTO) {
                Uri selectImg = data.getData();
                InputStream inputStream = null;
                try {
                    assert selectImg != null;
                    inputStream = getActivity().getContentResolver().openInputStream(selectImg);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BitmapFactory.decodeStream(inputStream);
                hinhSanPham.setImageURI(selectImg);
            }
        }
    }


}
