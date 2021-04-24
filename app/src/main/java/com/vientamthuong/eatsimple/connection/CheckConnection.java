package com.vientamthuong.eatsimple.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

public class CheckConnection {

    private static CheckConnection checkConnection;

    private CheckConnection() {
    }

    public static CheckConnection getInstance() {
        if (checkConnection == null) {
            checkConnection = new CheckConnection();
        }
        return checkConnection;
    }

    public boolean isConnected(AppCompatActivity appCompatActivity) {
        // Lấy connection của appCompatAcitivty truyền vào
        ConnectivityManager connectivityManager = (ConnectivityManager) appCompatActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobieConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        // Kiểm tra kết nối
        // 1. Wifi
        if (wifiConnection != null && wifiConnection.isConnected()) {
            return true;
        }
        // 2. Mobie
        return mobieConnection != null && mobieConnection.isConnected();
    }
}
