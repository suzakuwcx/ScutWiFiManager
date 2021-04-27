package org.windy.scutwifimanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

//表示这个应用程序的设置类，用于存储和读取设置，例如帐号密码
public class Config {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private AppCompatActivity activity;

    public Config(AppCompatActivity activity) {
        this.activity = activity;
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }
    //设置登陆密钥（用户名+密码）
    public void saveLoginKey(String usrname, String passwd) {
        editor.putString("Username", usrname);
        editor.putString("Password", passwd);
        editor.apply();
    }
    //设置是否自动连接
    public void setAutoConnect(boolean isConnect)
    {
        editor.putBoolean("isConnect",isConnect);
        editor.apply();
    }
    //获取自动连接配置
    public boolean getAutoConnect()
    {
        return sharedPref.getBoolean("isConnect",false);
    }
    //设置用户名
    public void setUsername(String usrname) {
        editor.putString("Username", usrname);
        editor.apply();
    }
    //设置密码
    public void setPassword(String passwd) {
        editor.putString("Password", passwd);
        editor.apply();
    }
    //获取用户名
    public String getUsername()
    {
        return sharedPref.getString("Username","");
    }
    //获取密码
    public String getPassword()
    {
        return sharedPref.getString("Password","");
    }
}
