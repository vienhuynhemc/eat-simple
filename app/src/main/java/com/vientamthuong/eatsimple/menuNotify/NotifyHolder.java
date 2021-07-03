package com.vientamthuong.eatsimple.menuNotify;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.vientamthuong.eatsimple.R;

import org.jetbrains.annotations.NotNull;

public class NotifyHolder extends RecyclerView.ViewHolder {

    private TextView title,content,time;
    private LottieAnimationView ring,img;
    private CardView layout;

    public NotifyHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        getView(itemView);

    }
    private void getView(View view){

        title = view.findViewById(R.id.title);
        content = view.findViewById(R.id.content);
        time = view.findViewById(R.id.time);
        ring = view.findViewById(R.id.ring);
        img = view.findViewById(R.id.lottieAnimationView);
        layout = view.findViewById(R.id.notify_layout);

    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getContent() {
        return content;
    }

    public void setContent(TextView content) {
        this.content = content;
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public LottieAnimationView getRing() {
        return ring;
    }

    public void setRing(LottieAnimationView ring) {
        this.ring = ring;
    }

    public LottieAnimationView getImg() {
        return img;
    }

    public void setImg(LottieAnimationView img) {
        this.img = img;
    }

    public CardView getLayout() {
        return layout;
    }

    public void setLayout(CardView layout) {
        this.layout = layout;
    }
}
