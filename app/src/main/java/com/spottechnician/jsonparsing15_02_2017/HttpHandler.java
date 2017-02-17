package com.spottechnician.jsonparsing15_02_2017;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by MyPC on 15-02-2017.
 */

public class HttpHandler {

    public HttpHandler() {

    }

    public String makeServiceCall(String reqUrl,String userName, String userPassword) {
        String response = null;
        Log.v("Testing", "userName"+userName+" userPassword"+userPassword);
        Log.e("Testing","reqURL"+reqUrl);

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();

            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            String data = URLEncoder.encode("login_id", "UTF-8") + "=" + URLEncoder.encode(userName,"UTF-8") + "&"
                    +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(userPassword,"UTF-8");
            br.write(data);
            br.flush();
            if(br!=null) {
                br.close();
            }
            if(os!=null) {
                os.close();
            }

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String convertStreamToString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while((line=reader.readLine())!=null) {
                sb.append(line).append('\n');
                Log.v("Testing", "line: "+line);
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
