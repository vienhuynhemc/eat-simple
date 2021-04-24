package com.vientamthuong.eatsimple.diaLog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import com.vientamthuong.eatsimple.R;

public class DiaLogLostConnection extends Dialog {

    private Context context;

    public DiaLogLostConnection(@NonNull Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_lost_connection);
        init();
    }

    private void init() {
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


}
