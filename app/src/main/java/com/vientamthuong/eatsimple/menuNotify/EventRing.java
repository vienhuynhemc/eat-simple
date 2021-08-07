package com.vientamthuong.eatsimple.menuNotify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.vientamthuong.eatsimple.R;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.homePage.HomeMeowBottom;

public class EventRing {

    private View view;
    private static EventRing eventRing;
    private Animation animation;

    private EventRing() {
    }

    public static EventRing getInstance() {
        if (eventRing == null) {
            eventRing = new EventRing();
        }
        return eventRing;
    }

    public void startAnim() {
        if (DataLocalManager.getRing()){
            animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.activity_notity);
            view.startAnimation(animation);
        }
        eventClick();
    }
    public void reStartAnim(){
            DataLocalManager.setRing(true);
            startAnim();
    }

    private void endAnim() {
        view.clearAnimation();
    }
    private void eventClick(){

        view.setOnClickListener(v -> {
            endAnim();
            DataLocalManager.setRing(false);
            Intent intent = new Intent(v.getContext(), HomeMeowBottom.class);
            Bundle bundle = new Bundle();
            bundle.putString("dichuyen","ring");
            intent.putExtra("call",bundle);
            view.getContext().startActivity(intent);

        });

    }


    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

//    public Context getContext() {
//        return context;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }
}
