package com.homeproject.dropboxapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Account;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;


//client to interact with the dropbox api and return all the necessary data

public class DropBoxClient {	
	
	//default configuration
	private String APP_KEY = "pru9yxt4q13fyvf";
	private String APP_SECRET = "edvh3mqtalvtolo";
	
	final static private AccessType ACCESS_TYPE = AccessType.DROPBOX;

	private AppKeyPair keyPair;
	private AndroidAuthSession session = null;
	private DropboxAPI<AndroidAuthSession> mDropBoxApi;
	private AccessTokenPair tokenPair;
	private Context mContext;
	
	private static DropBoxClient instance;
	
	
	private  DropBoxClient(Context context) {		
		keyPair = new AppKeyPair(APP_KEY, APP_SECRET);
		session = new AndroidAuthSession(keyPair, ACCESS_TYPE);
		mDropBoxApi = new DropboxAPI<AndroidAuthSession>(session);
		this.mContext = context;
		
	}
	
	public static DropBoxClient getInstance(Context mContext) {
		if(instance == null) {
			instance = new DropBoxClient(mContext);
		}
		
		return instance;
	}

	
	
	public void startAuthentication() {
		mDropBoxApi.getSession().startAuthentication(mContext);
	}
	
	
	public boolean checkIfAuthenticationSuccessful() {
		boolean isSuccessful = false;
		if(mDropBoxApi.getSession().authenticationSuccessful()) {
			isSuccessful = true;
		}
		return isSuccessful;
	}
	
	public AccessTokenPair finishAuthentication() {
		try {
			mDropBoxApi.getSession().finishAuthentication();
			tokenPair = mDropBoxApi.getSession().getAccessTokenPair();
			return tokenPair;
		} catch(IllegalStateException e) {
            Log.i("DbAuthLog", "Error authenticating", e);
            return null;
        }
	}

	
	public Account getAccountInfo() {
		Account account = null;
		try {
			account = mDropBoxApi.accountInfo();
		} catch(Exception e) {
			System.out.println("Exception at getaccountinfo"+e.toString());
		}
		return account;
	}
	
	public void handleAuthentication(AccessTokenPair tokenPair) {
		this.mDropBoxApi.getSession().setAccessTokenPair(tokenPair);
	}
	
	public ArrayList<Entry> getMetaData(String path) {
		if(path == null) {
			path = "/";
		}
		ArrayList<Entry> entryList = null;
		try {
			Entry entry =  mDropBoxApi.metadata(path, 0, null, true, null);
			if(entry.isDir) {
				entryList = (ArrayList<DropboxAPI.Entry>)entry.contents;
			} else {
				entryList.add(entry);
			}
			return entryList;

		} catch (DropboxException e) {
			Log.v("DropboxClient", "exception in dropbox client" + e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public byte[] downloadFile(String path) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			DropboxFileInfo info = mDropBoxApi.getFile(path, null, outputStream, null);
		} catch(Exception e) {
			System.out.println("Something went wrong"+ e.toString());
		} finally {
			if(outputStream != null) {
				try {
					byte[] byteArray = outputStream.toByteArray();
					outputStream.close();
					return byteArray;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public byte[] downloadThumbnail(String path) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			DropboxFileInfo info = mDropBoxApi.getThumbnail(path, outputStream, DropboxAPI.ThumbSize.ICON_128x128, DropboxAPI.ThumbFormat.JPEG, null);
		} catch(Exception e) {
			System.out.println("Something went wrong"+ e.toString());
		} finally {
			if(outputStream != null) {
				try {
					byte[] byteArray = outputStream.toByteArray();
					outputStream.close();
					return byteArray;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	

}
