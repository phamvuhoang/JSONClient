package com.secualinc.common;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpClient {
	private static final String BASE_URL = "http://zxc.cz:5000";
    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * Send POST request
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.setTimeout(50000);
        client.post(getAbsoluteURL(url), addCommonParams(params), responseHandler);
    }
    
    /**
     * Send POST request with no parameter
     * @param url
     * @param responseHandler
     */
    public static void post(String url, JsonHttpResponseHandler responseHandler) {
        client.setTimeout(50000);
        client.post(url, responseHandler);
    }

    /**
     * Send GET request
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.setTimeout(50000);
        client.get(getAbsoluteURL(url), addCommonParams(params), responseHandler);
    }

    /**
     * 
     * @param relativeURL
     * @return
     */
    private static String getAbsoluteURL(String relativeURL ) {
        return BASE_URL + relativeURL;
    }

    /**
     * If there are some common parameters that needed for every requests
     * Add them here
     * @param params
     * @return
     */
    private static RequestParams addCommonParams(RequestParams params) {
        RequestParams allParams = params;

//        allParams.add("common_param", COMMON_PARAM);

        return allParams;
    }
}
