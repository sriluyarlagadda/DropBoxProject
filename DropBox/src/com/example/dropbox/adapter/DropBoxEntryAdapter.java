package com.example.dropbox.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI.Entry;
import com.example.dropbox.R;
import com.example.dropbox.utils.BitMapUtils;
import com.example.dropbox.utils.DataUtils;

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
		View listEntry = (View) view.findViewById(R.id.listEntry);
		if(entry.isDir == false) {
			EntryFliginListener flingListener = new EntryFliginListener(listEntry);
			final GestureDetector detector = new GestureDetector(flingListener);
			listEntry.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					detector.onTouchEvent(event);
					return true;
				}
			});
		}
		
		
		
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
	
	
	class EntryFliginListener extends GestureDetector.SimpleOnGestureListener {
		private View view;
		public EntryFliginListener(View entryDisplay) {
			view = entryDisplay;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
	        int dx = (int) (e2.getX() - e1.getX());

			if(dx > 0) {
				View hiddenView = view.findViewById(R.id.hiddenView);
				hiddenView.setVisibility(View.VISIBLE);
				ObjectAnimator animeX = ObjectAnimator.ofFloat(view.findViewById(R.id.entryDisplay), "x", (float)view.getWidth());
				animeX.start();
				return true;

			} else if(dx < 0){
				View hiddenView = view.findViewById(R.id.hiddenView);
				ObjectAnimator animeX = ObjectAnimator.ofFloat(view.findViewById(R.id.entryDisplay), "x", 0);

				animeX.start();
			}
			return false;
		}
	}
	

}
