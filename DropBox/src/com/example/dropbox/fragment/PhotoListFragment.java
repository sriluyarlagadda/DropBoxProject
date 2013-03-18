package com.example.dropbox.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dropbox.client2.DropboxAPI.Entry;
import com.example.dropbox.R;
import com.homeproject.dropboxapi.DropBoxClient;

public class PhotoListFragment extends Fragment implements LoaderCallbacks<ArrayList<Entry>>{

	private static String PATH = "/Camera Uploads";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_photolist, container,
				false);
		
		return view;
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(0x2, null, this);
	} 
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	

	@Override
	public Loader<ArrayList<Entry>> onCreateLoader(int arg0, Bundle arg1) {
		Log.v("DropboxFilesListFragment", "creating loader");

		AsyncTaskLoader<ArrayList<Entry>> loader = new AsyncTaskLoader<ArrayList<Entry>>(
				getActivity()) {
			private ArrayList<Entry> entryList;

			@Override
			public ArrayList<Entry> loadInBackground() {
				Log.v("DropboxFilesListFragment",
						"Started loading in background");
				DropBoxClient client = DropBoxClient.getInstance(getActivity());
				entryList = client.getMetaData(PATH);

				return entryList;
			}

			@Override
			protected void onStartLoading() {
				if (entryList != null) {
					deliverResult(entryList);
				} else {
					forceLoad();
				}
			}

		};
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Entry>> loader,
			ArrayList<Entry> entryList) {
/*		adapter.clear();
		
		adapter.addAll(entryList);
		entries = entryList;
		entryListView.onRefreshComplete();*/
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Entry>> arg0) {

	}



}
