package com.example.dropbox.activity;

import com.example.dropbox.R;
import com.example.dropbox.fragment.UserProfileFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class UserProfileActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle instance) {
		super.onCreate(instance);
		setContentView(R.layout.activity_user_profile);
		if(instance == null) {
			UserProfileFragment userProfile = new UserProfileFragment();
			getSupportFragmentManager().beginTransaction().add(R.id.container, userProfile,null).commit();
		}

	}

}
