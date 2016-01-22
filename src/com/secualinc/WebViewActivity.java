package com.secualinc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.secualinc.common.LocalDB;
import com.secualinc.common.SecualWebViewClient;

public class WebViewActivity extends Activity {
	private WebView webView;
	private String accessTokenParam;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_webview);
		
		// Set access token parameter
		LocalDB localDB = LocalDB.getInstance(this);
		accessTokenParam = "access-token=".concat(localDB.getToken());
		
		//Get webview 
		webView = (WebView) findViewById(R.id.webview);
		
		if (savedInstanceState != null){
			webView.restoreState(savedInstanceState);
		}
		
		startWebView("http://google.com");
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    webView.saveState(savedInstanceState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
	    
	    webView.restoreState(savedInstanceState);
	}

	private void startWebView(String url) {
        
        //Create new webview Client to show progress dialog
        //When opening a url or click on link
        webView.setWebViewClient(new com.secualinc.common.SecualWebViewClient(this, webView) {     
            //ProgressDialog progressDialog;
          
            //If you will not use this method url links are opeen in new brower not in webview
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {   
            	if (url.startsWith("http")) {
            		if (url.indexOf(accessTokenParam) > -1){
            			view.loadUrl(url);
            		} else if (url.indexOf("?") > -1){
	            		view.loadUrl(url + "&" + accessTokenParam);
	            	} else {
	            		view.loadUrl(url + "?" + accessTokenParam);
	            	}
                } else{
                	view.loadUrl(url);
                }
                return true;
            }
        
            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
//                if (progressDialog == null) {
//                    progressDialog = new ProgressDialog(WebViewActivity.this);
//                    progressDialog.setMessage("Loading...");
//                    progressDialog.show();
//                }
            }
            public void onPageFinished(WebView view, String url) {
//                try{
//                if (progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                    progressDialog = null;
//                }
//                }catch(Exception exception){
//                    exception.printStackTrace();
//                }
                view.clearCache(true);
            }
            
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            	try{
//	                if (progressDialog.isShowing()) {
//	                    progressDialog.dismiss();
//	                    progressDialog = null;
//	                }
//                }catch(Exception exception){
//                    exception.printStackTrace();
//                }
            }
        });
          
         // Javascript inabled on webview 
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }       
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        
        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */
        
        //webView.addJavascriptInterface(new JavascriptBridge(), "jsAndroiInterface");
        
        webView.setWebChromeClient(new WebChromeClient());
        
        //Load url in webview
        webView.loadUrl(url);
          
    }
	
	final class JavascriptBridge{
//		public String getData(String address, String dns){
//			
//			String rst = "Server address: " + address + "\n";
//			rst = rst + "DNS: " + dns + "";
//			
//			Toast.makeText(WebViewActivity.this, rst, Toast.LENGTH_LONG).show();
//			
//			webView.loadUrl(com.secualinc.common.Constants.WIFI_SETTING_URL);
//			return rst;
//		}
	}
	
	public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }
	
}
