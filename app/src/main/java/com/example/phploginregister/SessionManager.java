package com.example.phploginregister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    int PRIVATE_MODE=0;
    private static final String PREF_NAME="LOGIN";
    private static final String LOGIN="IS_LOGIN";

    public static final String NAME="NAME";
    public static final String EMAIL="EMAIL";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public void createSession(String name,String email){
        editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
        editor.putString(EMAIL,email);
        editor.apply();

    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);

    }

    public void checkLogin(){
        if (!isLoggin()){
            Intent i=new Intent(context,Login_Activity.class);
            context.startActivity(i);
            ((Home)context).finish();
        }
    }

    public HashMap<String, String> userDetails(){
        HashMap<String,String> user=new HashMap<>();
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        return user;
    }

    public void logOut(){
        editor.clear();
        editor.commit();
        Intent intent=new Intent(context,Login_Activity.class);
        context.startActivity(intent);
        ((Home)context).finish();

    }

}
