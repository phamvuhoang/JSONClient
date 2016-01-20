package com.secualinc.common;


import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public abstract class HttpClientResponse extends JsonHttpResponseHandler {
	private static final int HTTP_SUCCESS = 200;
	private static final int HTTP_UNKNOWN_ERROR = 999;
	private static final String COMMON_UNKNOWN_ERROR_JSON = "";

	private int statusCode;
    private Header[] headers;
    private JSONObject responseData;

    public abstract void onSuccessHandler(int statusCode, JSONObject response);
    public abstract void onFailureHandler(int statusCode, JSONObject response);

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.d("HttpClientResponse", "onSuccess response=" + response.toString());

        //this.statusCode = getStatusCode(response);
        this.headers = headers;
        this.responseData = response;

        if (HTTP_SUCCESS == this.statusCode) {
        	onSuccessHandler(this.statusCode, response);
        } else {
        	onFailureHandler(this.statusCode, response);
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String error, Throwable e) {
        Log.d("HttpClientResponse", "onFailure error=" + error);

        String jsonMsg = COMMON_UNKNOWN_ERROR_JSON;

        try {
            onFailureHandler(HTTP_UNKNOWN_ERROR, new JSONObject(jsonMsg));
        } catch (JSONException e1) {

        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
        Log.d("HttpClientResponse", "onFailure response=" + errorResponse.toString());

        this.statusCode = statusCode;
        this.headers = headers;
        this.responseData = errorResponse;

        onFailureHandler(statusCode, errorResponse);
    }

}
