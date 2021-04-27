package org.windy.scutwifimanager;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Config config;
    EditText editTextUsr;
    EditText editTextPasswd;
    Switch switcher;
    TextView console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.config = new Config(this);

        editTextUsr = findViewById(R.id.username);
        editTextPasswd = findViewById(R.id.password);
        switcher = findViewById(R.id.switcher);
        console = findViewById(R.id.console);

        editTextUsr.setText(config.getUsername());
        editTextPasswd.setText(config.getPassword());

        switcher.setChecked(config.getAutoConnect());
        setSwitchListener();

        if (config.getAutoConnect())
            connect();
    }
    //带终端输出的连接
    public void connect(View view){
        String usename = editTextUsr.getText().toString();
        String passwd = editTextPasswd.getText().toString();
        //保存帐号密码
        config.saveLoginKey(usename,passwd);

        new Thread(()->
        {
            try {
                ConnectInfo info =  WifiUtil.connectScutWifi(usename,passwd);
                console.post(()->
                {
                    console.setText("");
                    console.append("重定向到登陆页面：" + info.isRedirect() + "\n");
                    console.append("wlanuserip：" + info.getWlanuserip() + "\n");
                    console.append("wlanacip：" + info.getWlanacip() + "\n");
                    console.append("lasturl：" + info.getLastURL() + "\n");
                    console.append("设备是否已经联网：" + info.isSuccess() + "\n");
                });
            } catch (IOException e) {
                console.post(() -> {
                    console.setText("");
                    console.append("设备是否已经联网：false\n");
                    console.append("错误消息：" + e.getMessage());
                });
            }
//            catch (InterruptedException e){
//
//            }
        }).start();
    }
    //直接连接，如果成功则程序退出
    public void connect()
    {
        String usename = editTextUsr.getText().toString();
        String passwd = editTextPasswd.getText().toString();
        config.saveLoginKey(usename,passwd);

        new Thread(()->
        {
            try {
                ConnectInfo info =  WifiUtil.connectScutWifi(usename,passwd);
                if (info.isSuccess())
                    System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    //保存开关状态
    public void setSwitchListener()
    {
        switcher.setOnCheckedChangeListener( (view , isChecked) -> config.setAutoConnect(isChecked));
    }
}