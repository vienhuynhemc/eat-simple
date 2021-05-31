package com.vientamthuong.eatsimple.admin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.vientamthuong.eatsimple.R;

public class DiaLogConfirm extends Dialog {

    private Context context;
    private AppCompatButton btTry;
    private TextView textViewTitle;
    private TextView textViewContent;
    private AppCompatButton btIgnore;

    public DiaLogConfirm(@NonNull Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.admin_dialog_confirm);
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
        textViewTitle = findViewById(R.id.dialog_lost_connection_title);
        textViewContent = findViewById(R.id.content);
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

    public TextView getTextViewTitle() {
        return textViewTitle;
    }

    public TextView getTextViewContent() {
        return textViewContent;
    }

}
