package com.nowy.android.basiclib.base;

import android.app.IntentService;

import com.nowy.android.basiclib.app.AppServices;


public abstract class BaseIntentService extends IntentService {

	public BaseIntentService(String name) {
		super(name);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		AppServices.addService(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		AppServices.removeService(this);
	}
}
