package com.example.dropbox.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dropbox.R;
import com.example.dropbox.constant.Constant;
import com.example.dropbox.pref.PreferencesStore;
import com.example.dropbox.utils.DataUtils;

public class UserProfileFragment extends Fragment {
	
	private TextView userName;
	private TextView quota;
	private TextView normalquota;
	
	private PreferencesStore store;
	private Activity mActivity;
	
	public UserProfileFragment() {
		store = PreferencesStore.getInstance();
		mActivity = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		
		View view = inflater.inflate(R.layout.fragment_userprofile, container, false);
		userName = (TextView) view.findViewById(R.id.userName);
		quota = (TextView) view.findViewById(R.id.quotaValue);
		normalquota = (TextView) view.findViewById(R.id.normalquotaValue);
		String name = store.getAccountName(getActivity());
		String userQuota = store.getAccountQuota(getActivity());
		String normalQuota = store.getNormalQuota(getActivity());
		userName.setText(name);
		
		DataUtils dataUtils = new DataUtils();
		quota.setText(dataUtils.convertBytesToMegaBytes(userQuota)+ "MB");
		normalquota.setText(dataUtils.convertBytesToMegaBytes(normalQuota)+"MB");
		return view;
	}
	
}
