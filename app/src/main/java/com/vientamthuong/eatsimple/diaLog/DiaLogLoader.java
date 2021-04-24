package com.vientamthuong.eatsimple.diaLog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import com.vientamthuong.eatsimple.R;

public class DiaLogLoader extends Dialog {

    private Context context;

    public DiaLogLoader(@NonNull Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_loader);
        init();
    }

    private void init() {
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
