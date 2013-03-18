package com.example.dropbox.fragment;

import java.util.ArrayList;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.dropbox.client2.DropboxAPI.Entry;
import com.example.dropbox.R;
import com.example.dropbox.activity.DropBoxDataActivity;
import com.example.dropbox.adapter.DropBoxEntryAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.homeproject.dropboxapi.DropBoxClient;

public class DropboxFilesListFragment extends Fragment implements
		LoaderCallbacks<ArrayList<Entry>> {
	private DropBoxEntryAdapter adapter;
	private PullToRefreshListView entryListView;
	private DropboxFilesListFragment fragment;

	private String path = "/";
	private ArrayList<Entry> entries;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {

			String directoryPath = bundle.getString("dropboxPath");
			if (directoryPath != null) {
				Log.v(DropboxFilesListFragment.class.getName(),"directoryPath"+ directoryPath);
				path = directoryPath;
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dropboxfilelist,
				container, false);

		adapter = new DropBoxEntryAdapter(getActivity());
		entryListView = (PullToRefreshListView) view.findViewById(R.id.Entries);
		entryListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				getLoaderManager().restartLoader(0, null,
						DropboxFilesListFragment.this);

			}
		});
		entryListView.getRefreshableView().setAdapter(adapter);
		entryListView.getRefreshableView().setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long row) {
						Entry entry = adapter.getItem(position-1);
						if (entry != null && entry.isDir == true) {
							Intent intent = new Intent(getActivity(),
									DropBoxDataActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("dropboxPath", entry.path);
							intent.putExtra("directoryPathBundle", bundle);
							startActivity(intent);
						} 
					}

				});
		
		

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
				entryList = client.getMetaData(path);

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
		adapter.clear();
		
		adapter.addAll(entryList);
		entries = entryList;
		entryListView.onRefreshComplete();
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<Entry>> arg0) {

	}

}
