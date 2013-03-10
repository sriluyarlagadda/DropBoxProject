package com.example.dropbox.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.dropbox.client2.session.AccessTokenPair;
import com.example.dropbox.R;
import com.example.dropbox.pref.PreferencesStore;
import com.example.dropbox.utils.LoginUtils;
import com.example.service.AccountInfoService;
import com.homeproject.dropboxapi.DropBoxClient;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getName();
	private Button enterDropBoxButton = null;
	
	private DropBoxClient dropBoxClient = null;
	private int authenticationMessageDuration =  Toast.LENGTH_LONG;	

	private PreferencesStore store;
	private AccessTokenPair tokenPair;
	private OnClickListener enterDropBoxClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			//load authentication details from store if don't exist start authentication else load session
			LoginUtils utils = LoginUtils.getInstance(MainActivity.this);
			if(!utils.finishedAuthentication()) {
				utils.beginAuthentication();
			} else {
				utils.loadSession();				
				gotoDashBoard();
			}
						
		}

	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		enterDropBoxButton = (Button)findViewById(R.id.dropBoxButton);
		enterDropBoxButton.setOnClickListener(enterDropBoxClickListener);		
		dropBoxClient = DropBoxClient.getInstance(this.getApplicationContext());
		store = PreferencesStore.getInstance();
	
	}
	
	
	protected void onResume() {
		super.onResume();
		
		Toast toast = null;

		if(dropBoxClient.checkIfAuthenticationSuccessful()) {
			Log.v(TAG,"Authentication Succesful.Showing toast now");
			toast = Toast.makeText(getApplicationContext(), R.string.authentication_successful, authenticationMessageDuration);
			tokenPair= dropBoxClient.finishAuthentication();
			if(tokenPair!=null) {
				PreferencesStore store = PreferencesStore.getInstance();
				store.storeKeys(tokenPair, this);
				store.setLoggedIn(this);
				gotoDashBoard();
				toast.show();
			}


		}
		else {
			Log.v(TAG,"Authentication Failure.");
			toast = Toast.makeText(getApplicationContext(), R.string.authenticaton_failed, authenticationMessageDuration);
		}

	}
	

	private void gotoDashBoard() {
		
		Intent serviceIntent = new Intent(this, AccountInfoService.class);
		startService(serviceIntent);
		Intent intent = new Intent(this, DashboardActivity.class);
		startActivity(intent);
	}
	
	
	
}
