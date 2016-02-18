package com.nowy.android.basiclib.model;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * APP数据模型
 * @author Nowy
 * 存放APP数据
 */
public class AppInfo {
	private String appLabel;
	private String pkgName;
	private Drawable appIcon;
	private Intent intent;
	public String getAppLabel() {
		return appLabel;
	}
	public void setAppLabel(String appLabel) {
		this.appLabel = appLabel;
	}
	public String getPkgName() {
		return pkgName;
	}
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	public Drawable getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	public Intent getIntent() {
		return intent;
	}
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	
}
