package com.example.service;

import android.app.IntentService;
import android.content.Intent;

public class AuthenticationService extends IntentService {
	
	private static final String TAG = AuthenticationService.class.getName();
	public AuthenticationService(String name) {
		super(name);	
	}

	@Override
	protected void onHandleIntent(Intent intent) {
			
	}

}
