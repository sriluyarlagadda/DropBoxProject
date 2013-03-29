package com.example.dropbox.fragment;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.RESTUtility;
import com.example.dropbox.R;
import com.example.dropbox.adapter.PhotoEntryAdapter;
import com.homeproject.dropboxapi.DropBoxClient;

public class PhotoListFragment extends Fragment implements LoaderCallbacks<ArrayList<Entry>>{

	private static String PATH = "/Camera Uploads";
	private ExpandableListView entryListView;
	private PhotoEntryAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_photolist, container,
				false);
		 adapter = new PhotoEntryAdapter(getActivity());
		entryListView = (ExpandableListView) view.findViewById(R.id.Entries);	
		entryListView.setAdapter(adapter);
		return view;
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getLoaderManager().initLoader(0x3, null, this);
	} 
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}



	@Override
	public Loader<ArrayList<Entry>> onCreateLoader(int arg0, Bundle arg1) {
		AsyncTaskLoader<ArrayList<Entry>> loader = new AsyncTaskLoader<ArrayList<Entry>>(getActivity()) {
			
			private ArrayList<Entry> entries;
			@Override
			public ArrayList<Entry> loadInBackground() {

				Log.v("PhotoListFragment",
						"Started loading in background");
				DropBoxClient client = DropBoxClient.getInstance(getActivity());
				entries = client.getMetaData(PATH);

				return entries;
			}
			
			@Override
			protected void onStartLoading() {
				if (entries != null) {
					deliverResult(entries);
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
		ArrayList<String> groupItems = new ArrayList<String>();
		for(Entry entry:entryList) {
			groupItems.add(entry.modified);
		}
		adapter.setGroupItems(groupItems);
	}



	@Override
	public void onLoaderReset(Loader<ArrayList<Entry>> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
