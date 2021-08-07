package com.vientamthuong.eatsimple.menuNotify;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vientamthuong.eatsimple.SharedReferences.DataLocalManager;
import com.vientamthuong.eatsimple.checkout.AddressActivity;
import com.vientamthuong.eatsimple.date.DateTime;
import com.vientamthuong.eatsimple.loadData.VolleyPool;

import java.util.HashMap;
import java.util.Map;

public class CustomNotify {

    private String title;
    private String content;
    private View view;
   // private Context context;

    public CustomNotify(String title, String content, View view) {
        this.title = title;
        this.content = content;
        this.view = view;

       // EventRing.getInstance().setContext(view.getContext());
       // EventRing.getInstance().startAnim();
      //  EventRing.getInstance().reStartAnim();

        addNotify();
        reStart();

    }


    public void addNotify(){
        String urlLoad = "https://eat-simple-app.000webhostapp.com/addNotify.php";
        StringRequest request = new StringRequest(Request.Method.POST, urlLoad,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("ma_kh", DataLocalManager.getAccount().getId());
                params.put("title",title);
                params.put("content",content);
                params.put("time", new DateTime().toString());
                return params;
            }
        };
        VolleyPool.getInstance(view.getContext()).addRequest(request);
    }
    private void reStart(){
        EventRing.getInstance().setView(view);
        EventRing.getInstance().reStartAnim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
