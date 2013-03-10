package com.example.dropbox.utils;

import com.dropbox.client2.session.AccessTokenPair;
import com.example.dropbox.activity.MainActivity;
import com.example.dropbox.pref.PreferencesStore;
import com.homeproject.dropboxapi.DropBoxClient;

import android.content.Context;

public class LoginUtils {
	
	private static LoginUtils instance = null;	
	private PreferencesStore store;
	
	private int value = 0 ;
	private DropBoxClient dropboxClient=null;
	private Context mContext;
	
	public  boolean finishedAuthentication() {
		store = PreferencesStore.getInstance();
		return store.getLoggedIn(mContext);
	}
	
	private LoginUtils(Context mContext) {
		this.mContext = mContext;
		dropboxClient = DropBoxClient.getInstance(mContext);
	}
	
	public static  LoginUtils getInstance(Context mContext) {
		if(instance == null) {
			instance = new LoginUtils(mContext);
		}
		
		return instance;
	}

	public void loadSession() {
		AccessTokenPair tokenPair = store.getStoredKeys(mContext); 
		dropboxClient.handleAuthentication(tokenPair);
	}

	public void beginAuthentication() {
		dropboxClient.startAuthentication();

	}

}
