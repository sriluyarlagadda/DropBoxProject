package com.example.dropbox.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.dropbox.R;
import com.example.dropbox.fragment.DropboxFilesListFragment;

public class DropBoxDataActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_dropboxdata);
		overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
		Intent intent = this.getIntent();
		
		if(savedInstance == null) {
			DropboxFilesListFragment fileListFragment = new DropboxFilesListFragment();
			if(intent!=null ) {
				fileListFragment.setArguments(intent.getBundleExtra("directoryPathBundle"));
			}
			
			getSupportFragmentManager().beginTransaction().add(R.id.fileList,fileListFragment, null).commit();
		}
		
	}

}
