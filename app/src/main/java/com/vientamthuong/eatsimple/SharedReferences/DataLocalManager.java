package com.vientamthuong.eatsimple.SharedReferences;

import android.content.Context;

import com.google.gson.Gson;
import com.vientamthuong.eatsimple.model.Account;

import java.util.HashMap;

public class DataLocalManager {
    private static final String REF_FIRST = "REF_FIRST";
    private static final String REF_VALID = "REF_VALID";
    private static final String REF_USERNAME = "REF_USERNAME";
    private static final String REF_PASSWORD = "REF_PASSWORD";
    private static final String REF_ACCOUNT = "REF_ACCOUNT";
    private static final String REF_RING = "REF_RING";
    private static DataLocalManager instance;
    private MyShareReferences myShareReferences;

    public static void init(Context context){
        instance = new DataLocalManager();
        instance.myShareReferences = new MyShareReferences(context);
    }
    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }
    public static void setFirstInstalled(boolean isFirst){
        DataLocalManager.getInstance().myShareReferences.putBooleanValue(REF_FIRST,isFirst);
    }
    public static boolean getFirstInstalled(){
        return DataLocalManager.getInstance().myShareReferences.getBooleanValue(REF_FIRST);
    }
    public static void setAccounts(Account account){
        Gson gson = new Gson();
        String stringJSONAccount = gson.toJson(account);
        DataLocalManager.getInstance().myShareReferences.putStringValue(REF_ACCOUNT,stringJSONAccount);
    }
    public static void setAccount(String response){
        DataLocalManager.getInstance().myShareReferences.putStringValue(REF_ACCOUNT,response);
    }
    public static Account getAccount(){
        Gson gson = new Gson();
        String stringJSONAccount = DataLocalManager.getInstance().myShareReferences.getStringValue(REF_ACCOUNT);
        Account account = gson.fromJson(stringJSONAccount,Account.class);
        return account;
    }
    public static void setValid(boolean input){
        DataLocalManager.getInstance().myShareReferences.putBooleanValue(REF_VALID,input);
    }
    public static boolean isValid(){
        return DataLocalManager.getInstance().myShareReferences.getBooleanValue(REF_VALID);
    }

    public static void setRing(boolean input){
        DataLocalManager.getInstance().myShareReferences.putBooleanValue(REF_RING,input);
    }
    public static boolean getRing() {
    return DataLocalManager.getInstance().myShareReferences.getBooleanValue(REF_RING);
    }

    public static HashMap<String,String> getLoginInput(){
        String username = DataLocalManager.getInstance().myShareReferences.getStringValue(REF_USERNAME);
        String password = DataLocalManager.getInstance().myShareReferences.getStringValue(REF_PASSWORD);

        HashMap<String,String> user = new HashMap<>();
        user.put(username,password);

        return user;
    }
    public static void setLoginInput(String username, String password){
        DataLocalManager.getInstance().myShareReferences.putStringValue(REF_USERNAME,username);
        DataLocalManager.getInstance().myShareReferences.putStringValue(REF_PASSWORD,password);
    }

}
