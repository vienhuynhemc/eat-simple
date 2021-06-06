package com.vientamthuong.eatsimple.loadData;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.toolbox.ImageRequest;
import com.vientamthuong.eatsimple.admin.model.ThongBaoChuong;
import com.vientamthuong.eatsimple.danhMuc.DanhMuc;

public class LoadImageForView {

    // Các image view nằm riêng lẻ -------------------------------
    // Tùy thuộc vào type mà sẽ chọn view hoặc image view
    private final int type;
    private View view;
    private ImageView imageView;
    private final String url;
    // LottieAnimationView để ẩn nó đi
    private LottieAnimationView lottieAnimationView;
    // Để chạy vào luồng AI
    private final AppCompatActivity appCompatActivity;
    // Cập nhật khi load hình thành công
    private boolean isComplete;
    private boolean isError;
    private boolean isStart;
    //-----------------------------------------------------------
    // Các object có hình ảnh -----------------------------------
    //  Danh mục
    private DanhMuc danhMuc;
    // Top header admin
    private CardView cardView;
    private CardView cardViewIm;
    // Danh mục bên admin
    private com.vientamthuong.eatsimple.admin.model.DanhMuc danhMucAdmin;
    // Thông báo chuông
    private ThongBaoChuong thongBaoChuong;
    //-----------------------------------------------------------

    public LoadImageForView(ImageView imageView, AppCompatActivity appCompatActivity, LottieAnimationView lottieAnimationView, int type, String url) {
        this.imageView = imageView;
        this.type = type;
        this.lottieAnimationView = lottieAnimationView;
        this.appCompatActivity = appCompatActivity;
        this.url = url;
    }

    public LoadImageForView(View view, AppCompatActivity appCompatActivity, LottieAnimationView lottieAnimationView, int type, String url) {
        this.type = type;
        this.lottieAnimationView = lottieAnimationView;
        this.view = view;
        this.appCompatActivity = appCompatActivity;
        this.url = url;
    }

    // Danh mục
    public LoadImageForView(AppCompatActivity appCompatActivity, DanhMuc danhMuc, int type) {
        this.danhMuc = danhMuc;
        this.appCompatActivity = appCompatActivity;
        this.type = type;
        // Lấy url
        this.url = danhMuc.getHinh();
    }

    // Danh mục bên admin
    public LoadImageForView(AppCompatActivity appCompatActivity, com.vientamthuong.eatsimple.admin.model.DanhMuc danhMuc,
                            int type) {
        this.danhMucAdmin = danhMuc;
        this.appCompatActivity = appCompatActivity;
        this.type = type;
        // Lấy url
        this.url = danhMuc.getUrl();
    }

    // Thông báo chuông
    public LoadImageForView(AppCompatActivity appCompatActivity, ThongBaoChuong thongBaoChuong, int type) {
        this.thongBaoChuong = thongBaoChuong;
        this.appCompatActivity = appCompatActivity;
        this.type = type;
        // Lấy url
        this.url = thongBaoChuong.getUrl();
    }

    // Hình đại diện top header admin
    public LoadImageForView(String url, AppCompatActivity appCompatActivity, ImageView imageView, int type, CardView cardView, CardView cardViewIm) {
        this.imageView = imageView;
        this.appCompatActivity = appCompatActivity;
        this.type = type;
        this.url = url;
        this.cardViewIm = cardViewIm;
        this.cardView = cardView;
    }

    public void run() {
        ImageRequest imageRequest = new ImageRequest(url, response -> {
            switch (type) {
                case LoadDataConfiguration.VIEW_NORMAL:
                    view.setBackground(new BitmapDrawable(response));
                    lottieAnimationView.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);
                    break;
                case LoadDataConfiguration.IMAGE_VIEW:
                    imageView.setImageBitmap(response);
                    lottieAnimationView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    break;
                case LoadDataConfiguration.IMAGE_DANH_MUC:
                    danhMuc.setBitmap(response);
                    break;
                case LoadDataConfiguration.IMAGE_THONG_BAO_CHUONG:
                    thongBaoChuong.setHinh_nguoi_gui(response);
                    break;
                case LoadDataConfiguration.TOP_HEADER_ADMIN:
                    imageView.setImageBitmap(response);
                    cardView.setVisibility(View.INVISIBLE);
                    cardViewIm.setVisibility(View.VISIBLE);
                    break;
                case LoadDataConfiguration.DANH_MUC_ADMIN:
                    danhMucAdmin.setHinh(response);
                    break;
            }
            isComplete = true;
        }, 0, 0, ImageView.ScaleType.FIT_CENTER, null, error -> {
            isError = true;
            Toast.makeText(appCompatActivity, "Lỗi tải hình!", Toast.LENGTH_SHORT).show();
        });
        VolleyPool.getInstance(appCompatActivity).addRequest(imageRequest);
    }

    // Getter and setter
    public boolean isComplete() {
        return isComplete;
    }

    public boolean isError() {
        return isError;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public int getType() {
        return type;
    }

    public void setError(boolean error) {
        this.isError = error;
    }
}
