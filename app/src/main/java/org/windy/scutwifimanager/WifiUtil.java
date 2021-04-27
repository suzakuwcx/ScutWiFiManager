package org.windy.scutwifimanager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class WifiUtil {
    //连接学校的wifi
    public static ConnectInfo connectScutWifi(String usrname, String passwd) throws IOException {
        ConnectInfo connectInfo = new ConnectInfo();
//        if (true)
//            return connectInfo;
        if (usrname.equals("") || passwd.equals(""))
        {
            connectInfo.setRedirect(false);
            connectInfo.setIsSuccess(false);
            return connectInfo;
        }
        URL url = new URL("http://www.baidu.com/");
        HttpURLConnection session = (HttpURLConnection) url.openConnection();
        session.setRequestMethod("GET");
        session.setInstanceFollowRedirects(false);
        session.setConnectTimeout(3000);
        session.connect();
        //捕获学校的强制门户
        if (session.getResponseCode() != HttpURLConnection.HTTP_MOVED_TEMP)
        {
            connectInfo.setRedirect(false);
            connectInfo.setIsSuccess(true);
            return connectInfo;
        }

        connectInfo.setRedirect(true);

        Scanner in = new Scanner(session.getInputStream());
        String wlanuserip = null;
        String wlanacip = null;
        //从返回报文中读取相关配置
        while (in.hasNextLine())
        {
            if (in.hasNext("<NextURL>\\S+</NextURL>"))
            {
                wlanuserip = in.findInLine("(?<=wlanuserip=)([0-9|.]+)");
                wlanacip = in.findInLine("(?<=wlanacip=)([0-9|.]+)");
            }
            else in.nextLine();
        }
        connectInfo.setWlanuserip(wlanuserip);
        connectInfo.setWlanacip(wlanacip);

        session.disconnect();

        String params =
                "DDDDD=" + URLEncoder.encode(usrname, "UTF-8") + "&" +
                        "upass=" + URLEncoder.encode(passwd, "UTF-8") + "&" +
                        "R1=" + URLEncoder.encode("0", "UTF-8") + "&" +
                        "R2=" + URLEncoder.encode("", "UTF-8") + "&" +
                        "R6=" + URLEncoder.encode("0", "UTF-8") + "&" +
                        "para=" + URLEncoder.encode("00", "UTF-8") + "&" +
                        "0MKKey=" + URLEncoder.encode("123456", "UTF-8");

        url = new URL("https://s.scut.edu.cn:801/eportal/" +
                "?c=ACSetting&a=Login" +
                "&wlanuserip=" + wlanuserip +
                "&wlanacip=" + wlanacip +
                "&wlanacname=" +
                "&redirect=" +
                "&session=" +
                "&vlanid=0" +
                "&port=" +
                "&iTermType=1" +
                "&protocol=https:");

        session = (HttpsURLConnection) url.openConnection();

        session.setRequestMethod("POST");
        session.setDoInput(true);
        session.setDoOutput(true);
        session.setInstanceFollowRedirects(true);
        session.setUseCaches(false);
        session.setConnectTimeout(3000);
        session.setRequestProperty("Content-Length", String.valueOf(params.length()));
        session.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        session.connect();
        //向服务器输出密码
        OutputStream postStream = session.getOutputStream();
        postStream.write(params.getBytes());
        postStream.close();
        //读取返回页面，如果返回页面是3.htm说明连接成功
        connectInfo.setLastURL(session.getHeaderField("Location"));

        session.disconnect();

        return connectInfo;
    }

}
