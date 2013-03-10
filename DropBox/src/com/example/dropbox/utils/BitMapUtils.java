package com.example.dropbox.utils;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.example.dropbox.R;
import com.homeproject.dropboxapi.DropBoxClient;

public class BitMapUtils {

	private int reqWidth = 48;
	private int reqHeight = 48;
	private LruCache<String, Bitmap> mMemoryCache;
	
	public BitMapUtils() {
	final int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
		
		final int cacheSize = maxMemory/8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
			
				return bitmap.getByteCount()/1024;
			}
		};
	}

	public void loadImage(String imagePath, ImageView imageView, int reqWidth, int reqHeight, Context mContext) {
		this.reqHeight = reqHeight;
		this.reqWidth = reqWidth;
		String[] params = {imagePath, String.valueOf(reqWidth), String.valueOf(reqHeight)};
		DropBoxClient client = DropBoxClient.getInstance(mContext);
		Bitmap bitmap = getBitmapFromMemCache(imagePath);
		if(bitmap != null ) {
			imageView.setImageBitmap(bitmap);
		}
		else {
			if(cancelPotentialWork(imagePath,imageView)) {

				BitMapWorkerTask task = new BitMapWorkerTask(imageView, client);
				AsyncDrawable asyncDrawable =
		                new AsyncDrawable(mContext.getResources(),BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_no_image_agent), task);
				imageView.setImageDrawable(asyncDrawable);
				task.execute(params);	
			}
		}

	}

	private boolean cancelPotentialWork(String path, ImageView imageView) {
		BitMapWorkerTask task = getBitMapDownloaderTask(imageView);
		if(task != null) {
			String bitmapPath = task.imagePath;
			if(bitmapPath == null || (!bitmapPath.equals(path)) ) {
				task.cancel(true);
			} else {
				return false;
			}
		}
		return true;
	}

	private BitMapWorkerTask getBitMapDownloaderTask(ImageView imageView) {
		Drawable drawable = imageView.getDrawable();
		if(drawable instanceof AsyncDrawable) {
			final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
	           return asyncDrawable.getBitmapWorkerTask();
		}
		return null;
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if(getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key,bitmap);
		}
	}

	private Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}
	

class BitMapWorkerTask extends AsyncTask<String, Void, Bitmap> {
	private WeakReference<ImageView> imageViewReference;
	private DropBoxClient client;
	String imagePath;
	public BitMapWorkerTask(ImageView imageView, DropBoxClient client) {
		imageViewReference = new WeakReference<ImageView>(imageView);
		this.client = client;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		String path = params[0];

		Log.v(BitMapUtils.class.getName(),"reached backgroundsssss");

		int reqWidth = Integer.parseInt(params[1]);
		int reqHeight = Integer.parseInt(params[2]);
		imagePath = path;
		byte[] byteImageArray = client.downloadThumbnail(path);

		Bitmap bitmap =  decodeSampledBitMapFromResource(byteImageArray, reqWidth, reqHeight);
		addBitmapToMemoryCache(path, bitmap);
		return bitmap;
	}
	
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if(imageViewReference != null && bitmap != null) {
			final ImageView imageView = imageViewReference.get();
/*			BitMapWorkerTask task = getBitMapWorkerTask(imageView);*/
			if(imageView != null) {
				Log.v(BitMapUtils.class.getName(),"reached here");
				
				imageView.setImageBitmap(bitmap);
			}
		}
	}

	

	public  int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		int height = options.outHeight;
		int width = options.outWidth;
		int sampleSize = 1;
		if(height > reqHeight || width > reqWidth) {
			
			// Calculate ratios of height and width to requested height and width
	        final int heightRatio = Math.round((float) height / (float) reqHeight);
	        final int widthRatio = Math.round((float) width / (float) reqWidth);
	        	
	        sampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return sampleSize;
		
	}
	
	public Bitmap decodeSampledBitMapFromResource(byte[] byteImageArray, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(byteImageArray, 0, byteImageArray.length, options);
		
		options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(byteImageArray, 0, byteImageArray.length,options);
		
	}
	
}


class AsyncDrawable extends BitmapDrawable {
	private final WeakReference<BitMapWorkerTask> bitmapWorkerTaskReference;

	public AsyncDrawable(Resources res, Bitmap bitmap, BitMapWorkerTask task) {
		super(res,bitmap);
		bitmapWorkerTaskReference = new WeakReference<BitMapWorkerTask>(
				task);
	}

	public BitMapWorkerTask getBitmapWorkerTask() {
		return bitmapWorkerTaskReference.get();
	}
}


}


