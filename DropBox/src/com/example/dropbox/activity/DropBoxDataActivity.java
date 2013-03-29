package com.example.dropbox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dropbox.R;
import com.example.dropbox.fragment.DropboxFilesListFragment;
import com.example.dropbox.fragment.PhotoListFragment;

public class DropBoxDataActivity extends BaseActivity {
	
	private ImageView photos;
	private ImageView dropbox;
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
		
		photos = (ImageView) findViewById(R.id.photosBottomBar);
		dropbox = (ImageView) findViewById(R.id.dropboxBottomBar);
		photos.setOnClickListener(photosListener);
		dropbox.setOnClickListener(dropboxListener);
	}
	private View.OnClickListener photosListener = new View.OnClickListener() {
		public void onClick(View v) {
			PhotoListFragment photoListFragment = new PhotoListFragment();
			photos.setBackgroundResource(R.color.gray);
			dropbox.setBackgroundResource(R.color.lightgray);
			getSupportFragmentManager().beginTransaction().replace(R.id.fileList,photoListFragment).commit();
		};
	};
	
	private View.OnClickListener dropboxListener = new View.OnClickListener() {
		public void onClick(View v) {
			DropboxFilesListFragment fileListFragment = new DropboxFilesListFragment();
			photos.setBackgroundResource(R.color.lightgray);			
			dropbox.setBackgroundResource(R.color.gray);
			getSupportFragmentManager().beginTransaction().replace(R.id.fileList,fileListFragment).commit();
		};
	};


}
