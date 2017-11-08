package util;


import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by LiXuan on 2017/11/2.
 */
public class HttpUtil {
    public static Logger logger = Logger.getLogger(HttpUtil.class);
    public <T> String doGet(T para){
        String url = getUrl(para,para.getClass());
        System.out.println("url:"+url);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type","application/json; charset=utf-8");
        CloseableHttpClient httpClient = getHttpClient();
        String result = null;
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            result = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private CloseableHttpClient getHttpClient(){
        CloseableHttpClient httpClient = null;
        try {
            PoolingHttpClientConnectionManager pool;
            RequestConfig requestConfig;
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register(
                    "http", PlainConnectionSocketFactory.getSocketFactory()).register(
                    "https", sslsf).build();
            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
            pool.setMaxTotal(200);
            // 设置最大路由
            pool.setDefaultMaxPerRoute(2);
            // 根据默认超时限制初始化requestConfig
            int socketTimeout = 10000;
            int connectTimeout = 10000;
            int connectionRequestTimeout = 10000;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(
                    connectionRequestTimeout).setSocketTimeout(socketTimeout).setConnectTimeout(
                    connectTimeout).build();
            httpClient = HttpClients.custom()
                    .setConnectionManager(pool)
                    .setDefaultRequestConfig(requestConfig)
                    .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return httpClient;
    }

    //拼接URL
    private <T> String getUrl(T obj,Class<?> cl){
        try {
            StringBuffer url = new StringBuffer();
            url.append(getPropertyValue("host"));
            //通过反射获取注解中的网址
            RequestAnnotation annotation = cl.getAnnotation(RequestAnnotation.class);
            url.append(annotation.uri() + "?");
            Field[] fs = cl.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field field = fs[i];
                String value = getFieldValue(obj, field);
                url.append(field.getName() + "=" + value + "&");
            }
            if (url.length() > 0)
                url.deleteCharAt(url.length() - 1);
            return url.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //从不同的配置文件中获取网址
    public String getPropertyValue(String key) {
        Properties properties=new Properties();
        FileInputStream fileInputStream;
        String url= this.getClass().getClassLoader().getResource("config.properties").getPath();
        try{

            fileInputStream=new FileInputStream(url);
            properties.load(fileInputStream);
            fileInputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

    private  <T> String getFieldValue(T obj, Field field)
    {
        String value = null;
        try {
            Class cl = obj.getClass();
            String name  = field.getName();
            String methodName = name.substring(0, 1).toUpperCase()+ name.substring(1);
            Method method = cl.getMethod("get"+methodName);
            value = (String) method.invoke(obj);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return value;
    }
}
