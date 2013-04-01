package com.example.dropbox.adapter;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI.Entry;
import com.example.dropbox.R;
import com.example.dropbox.utils.BitMapUtils;
import com.homeproject.dropbox.models.PhotoDirectory;

public class PhotoEntryAdapter extends BaseExpandableListAdapter {
	
	private Context mContext;
	private ArrayList<String> groupItems;
	private PhotoDirectory directory;
	public PhotoEntryAdapter(Context context) {
		mContext = context;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(android.R.layout.simple_list_item_1, parent,false);
		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		textView.setText(String.valueOf(childPosition));
		return textView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 5;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		if(groupItems!=null) {
			return groupItems.size();	
		}
		return 0;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.v("PhotoEntryAdapter", "groupPosition"+ groupPosition);
		View view = inflater.inflate(android.R.layout.simple_list_item_1, parent,false);
		TextView textView = (TextView) view.findViewById(android.R.id.text1);
		String text = directory.getDate(groupPosition);
		textView.setText(text);
		textView.setText(groupItems.get(groupPosition));
		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public void setGroupItems(ArrayList<String> groupItems) {
		this.groupItems = groupItems;
		notifyDataSetChanged();
	}
	
	public void clear() {
		if(this.groupItems!=null) {
			this.groupItems.clear();	
		}
		
	}
	
	




}
