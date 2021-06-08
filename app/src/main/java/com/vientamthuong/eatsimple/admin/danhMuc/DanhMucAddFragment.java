package com.vientamthuong.eatsimple.admin.danhMuc;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.admin.Configuration;
import com.vientamthuong.eatsimple.admin.WebService;
import com.vientamthuong.eatsimple.admin.dialog.DiaLogConfirm;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DanhMucAddFragment extends Fragment {

    private CardView buttonBack;
    private CardView buttonCamera;
    private CardView buttonStorage;
    private ImageView hinhDanhMuc;
    private EditText editTextTenDanhMuc;
    private TextView textViewThieuTenDanhMuc;
    private FloatingActionButton done;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_danh_muc_add, container, false);
        getView(view);
        action();
        return view;
    }

    private void getView(View view) {
        buttonBack = view.findViewById(R.id.buttonBack);
        buttonCamera = view.findViewById(R.id.buttonCamera);
        buttonStorage = view.findViewById(R.id.buttonStorage);
        hinhDanhMuc = view.findViewById(R.id.hinhDanhMuc);
        editTextTenDanhMuc = view.findViewById(R.id.tenDanhMuc);
        textViewThieuTenDanhMuc = view.findViewById(R.id.thieuTenDanhMuc);
        done = view.findViewById(R.id.buttonThem);
    }

    private void action() {
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
            String ten_danh_muc = editTextTenDanhMuc.getText().toString().trim();
            if (ten_danh_muc.length() == 0) {
                Toast.makeText(getActivity(), "Thiếu thông tin", Toast.LENGTH_SHORT).show();
                textViewThieuTenDanhMuc.setVisibility(View.VISIBLE);
            } else {
                textViewThieuTenDanhMuc.setVisibility(View.GONE);
                DiaLogConfirm diaLogConfirm = new DiaLogConfirm(getActivity());
                diaLogConfirm.getTextViewTitle().setText("Thêm danh mục mới");
                diaLogConfirm.getTextViewContent().setText("Bạn có chắc chắn rằng mình muốn tạo một danh mục mới?");
                diaLogConfirm.getBtTry().setText("Không");
                diaLogConfirm.getBtIgnore().setText("Tạo");
                diaLogConfirm.getBtTry().setOnClickListener(v1 -> diaLogConfirm.dismiss());
                diaLogConfirm.getBtIgnore().setOnClickListener(v1 -> {
                    diaLogConfirm.dismiss();
                    addNewDanhMuc(ten_danh_muc);
                });
                diaLogConfirm.show();
            }
        });
        editTextTenDanhMuc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    textViewThieuTenDanhMuc.setVisibility(View.VISIBLE);
                } else {
                    textViewThieuTenDanhMuc.setVisibility(View.GONE);
                }
            }
        });
    }

    private void addNewDanhMuc(String ten_danh_muc) {
        Map<String, String> result = new HashMap<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebService.lay_ma_danh_muc_tiep_theo,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            result.put("id", jsonObject.getString("ma_danh_muc"));
                        }
                        String ma_danh_muc = "danh_muc_" + result.get("id");
                        addToFirebase(ten_danh_muc, ma_danh_muc);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        VolleyPool.getInstance(getActivity()).addRequest(stringRequest);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference root = firebaseDatabase.getReference();
    }

    private void addToFirebase(String ten_danh_muc, String ma_danh_muc) {
        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Tải dữ liệu lên server");
        pd.show();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference root = firebaseStorage.getReference();
        StorageReference riversRef = root.child("danh_muc/" + ma_danh_muc + ".png");
        hinhDanhMuc.setDrawingCacheEnabled(true);
        hinhDanhMuc.buildDrawingCache();
        Bitmap bitmap = hinhDanhMuc.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        riversRef.putBytes(data).addOnSuccessListener(taskSnapshot -> {
            pd.dismiss();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference r = firebaseDatabase.getReference();
            DatabaseReference d = r.child("danh_muc").child(ma_danh_muc);
        }).addOnFailureListener(e -> {

        }).addOnProgressListener(snapshot -> {
            double p = (100.00 * snapshot.getBytesTransferred()) / (double) snapshot.getTotalByteCount();
            pd.setMessage("Tiến độ: " + (int) p + " %");
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {
            if (requestCode == Configuration.CAMERA_ACTION_CODE) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                hinhDanhMuc.setImageBitmap(bitmap);
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
                hinhDanhMuc.setImageURI(selectImg);
            }
        }
    }
}
