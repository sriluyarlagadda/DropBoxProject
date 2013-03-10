package com.example.dropbox.activity;

import com.example.dropbox.R;
import com.example.dropbox.fragment.DashBoardFragment;
import com.example.service.AccountInfoService;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class DashboardActivity extends BaseActivity {
	
	private Fragment fragment;
	@Override
	protected void onCreate(Bundle instance) {
		super.onCreate(instance);
		setContentView(R.layout.activity_dashboard);
		if(instance == null) {

			fragment = new DashBoardFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.dashboard_container,fragment, null).addToBackStack(null).commit();
		}		
	}


}
