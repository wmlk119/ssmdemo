package com.archer.ssm.utils.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    public static String accept = "*/*";
    public static String acceptEncoding = "gzip, deflate, sdch";
    public static String acceptLanguage = "zh-CN,zh;q=0.8,en;q=0.6";
    public static String connection = "keep-alive";
    public static String contentType = "application/x-www-form-urlencoded";
    public static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2896.3 Safari/537.36";
    public static int time_out = 30000;// 超时时间
    public static boolean isGetSetCookie = false;// 是否获取set-cookie
    public static String cookie = "";// set-cookie
    public static String proxyIP = "";// 代理ip
    public static int port = 0;// 端口号
    public static String proxyUsername = "";// 代理账号
    public static String proxyPassword = "";// 代理密码

    protected static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * Get
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        // 关闭HttpClient系统日志;
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");

        HttpResponse response = null;
        HttpEntity httpEntity = null;
        String content = null;
        try {
            CloseableHttpClient httpClient = HttpPoolManager.getHttpClient(); //http连接池
            HttpGet get = new HttpGet(url);
            // 请求头
            get.addHeader("Accept", accept);
            get.addHeader("Accept-Encoding", acceptEncoding);
            get.addHeader("Accept-Language", acceptLanguage);
            get.addHeader("Connection", connection);
            get.addHeader("Content-Type", contentType);
            get.addHeader("User-Agent", userAgent);
            get.addHeader("cookie", cookie);
            // 超时时间
            get.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, time_out);// 设置连接超时时间
            get.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, time_out);// 设置数据传输超时时间
            // 设置代理
            if(!StringUtils.isEmpty(proxyIP)) {
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(new AuthScope(proxyIP, port), new UsernamePasswordCredentials(proxyUsername, proxyPassword));
                httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
                HttpHost proxy = new HttpHost(proxyIP, port);
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                get.setConfig(config);
            }
            // 执行请求
            response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 判断gzip,解压缩
                if (null != response.getLastHeader("Content-Encoding") && (response.getLastHeader("Content-Encoding").toString()).indexOf("gzip") >= 0) {
                    response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                }
                content = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
                if(isGetSetCookie){
                    Header[] cookieHeader = response.getHeaders("Set-Cookie");
                    if(null != cookieHeader && cookieHeader.length > 0){
                        cookie = cookieHeader[0].getValue();
                        isGetSetCookie = false;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("HttpClientUtil.get异常", e);
        } finally {
            if (null != httpEntity) {
                try {
                    httpEntity.consumeContent();
                } catch (IOException e) {
                    logger.error("HttpClientUtil.get异常", e);
                }
            }
        }
        return content;
    }

    /**
     * Post
     *
     * @param url
     * @param maps	参数组
     * @return
     */
    public static String post(String url, Map<String, String> maps) {
        // 关闭HttpClient系统日志;
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> map : maps.entrySet()) {
            nvps.add(new BasicNameValuePair(map.getKey(), map.getValue()));
        }
        String content = null;
        HttpResponse response = null;
        HttpEntity httpEntity = null;
        try {
            CloseableHttpClient httpClient = HttpPoolManager.getHttpClient(); //http连接池
            HttpPost post = new HttpPost(url);
            // 请求头
            post.addHeader("Accept", accept);
            post.addHeader("Accept-Encoding", acceptEncoding);
            post.addHeader("Accept-Language", acceptLanguage);
            post.addHeader("Connection", connection);
            post.addHeader("Content-Type", contentType);
            post.addHeader("User-Agent", userAgent);
            post.addHeader("cookie", cookie);
            // 超时时间
            post.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, time_out);// 设置连接超时时间
            post.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, time_out);// 设置数据传输超时时间
            // 设置代理
            if(!StringUtils.isEmpty(proxyIP)) {
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(new AuthScope(proxyIP, port), new UsernamePasswordCredentials(proxyUsername, proxyPassword));
                httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
                HttpHost proxy = new HttpHost(proxyIP, port);
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                post.setConfig(config);
            }
            // 执行请求
            post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 判断gzip,解压缩
                if (null != response.getLastHeader("Content-Encoding")	&& (response.getLastHeader("Content-Encoding").toString()).indexOf("gzip") >= 0) {
                    response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                }
                httpEntity = response.getEntity();
                content = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                if(isGetSetCookie){
                    Header[] cookieHeader = response.getHeaders("Set-Cookie");
                    if(null != cookieHeader && cookieHeader.length > 0){
                        cookie = cookieHeader[0].getValue();
                        isGetSetCookie = false;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("HttpClientUtil.post异常", e);
        } finally {
            if (null != httpEntity) {
                try {
                    httpEntity.consumeContent();
                } catch (IOException e) {
                    logger.error("HttpClientUtil.post异常", e);
                }
            }
        }
        return content;
    }

    /**
     * post流
     * @param url
     * @param data
     *            (以流的方式发送请求参数)
     * @return
     */
    public static String postStream(String url, String data) {
        // 关闭HttpClient系统日志;
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");

        String content = null;
        HttpResponse response = null;
        HttpEntity httpEntity = null;
        try {
            CloseableHttpClient httpClient = HttpPoolManager.getHttpClient(); //http连接池
            HttpPost post = new HttpPost(url);
            // 请求头
            post.addHeader("Accept", accept);
            post.addHeader("Accept-Encoding", acceptEncoding);
            post.addHeader("Accept-Language", acceptLanguage);
            post.addHeader("Connection", connection);
            post.addHeader("Content-Type", contentType);
            post.addHeader("User-Agent", userAgent);
            post.addHeader("cookie", cookie);
            // 超时时间
            post.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, time_out);// 设置连接超时时间
            post.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, time_out);// 设置数据传输超时时间
            // 设置代理
            if(!StringUtils.isEmpty(proxyIP)) {
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(new AuthScope(proxyIP, port), new UsernamePasswordCredentials(proxyUsername, proxyPassword));
                httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
                HttpHost proxy = new HttpHost(proxyIP, port);
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                post.setConfig(config);
            }
            // 执行请求
            StringEntity entity = new StringEntity(data, "UTF-8");
            post.setEntity(entity);
            response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 判断gzip,解压缩
                if (null != response.getLastHeader("Content-Encoding")	&& (response.getLastHeader("Content-Encoding").toString()).indexOf("gzip") >= 0) {
                    response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                }
                httpEntity = response.getEntity();
                content = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                if(isGetSetCookie){
                    Header[] cookieHeader = response.getHeaders("Set-Cookie");
                    if(null != cookieHeader && cookieHeader.length > 0){
                        cookie = cookieHeader[0].getValue();
                        isGetSetCookie = false;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("HttpClientUtil.postStream异常", e);
        } finally {
            if (null != httpEntity) {
                try {
                    httpEntity.consumeContent();
                } catch (IOException e) {
                    logger.error("HttpClientUtil.postStream异常", e);
                }
            }
        }
        return content;
    }


    /**
     * Restful API Delete
     * @param url
     * @return
     */
    public static String delete(String url){
        // 关闭HttpClient系统日志;
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");

        HttpResponse response = null;
        HttpEntity httpEntity = null;
        String content = null;
        try {
            CloseableHttpClient httpClient = HttpPoolManager.getHttpClient(); //http连接池
            HttpDelete delete = new HttpDelete(url);
            // 请求头
            delete.addHeader("Accept", accept);
            delete.addHeader("Accept-Encoding", acceptEncoding);
            delete.addHeader("Accept-Language", acceptLanguage);
            delete.addHeader("Connection", connection);
            delete.addHeader("Content-Type", contentType);
            delete.addHeader("User-Agent", userAgent);
            delete.addHeader("cookie", cookie);
            // 超时时间
            delete.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, time_out);// 设置连接超时时间
            delete.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, time_out);// 设置数据传输超时时间
            // 设置代理
            if(!StringUtils.isEmpty(proxyIP)) {
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(new AuthScope(proxyIP, port), new UsernamePasswordCredentials(proxyUsername, proxyPassword));
                httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
                HttpHost proxy = new HttpHost(proxyIP, port);
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                delete.setConfig(config);
            }
            // 执行请求
            response = httpClient.execute(delete);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 判断gzip,解压缩
                if (null != response.getLastHeader("Content-Encoding") && (response.getLastHeader("Content-Encoding").toString()).indexOf("gzip") >= 0) {
                    response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                }
                content = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
                if(isGetSetCookie){
                    Header[] cookieHeader = response.getHeaders("Set-Cookie");
                    if(null != cookieHeader && cookieHeader.length > 0){
                        cookie = cookieHeader[0].getValue();
                        isGetSetCookie = false;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("HttpClientUtil.delete异常", e);
        } finally {
            if (null != httpEntity) {
                try {
                    httpEntity.consumeContent();
                } catch (IOException e) {
                    logger.error("HttpClientUtil.delete异常", e);
                }
            }
        }
        return content;
    }

    /**
     * Restful API PUT
     * @param url
     * @param data
     * @return
     */
    public static String putStream(String url, String data){
        // 关闭HttpClient系统日志;
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");

        String content = null;
        HttpResponse response = null;
        HttpEntity httpEntity = null;
        try {
            CloseableHttpClient httpClient = HttpPoolManager.getHttpClient(); //http连接池
            HttpPut put = new HttpPut(url);
            // 请求头
            put.addHeader("Accept", accept);
            put.addHeader("Accept-Encoding", acceptEncoding);
            put.addHeader("Accept-Language", acceptLanguage);
            put.addHeader("Connection", connection);
            put.addHeader("Content-Type", contentType);
            put.addHeader("User-Agent", userAgent);
            put.addHeader("cookie", cookie);
            // 超时时间
            put.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, time_out);// 设置连接超时时间
            put.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, time_out);// 设置数据传输超时时间
            // 设置代理
            if(!StringUtils.isEmpty(proxyIP)) {
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(new AuthScope(proxyIP, port), new UsernamePasswordCredentials(proxyUsername, proxyPassword));
                httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
                HttpHost proxy = new HttpHost(proxyIP, port);
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                put.setConfig(config);
            }
            // 执行请求
            StringEntity entity = new StringEntity(data, "UTF-8");
            put.setEntity(entity);

            response = httpClient.execute(put);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                // 判断gzip,解压缩
                if (null != response.getLastHeader("Content-Encoding")	&& (response.getLastHeader("Content-Encoding").toString()).indexOf("gzip") >= 0) {
                    response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                }
                httpEntity = response.getEntity();
                content = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                if(isGetSetCookie){
                    Header[] cookieHeader = response.getHeaders("Set-Cookie");
                    if(null != cookieHeader && cookieHeader.length > 0){
                        cookie = cookieHeader[0].getValue();
                        isGetSetCookie = false;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("HttpClientUtil.putStream异常", e);
        } finally {
            if (null != httpEntity) {
                try {
                    httpEntity.consumeContent();
                } catch (IOException e) {
                    logger.error("HttpClientUtil.putStream异常", e);
                }
            }
        }
        return content;
    }

    public static void main(String[] args) {
        String res = get("http://127.0.0.1:8080/api/dotest?itemName=hello%20world");
        System.out.println(res);
    }
}
