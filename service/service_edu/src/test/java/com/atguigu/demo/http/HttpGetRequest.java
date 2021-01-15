package com.atguigu.demo.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONObject;
/**
 * @program: guli_parent
 * @description:
 * @author: Mr.Zhao
 * @create: 2020-12-20 21:36
 **/
public class HttpGetRequest {

    public static void main(String[] args) {
        try {
            String s = doPost("http://localhost:88/api/product/brand/save",null);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Get Request
     *
     * @return
     * @throws Exception
     */
    public static String doGet(String url) throws Exception {
        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type",
                "application/text");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception(
                    "HTTP Request is not success, Response code is "
                            + httpURLConnection.getResponseCode());
        }

        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        } finally {

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }
        return resultBuffer.toString();
    }
    /**
     * Http post
     * @param url post请求url
     * @param params 参数
     * @return
     * @throws Exception
     */
    public static String doPost(String url,Map<String, String> params) throws Exception {
        HttpPost httpPost=new HttpPost(url);
        HttpClient httpclient=new DefaultHttpClient();
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(params!=null){
            BasicNameValuePair bnvp=null;
            for (Map.Entry<String, String> p : params.entrySet()) {
                bnvp=new BasicNameValuePair(p.getKey(), p.getValue());
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity respEntity = response.getEntity();//获得返回数据
        String text = EntityUtils.toString(respEntity, "UTF-8");
        JSONObject obj = (JSONObject) JSONObject.parse(text);
        httpclient.getConnectionManager().shutdown();
        return obj.toJSONString();
    }
}
