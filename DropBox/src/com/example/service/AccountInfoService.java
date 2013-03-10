package com.example.service;

import com.dropbox.client2.DropboxAPI.Account;
import com.dropbox.client2.session.AccessTokenPair;
import com.example.dropbox.pref.PreferencesStore;
import com.homeproject.dropboxapi.DropBoxClient;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AccountInfoService extends IntentService {
	private static final String TAG = "AccountInfoService";
	private DropBoxClient mDropBoxClient = null;
	public AccountInfoService() {
		super("getAccountInfo");
	}

	@Override
	protected void onHandleIntent(Intent intent){
		
		mDropBoxClient = DropBoxClient.getInstance(this);
		PreferencesStore store = PreferencesStore.getInstance();
		AccessTokenPair tokenPair = store.getStoredKeys(this);
		mDropBoxClient.handleAuthentication(tokenPair);
    	Account account = mDropBoxClient.getAccountInfo();
    	if(account!=null) {
    		store.storeDropBoxAccount(account,getApplicationContext());
    	}
    	else {
    		Log.v(TAG,"account name is empty");
    	}
    	
	}
	



}

