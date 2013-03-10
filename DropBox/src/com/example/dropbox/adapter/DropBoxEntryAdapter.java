package com.example.dropbox.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI.Entry;
import com.example.dropbox.R;
import com.example.dropbox.utils.BitMapUtils;
import com.example.dropbox.utils.DataUtils;
import com.homeproject.dropboxapi.DropBoxClient;

public class DropBoxEntryAdapter extends ArrayAdapter<Entry> {

	private Context mContext;
	private BitMapUtils bitmapUtils;

	public DropBoxEntryAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_2);
		 bitmapUtils = new BitMapUtils();

		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view;
		
		if (convertView == null) {
			view = inflater.inflate(R.layout.entry, parent, false);
		} else {
			view = convertView;
		}
		
		DataUtils dataUtils = new DataUtils();
		
		Entry entry = getItem(position);
		TextView entryText = (TextView) view.findViewById(R.id.dropBoxEntry);
		entryText.setText(dataUtils.truncateText(entry.fileName(),30,18));
		
		ImageView entryIcon = (ImageView) view.findViewById(R.id.entryIcon);
		if(entry.thumbExists) {
			//load the image
			bitmapUtils.loadImage(entry.path, entryIcon, 120, 120, mContext);
		} else {
			entryIcon.setImageResource(mContext.getResources().getIdentifier("com.example.dropbox:drawable/"+entry.icon+"48", null, null));	
		}
		
		TextView entryMetaData;
		entryMetaData= (TextView) view.findViewById(R.id.entryMetaData);
		ImageView goIntoIcon = (ImageView) view.findViewById(R.id.goIntoIcon);
		if(entry.isDir == false) {
			String metaData = entry.size+", "+entry.modified;
			Log.v("DropBoxEntryAdapter", "Meta data"+ metaData);
			entryMetaData.setText(metaData);
			entryMetaData.setVisibility(view.VISIBLE);
			goIntoIcon.setVisibility(View.INVISIBLE);

		} else {
			entryMetaData.setVisibility(view.INVISIBLE);
			goIntoIcon.setVisibility(View.VISIBLE);
		}
		
		return view;
	}
	

}
