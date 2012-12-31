package com.example.dropbox;

import com.homeproject.dropboxapi.DropBoxClient;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getName();
	private Button enterDropBoxButton = null;
	
	private DropBoxClient dropBoxClient = null;
	
	private int authenticationMessageDuration =  Toast.LENGTH_LONG;	

	
	private OnClickListener enterDropBoxClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			dropBoxClient.startAuthentication();			
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		enterDropBoxButton = (Button)findViewById(R.id.dropBoxButton);
		enterDropBoxButton.setOnClickListener(enterDropBoxClickListener);		
		dropBoxClient = new DropBoxClient(this);
	}
	
	
	protected void onResume() {
		super.onResume();
		
		Toast toast = null;

		if(dropBoxClient.checkIfAuthenticationSuccessful()) {
			Log.v(TAG,"Authentication Succesful.Showing toast now");
			toast = Toast.makeText(getApplicationContext(), R.string.authentication_successful, authenticationMessageDuration);
			dropBoxClient.finishAuthentication();
			toast.show();

		}
		else {
			Log.v(TAG,"Authentication Failure.");
			toast = Toast.makeText(getApplicationContext(), R.string.authenticaton_failed, authenticationMessageDuration);
		}

	}
	
	
	
}
