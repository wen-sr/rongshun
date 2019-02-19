package com.rongshun.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * apache common HttpClient 4.2 以上版本 HTTP请求
 * 配带连接池
 * @author kun.zhang@downjoy.com
 *
 */
public class ApacheHttpUtils {

    private static final Logger logger= LoggerFactory.getLogger(ApacheHttpUtils.class);
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000).setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000).build();

    private static ApacheHttpUtils instance = null;
    public ApacheHttpUtils() {
        super();
    }
    public static ApacheHttpUtils getInstance(){
        if(instance==null){
            instance  = new ApacheHttpUtils();
        }
        return instance;
    }
    /**
     * 发送 post请求
     * @param url
     * @return
     */
    public static HttpResult sendHttpPost(String url){
        HttpPost httpPost = new HttpPost(url);
        return sendHttpPost(httpPost);
    }
    /**
     * 发送 post 请求
     * @param url 地址
     * @param params 参数(格式:key1=value1&key2=value2)
     * @return
     */
    public static HttpResult sendHttpPost(String url,String params){
        HttpPost httpPost = new HttpPost(url);
        try{
            StringEntity stringEntity = new StringEntity(params,"UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("{}-{}", "网络超时", e.getMessage());
        }
        return sendHttpPost(httpPost);
    }
    /**
     * post 请求 map 参数
     * @param url
     * @param maps
     * @return
     */
    public static HttpResult sendHttpPost(String url,Map<String,String> maps){
        HttpPost httpPost = new HttpPost(url);
        try{
            List<NameValuePair>  nameValuePairs = new ArrayList<NameValuePair>();
            if(maps!=null){
                for (String key  : maps.keySet()) {
                    nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                return sendHttpPost(httpPost);
            }else{
                return sendHttpPost(httpPost);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.info("{}-{}", "网络超时", e.getMessage());
        }
        return null;
    }
    /**
     * 发送 post请求（带文件）
     * @param url
     * @param maps
     * @param files  附件
     * @return
     */
    public HttpResult sendHttpPost(String url,Map<String,String> maps,List<File> files){
        HttpPost httpPost = new HttpPost(url);
        try{
            MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
            if(maps!=null){
                for (String key  : maps.keySet()) {
                    meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
                }
                for (File file : files) {
                    FileBody fileBody = new FileBody(file);
                    meBuilder.addPart("files", fileBody);
                }
                HttpEntity httpEntity  = meBuilder.build();
                httpPost.setEntity(httpEntity);
                return sendHttpPost(httpPost);
            }else{
                return sendHttpPost(httpPost);
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.info("{}-{}", "网络超时", e.getMessage());
        }
        return null;
    }
    /**
     * 发送Post请求
     * @param httpPost
     * @return
     */

    private static HttpResult sendHttpPost(HttpPost httpPost) {
        CloseableHttpClient  httpClient = null;
        CloseableHttpResponse httpResponse = null;
        HttpEntity entity = null;
        String responseContent  = null;
        try{ // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            httpResponse = httpClient.execute(httpPost);
            entity = httpResponse.getEntity();
            responseContent  = EntityUtils.toString(entity, "UTF-8");
            logger.info("{}-{}",httpResponse.getStatusLine().getStatusCode(),responseContent);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("{}-{}", e.getMessage(), e);
            return new HttpResult(httpResponse.getStatusLine().getStatusCode(), responseContent);
        }finally {
            try {
                // 关闭连接,释放资源
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  new HttpResult(httpResponse.getStatusLine().getStatusCode(),responseContent);
    }

    /**
     * 发送 get请求
     *  @param httpUrl
     * */
    public HttpResult sendHttpGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);
        // 创建get请求
        return sendHttpGet(httpGet);
    }
    /**
     *  发送 get请求Https
     * @param httpUrl
     * */
    public HttpResult sendHttpsGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);
        // 创建get请求
        return sendHttpsGet(httpGet);
    }
    /**
     *发送Get请求
     * @param
     *@return
     *
     */
    private HttpResult sendHttpGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            logger.info("{}-{}",response.getStatusLine().getStatusCode(),responseContent);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("{}-{}", e.getMessage(), e);
            return new HttpResult(response.getStatusLine().getStatusCode(), responseContent);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HttpResult(response.getStatusLine().getStatusCode(),responseContent);
    }

    /**
     *
     * 发送Get请求Https
     *  @param
     *  @return
     * */
    private HttpResult sendHttpsGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            logger.info("{}-{}",response.getStatusLine().getStatusCode(),responseContent);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("{}-{}", e.getMessage(), e);
            return new HttpResult(response.getStatusLine().getStatusCode(), responseContent);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HttpResult(response.getStatusLine().getStatusCode(), responseContent);
    }
}


