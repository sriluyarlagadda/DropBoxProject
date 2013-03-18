package com.example.dropbox.adapter;

import java.util.zip.Inflater;

import com.example.dropbox.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class PhotoEntryAdapter extends ExpandableListView {

	private Context mContext;
	public PhotoEntryAdapter(Context mContext) {
		super(mContext);
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.id.photoentryheading, parent);
		//if entry Exist return view else null
		Entry entry = getItem(groupPosition);
		return parent;
	}

}
