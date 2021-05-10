package com.vientamthuong.eatsimple.homePage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vientamthuong.eatsimple.R;

public class CustomDanhMucViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView textView;
    private View layout;

    public CustomDanhMucViewHolder(@NonNull View itemView, int type) {
        super(itemView);
        this.layout = itemView;
        if (type == HomePageConfiguration.REAL) {
            getView(itemView);
        }
    }

    private void getView(View view) {
        imageView = view.findViewById(R.id.activity_home_page_custom_danh_muc_image_view);
        textView = view.findViewById(R.id.activity_home_page_custom_danh_muc_text_view);
    }

    // GETTER AND SETTER
    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public View getLayout() {
        return layout;
    }
}
