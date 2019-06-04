package com.mianze.apiexport.util;

import com.mianze.apiexport.constant.Constant;
import okhttp3.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by hexu on 2017/4/3.
 */
public class HttpUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    private static final MediaType XML = MediaType.parse("application/xml;charset=utf-8");

    private static OkHttpClient okHttpClient =
            new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();


    public static String postXML(String url, String soap) {
        RequestBody requestBody = RequestBody.create(XML, soap);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("网络异常6:", e);
            return null;
        }
    }

    public static String postLongFenQi(String url, String str) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), str);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
//                 .addHeader("rft-key", "3343346 ")
                 .addHeader("rft-key", "203663862")
                .addHeader("rft-token", "200000004")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("网络异常1:", e);
            return null;
        }
    }

    public static String postFangsiling(String url, String str) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), str);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("网络异常1:", e);
            return null;
        }
    }


    public static String postPaiPaiDai(String url, String str, String time, String timeSign, String sign) {
        RequestBody requestBody = RequestBody.create(JSON, str);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("x-ppd-appid", Constant.ppdId)
                .addHeader("x-ppd-timestamp-sign", timeSign)
                .addHeader("x-ppd-timestamp", time)
                .addHeader("x-ppd-sign", sign)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("网络异常1:", e);
            return null;
        }
    }

    public static String postJSON(String url, String json) {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("网络异常1:", e);
            return null;
        }
    }

    public static String post(String url,Object obj){
        RequestBody requestBody = RequestBody.create(JSON,GsonUtil.toString(obj));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200){
                return response.body().string();
            }else {
                log.error(response.toString());
                return null;
            }
        } catch (Exception e) {
            log.error("网络异常1:",e);
            return null;
        }
    }

    public static String post(String url,FormBody formBody){
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200){
                return response.body().string();
            }else {
                log.error("网络异常,http code:" + response.code() + ",url:" + url);
                log.error(response.body().string());
                return null;
            }
        } catch (Exception e) {
            log.error("网络异常5:",e);
            return null;
        }
    }

    public static void postAsync(String url,Object obj){
        RequestBody requestBody = RequestBody.create(JSON,GsonUtil.toString(obj));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    log.error("网络异常4", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                 //   System.out.println("返回:" + String.valueOf(response));
                }
            });
    }



    public static void postAsync2(String url,String json){
        RequestBody requestBody = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error("网络异常4",e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              //  System.out.println("返回:" + String.valueOf(response));
            }
        });
    }

    public static String get(String url){
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response.code() == 200){
                return response.body().string();
            }else {
                return null;
            }
        } catch (Exception e) {
            log.error("网络异常2", e);
            return null;
        }
    }


    public static void getAsync(String url){
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                log.error("网络异常3" , e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // System.out.println("返回:" + String.valueOf(response));
            }
        });
    }

    public static String getTest(String url){
        try {
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null,  new TrustManager[] { new X509TrustManager(){
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                System.out.println("*****************");
                for(X509Certificate certificate:x509Certificates){
                    System.out.println(certificate);
                }
                System.out.println(s);
                System.out.println("*****************");
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                System.out.println("------------------");
                for(X509Certificate certificate:x509Certificates){
                    System.out.println(certificate);
                }
                System.out.println(s);
                System.out.println("------------------");
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                System.out.println("getAcceptedIssuers");
                return new X509Certificate[0];
            }
        } }, new SecureRandom());
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sc.getSocketFactory())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        System.out.println("+++++++++++++++++");
                        System.out.println(s);
                        System.out.println(sslSession.getPeerHost() + "," + sslSession.getProtocol());
                        System.out.println("+++++++++++++++++");
                        return true;
                    }
                })
                .build();
        Request request = new Request.Builder().url(url).build();
        Call call = httpClient.newCall(request);

            Response response = call.execute();
            System.out.println(response);
            if (response.code() == 200){
                return response.body().string();
            }else {
                return null;
            }
        } catch (Exception e) {
            log.error("网络异常2", e);
            return null;
        }
    }


    //有些测试地址https证书无效的,这个方法可以信任所有证书,仅供测试使用
    public static String postTest(String url,Object model){
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null,  new TrustManager[] { new X509TrustManager(){
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sc.getSocketFactory())
                    .hostnameVerifier((s, sslSession) -> true)
                    .build();


            FormBody.Builder builder = new FormBody.Builder();
            Field[] declaredFields = model.getClass().getDeclaredFields();
            for (Field field:declaredFields){
                field.setAccessible(true);
                Object o = field.get(model);
                if (o != null){
                    builder.add(field.getName(),o.toString());
                }
            }
            FormBody formBody = builder.build();
            System.out.println(formBody.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                System.out.println(response);
                if (response.code() == 200){
                    return response.body().string();
                }else {
                    return null;
                }
            } catch (Exception e) {
                log.error("网络异常1:",e);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (null != param) {
                List<BasicNameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    private static CloseableHttpClient getHttpClient() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        //指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //信任任何链接
            TrustStrategy anyTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        //设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        //构建客户端
        return HttpClientBuilder.create().setConnectionManager(connManager).build();
    }


}
