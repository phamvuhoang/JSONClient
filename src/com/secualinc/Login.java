package com.secualinc;

import org.json.JSONObject;
import com.loopj.android.http.RequestParams;
import com.secualinc.common.HttpClient;
import com.secualinc.common.HttpClientResponse;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Login extends Activity {
	
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
    	params.add("email", email);
    	params.add("password", password);
    	LoginResponse response = new LoginResponse();
    	
    	HttpClient.post("/api/v1/auth/sign_in", params, response);
    	
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
            
            final AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Login authentication success");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
		            alertDialog.dismiss();
		            }
	            });
            alertDialog.show();            
        }

        @Override
        public void onFailureHandler(int statusCode, JSONObject response) {
        	String str = "";
        	if (response != null){
        		str = response.toString();
        	}
            Log.d("LoginResponse", "onFailureResult statusCode=" + statusCode + "/response=" + str);
            
            final AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
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
