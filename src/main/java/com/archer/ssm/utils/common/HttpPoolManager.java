package com.archer.ssm.utils.common;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * HTTPclient连接池
 */
public class HttpPoolManager {
    public static PoolingHttpClientConnectionManager clientConnectionManager = null;

    private int maxTotal = 50;

    private int defaultMaxPerRoute = 25;

    private HttpPoolManager(int maxTotal, int defaultMaxPerRoute) {
        this.maxTotal = maxTotal;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        clientConnectionManager.setMaxTotal(maxTotal);
        clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }

    private HttpPoolManager() {
        clientConnectionManager.setMaxTotal(maxTotal);
        clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }

    private static HttpPoolManager poolManager = null;

    /*
     * 获取实例1
     */
    public synchronized static HttpPoolManager getInstance() {
        if (poolManager == null) {
            clientConnectionManager = new PoolingHttpClientConnectionManager();
            poolManager = new HttpPoolManager();
        }
        return poolManager;
    }

    /*
     * 获取实例1
     */
    public synchronized static HttpPoolManager getInstance(int maxTotal, int defaultMaxPerRoute) {
        if (poolManager == null) {
            poolManager = new HttpPoolManager(maxTotal, defaultMaxPerRoute);
        }
        return poolManager;
    }

    /*
     * 获取CloseableHttpClient
     */
    public static CloseableHttpClient getHttpClient() {
        if (clientConnectionManager == null) {
            clientConnectionManager = new PoolingHttpClientConnectionManager();
            getInstance();
        }
        return HttpClients.custom().setConnectionManager(clientConnectionManager).build();
    }
}
