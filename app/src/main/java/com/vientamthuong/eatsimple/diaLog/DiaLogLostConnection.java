package com.vientamthuong.eatsimple.diaLog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.vientamthuong.eatsimple.R;

public class DiaLogLostConnection extends Dialog {

    private AppCompatActivity appCompatActivity;
    private AppCompatButton btTry;
    private AppCompatButton btIgnore;

    public DiaLogLostConnection(@NonNull AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
        this.appCompatActivity = appCompatActivity;
        setContentView(R.layout.dialog_lost_connection);
        getView();
        init();
    }

    @Override
    public void onBackPressed() {
        // Hiện thực lại không cho làm gì cả
    }

    private void getView() {
        btTry = findViewById(R.id.dialog_lost_connection_try);
        btIgnore = findViewById(R.id.dialog_lost_connection_ignore);
    }

    private void init() {
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    // GETTER AND SETTER
    public AppCompatButton getBtTry() {
        return btTry;
    }

    public AppCompatButton getBtIgnore() {
        return btIgnore;
    }
}
