package com.example.dropbox.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dropbox.R;
import com.example.dropbox.activity.DashboardActivity;
import com.example.dropbox.activity.DropBoxDataActivity;
import com.example.dropbox.activity.UserProfileActivity;

public class DashBoardFragment extends Fragment {
	
	
	
	private Button mButtonUserProfile;
	private Button mButtonDropBoxFolder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
		
		mButtonUserProfile = (Button) view.findViewById(R.id.userProfile);
		mButtonUserProfile.setOnClickListener(userProfileListener);
		
		mButtonDropBoxFolder = (Button) view.findViewById(R.id.dropboxFolder);
		mButtonDropBoxFolder.setOnClickListener(dropBoxFolderListener);
		return view;
	}
	
		
	private View.OnClickListener userProfileListener = new OnClickListener() {		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), UserProfileActivity.class);
			startActivity(intent);
		}
	};
	
	private View.OnClickListener dropBoxFolderListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), DropBoxDataActivity.class);
			startActivity(intent);
		}
	};

}
