package org.windy.scutwifimanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
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
        editTextUsr.setText(loginKeyController.getUsername());
        editTextPasswd.setText(loginKeyController.getPassword());
    }

    public void connect(View view) throws IOException {
        String usename = editTextUsr.getText().toString();
        String passwd = editTextPasswd.getText().toString();
        TextView output = findViewById(R.id.output);
        output.setText("");
        loginKeyController.saveLoginKey(usename,passwd);

        new Thread(()->
        {
            try {
                if (WifiUtil.connectScutWifi(usename, passwd))
                    output.append("success");
                else
                    output.append("fail");
            } catch (IOException e) {
                e.printStackTrace();
                output.append("fail");
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}