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
    public static boolean connectScutWifi(String usrname, String passwd) throws IOException {
        URL url = new URL("http://www.baidu.com");
        HttpURLConnection session = (HttpURLConnection) url.openConnection();
        session.setRequestMethod("GET");
        session.setInstanceFollowRedirects(false);
        session.setConnectTimeout(3000);
        session.connect();

        if (session.getResponseCode() != HttpURLConnection.HTTP_MOVED_TEMP)
            return true;

        Scanner in = new Scanner(session.getInputStream());
        String wlanuserip = null;
        String wlanacip = null;
        while (in.hasNextLine())
        {
            if (in.hasNext("<NextURL>\\S+</NextURL>"))
            {
                wlanuserip = in.findInLine("(?<=wlanuserip=)([0-9|.]+)");
                wlanacip = in.findInLine("(?<=wlanacip=)([0-9|.]+)");
            }
            else in.nextLine();
        }

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

        OutputStream postStream = session.getOutputStream();
        postStream.write(params.getBytes());
        postStream.close();

        if (session.getHeaderField("Location").contains("3.htm"))
        {
            session.disconnect();
            return true;
        }
        else
        {
            session.disconnect();
            return false;
        }
    }

}
