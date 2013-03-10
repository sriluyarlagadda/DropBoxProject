package com.example.dropbox.pref;

import android.R.bool;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.dropbox.client2.DropboxAPI.Account;
import com.dropbox.client2.session.AccessTokenPair;
import com.example.dropbox.constant.Constant;

public class PreferencesStore {
	private String TOKEN_STORE = "tokenStore";
	private static final String TOKEN_KEY = "key";
	private static final String TOKEN_SECRET = "secret";
	private static PreferencesStore store = null;
	private PreferencesStore() {
		
	}
	public void storeKeys(AccessTokenPair tokenPair, Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				TOKEN_STORE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		editor.putString(TOKEN_KEY, tokenPair.key);
		editor.putString(TOKEN_SECRET, tokenPair.secret);
		editor.commit();
	}
	
	public AccessTokenPair getStoredKeys(Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(TOKEN_STORE, 0);
		String key = preferences.getString(TOKEN_KEY, "ab");
		String secret = preferences.getString(TOKEN_SECRET, "ab");
		AccessTokenPair tokenPair = new AccessTokenPair(key, secret);
		return tokenPair;
	}
	
	public static PreferencesStore getInstance() {
		if(store == null) {
			store = new PreferencesStore();
		}
		
		return store;
	}
	
	public void storeDropBoxAccount(Account account, Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(TOKEN_STORE, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(Constant.PREFERENCE_ACCOUNT_NAME, account.displayName);
		editor.putLong(Constant.PREFERENCE_ACCOUNT_ID, account.uid);
		Log.v("Preferences Store", "quota"+account.quota);
		editor.putString(Constant.PREFERENCE_USERQUOTA, String.valueOf(account.quota));
		editor.putString(Constant.PREFERENCE_NORMALQUOTA, String.valueOf(account.quotaNormal));
		editor.commit();
	}
	
	public String getAccountName(Context mContext) {
		String value = "";
		SharedPreferences preferences = mContext.getSharedPreferences(TOKEN_STORE, Context.MODE_PRIVATE);
		value = preferences.getString(Constant.PREFERENCE_ACCOUNT_NAME, "No Name");
		return value;
	}
	public void setLoggedIn(Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				TOKEN_STORE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		editor.putBoolean(Constant.PREFERENCE_SETLOGGED_IN, true);
		editor.commit();
	}
	
	public boolean getLoggedIn(Context mContext) {
		SharedPreferences preferences = mContext.getSharedPreferences(TOKEN_STORE, Context.MODE_PRIVATE);
		boolean value = preferences.getBoolean(Constant.PREFERENCE_SETLOGGED_IN, false);
		return value;
	}
	public String getAccountQuota(Context mContext) {
		String value = "empty";
		SharedPreferences preferences = mContext.getSharedPreferences(TOKEN_STORE, Context.MODE_PRIVATE);
		value = preferences.getString(Constant.PREFERENCE_USERQUOTA, "0.0");
		Log.v("Preferences Store", "account quota returned value"+value);

		return value;
	}
	public String getNormalQuota(Context mContext) {
		String value = "empty";
		SharedPreferences preferences = mContext.getSharedPreferences(TOKEN_STORE, Context.MODE_PRIVATE);
		value = preferences.getString(Constant.PREFERENCE_NORMALQUOTA, "0.0");
		Log.v("Preferences Store", "normal quota returned value"+value);

		return value;
	}

}
