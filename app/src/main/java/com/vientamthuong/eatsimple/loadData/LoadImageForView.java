package com.vientamthuong.eatsimple.loadData;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.toolbox.ImageRequest;

public class LoadImageForView {

    // Tùy thuộc vào type mà sẽ chọn view hoặc image view
    private final int type;
    private View view;
    private ImageView imageView;
    private final String url;
    // LottieAnimationView để ẩn nó đi
    private final LottieAnimationView lottieAnimationView;
    // Để chạy vào luồng AI
    private final AppCompatActivity appCompatActivity;
    // Cập nhật khi load hình thành công
    private boolean isComplete;
    private boolean isError;
    private boolean isStart;

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

    public void run() {
        ImageRequest imageRequest = new ImageRequest(url, response -> {
            if (type == LoadDataConfiguration.VIEW_NORMAL) {
                view.setBackground(new BitmapDrawable(response));
                view.setVisibility(View.VISIBLE);
            } else if (type == LoadDataConfiguration.IMAGE_VIEW) {
                imageView.setImageBitmap(response);
                imageView.setVisibility(View.VISIBLE);
            }
            isComplete = true;
            lottieAnimationView.setVisibility(View.GONE);
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

    public void setError(boolean error){
        this.isError = error;
    }
}
