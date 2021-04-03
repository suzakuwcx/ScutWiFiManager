package org.windy.scutwifimanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginKeyController {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private AppCompatActivity activity;

    public LoginKeyController(AppCompatActivity activity) {
        this.activity = activity;
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void reloadLoginKey(EditText usrEdit, EditText passwdEdit) {
        usrEdit.setText(sharedPref.getString("Username", ""));
        passwdEdit.setText(sharedPref.getString("Password", ""));
    }

    public void saveLoginKey(String usrname, String passwd) {
        editor.putString("Username", usrname);
        editor.putString("Password", passwd);
        editor.apply();
    }

    public void setUsername(String usrname) {
        editor.putString("Username", usrname);
        editor.apply();
    }

    public void setPassword(String passwd) {
        editor.putString("Password", passwd);
        editor.apply();
    }

    public String getUsername()
    {
        return sharedPref.getString("Username","");
    }

    public String getPassword()
    {
        return sharedPref.getString("Password","");
    }
}
