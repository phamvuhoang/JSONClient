package com.secualinc;

import org.json.JSONObject;
import com.loopj.android.http.RequestParams;
import com.secualinc.common.Constants;
import com.secualinc.common.HttpClient;
import com.secualinc.common.HttpClientResponse;
import com.secualinc.common.LocalDB;

import cz.msebera.android.httpclient.Header;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	private EditText mEmail;
	private EditText mPassword;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        mEmail = (EditText)findViewById(R.id.email);
        mPassword = (EditText)findViewById(R.id.password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * 
     * @param v
     */
    public void loginClicked(View v) {
    	String email = mEmail.getText().toString().trim();
    	String password = mPassword.getText().toString().trim();
    	
    	RequestParams params = new RequestParams();
    	params.add(Constants.PARAM_EMAIL, email);
    	params.add(Constants.PARAM_PASSWORD, password);
    	LoginResponse response = new LoginResponse();
    	
    	HttpClient.post(Constants.API_AUTH, params, response);
    	
    }
    
    /**
     * Helper class for server response handler
     */
    private class LoginResponse extends HttpClientResponse {

        @Override
        public void onSuccessHandler(int statusCode, JSONObject response) {
        	String str = "";
        	if (response != null){
        		str = response.toString();
        	}
            Log.d("LoginResponse", "onSuccessResult statusCode=" + statusCode + "/response=" + str);
            
//            final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
//            alertDialog.setTitle("Info");
//            alertDialog.setMessage("Login authentication success");
//            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//	            public void onClick(DialogInterface dialog, int which) {
//		            alertDialog.dismiss();
//		            }
//	            });
//            alertDialog.show();
            
            int len = headers.length;
            for (int i = 0; i < len; i++) {
            	Header header = headers[i];
            	if (Constants.ACCESS_TOKEN.equals(header.getName())) {
            		LocalDB localDB = LocalDB.getInstance(LoginActivity.this);
            		localDB.saveToken(header.getValue());
            		break;
            	}
            }
            
            startActivity(new Intent(LoginActivity.this, WebViewActivity.class));
        }

        @Override
        public void onFailureHandler(int statusCode, JSONObject response) {
        	String str = "";
        	if (response != null){
        		str = response.toString();
        	}
            Log.d("LoginResponse", "onFailureResult statusCode=" + statusCode + "/response=" + str);
            
            final AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Login authentication failed");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
		            alertDialog.dismiss();
		            }
	            });
            alertDialog.show();            
        }
    }
    
}
