package com.vientamthuong.eatsimple.fontAwesome;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FontAwesomeManager {

    private static FontAwesomeManager fontAwesomeManager;

    private FontAwesomeManager() {

    }

    public static FontAwesomeManager getInstance() {
        if (fontAwesomeManager == null) {
            fontAwesomeManager = new FontAwesomeManager();
        }
        return fontAwesomeManager;
    }

    public void addIcon(TextView textView, String type, String icon, Context context) {
        Typeface font;
        switch (type) {
            case "fab":
                font = Typeface.createFromAsset(context.getAssets(), "fonts/font-awesome/fa-brands-400.ttf");
                break;
            case "fas":
                font = Typeface.createFromAsset(context.getAssets(), "fonts/font-awesome/fa-solid-900.ttf");
                break;
            case "far":
                font = Typeface.createFromAsset(context.getAssets(), "fonts/font-awesome/fa-regular-400.ttf");
                break;
            default:
                font = null;
        }
        textView.setTypeface(font);
        textView.setText(icon);
    }

}
