package org.windy.scutwifimanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    LoginKeyController loginKeyController;
    EditText editTextUsr;
    EditText editTextPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.loginKeyController = new LoginKeyController(this);
        editTextUsr = findViewById(R.id.username);
        editTextPasswd = findViewById(R.id.password);
        loginKeyController.reloadLoginKey(editTextUsr,editTextPasswd);
    }

    public void connect(View view) throws IOException {
        String usename = editTextUsr.getText().toString();
        String passwd = editTextPasswd.getText().toString();
        loginKeyController.saveLoginKey(usename,passwd);
        new Thread(()->
        {
            try {
                WifiUtil.connectScutWifi(usename,passwd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}