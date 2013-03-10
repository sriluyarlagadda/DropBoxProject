package com.example.dropbox.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.LruCache;

import com.example.dropbox.utils.LoginUtils;


public class BaseActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		LoginUtils loginUtils = LoginUtils.getInstance(this);
	
		
		
		if(!loginUtils.finishedAuthentication()) {
			gotoMainActivity();
		} else {
			loginUtils.loadSession();
		}
		
	}
	
	private void gotoMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	

}
