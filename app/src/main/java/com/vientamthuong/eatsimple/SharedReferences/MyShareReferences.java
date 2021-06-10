package com.vientamthuong.eatsimple.SharedReferences;

import android.content.Context;
import android.content.SharedPreferences;

public class MyShareReferences {
    private static final String MY_SHARE_REFERENCES = "MY_SHARE_REFERENCE";
    private Context mContext;

    public MyShareReferences(Context mContext) {
        this.mContext = mContext;

    }
    public void putBooleanValue(String key, boolean value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARE_REFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARE_REFERENCES,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }
    public void putStringValue(String key, String value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARE_REFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String getStringValue(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARE_REFERENCES,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}
