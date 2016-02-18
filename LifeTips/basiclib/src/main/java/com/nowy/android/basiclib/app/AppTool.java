package com.nowy.android.basiclib.app;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.nowy.android.basiclib.model.AppInfo;
import com.nowy.android.basiclib.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class AppTool {

	private static AppTool appTool = null;
	private Context ctx;

	private static PackageManager pm;

	private List<AppInfo> listMap = new ArrayList<AppInfo>();

	public static AppTool getInstance(Context ctx) {
		if (appTool == null)
			appTool = new AppTool(ctx);
		pm = ctx.getPackageManager();// 获得PackageManager对象
		return appTool;
	}

	public AppTool(Context ctx) {
		this.ctx = ctx;
	}

	/**
	 * 获取当前应用的包名
	 * 
	 * @return
	 */
	public String getPackageName() {
		return ctx.getPackageName();
	}

	/**
	 * 获取当前应用的版本号
	 * 
	 * @return
	 * @throws NameNotFoundException
	 */
	public String getVersionName() throws NameNotFoundException {
		PackageInfo packInfo = pm.getPackageInfo(getPackageName(), 0);// 0代表是获取版本信息
		return packInfo.versionName;
	}

	/**
	 * 获取当前系统的版本号
	 * 
	 * @return
	 */
	public String getSystemVersionName() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获得所有启动Activity的信息
	 * 
	 * @return
	 */
	public List< AppInfo> queryAllStartAppInfo() {
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// 通过查询，获得所有ResolveInfo对象.
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent,
				PackageManager.MATCH_DEFAULT_ONLY);
		// 调用系统排序 ， 根据name排序
		// 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序
		Collections.sort(resolveInfos,
				new ResolveInfo.DisplayNameComparator(pm));
		if (listMap != null) {
			listMap.clear();
			for (ResolveInfo reInfo : resolveInfos) {
				String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
				String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
				String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label
				Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标
				// 为应用程序的启动Activity 准备Intent
				Intent launchIntent = new Intent();
				launchIntent.setComponent(new ComponentName(pkgName,
						activityName));
				// 创建一个AppInfo对象，并赋值
				 AppInfo appInfo = new  AppInfo();
				appInfo.setAppLabel(appLabel);
				appInfo.setPkgName(pkgName);
				appInfo.setAppIcon(icon);
				appInfo.setIntent(launchIntent);
				listMap.add(appInfo); // 添加至列表中
			}
		}
		return listMap;
	}

	/**
	 * 查询所有已经安装的应用程序
	 * 
	 * @return
	 */
	public List<ApplicationInfo> queryAllInstallAppInfo() {
		// 查询所有已经安装的应用程序
		List<ApplicationInfo> listAppcations = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		Collections.sort(listAppcations,
				new ApplicationInfo.DisplayNameComparator(pm));// 排序
		return listAppcations;
	}

	/**
	 * 查询所有已经安装的应用程序
	 * 
	 * @return
	 */
	public List< AppInfo> queryAllAppInfo() {
		List< AppInfo> appInfos = new ArrayList< AppInfo>(); // 保存过滤查到的AppInfo
		for (ApplicationInfo app : queryAllInstallAppInfo()) {
			appInfos.add(getAppInfo(app));
		}
		return appInfos;
	}

	/**
	 * 查询所有已经启动的系统应用程序
	 * 
	 * @return
	 */
	public List< AppInfo> queryAllSystemAppInfo() {
		List< AppInfo> appInfos = new ArrayList< AppInfo>(); // 保存过滤查到的AppInfo
		for (ApplicationInfo app : queryAllInstallAppInfo()) {
			if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				appInfos.add(getAppInfo(app));
			}
		}
		return appInfos;
	}

	/**
	 * 查询所有第三方应用程序
	 * 
	 * @return
	 */
	public List< AppInfo> queryAllTheFirstAppInfo() {
		List< AppInfo> appInfos = new ArrayList< AppInfo>(); // 保存过滤查到的AppInfo
		for (ApplicationInfo app : queryAllInstallAppInfo()) {
			if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0)// 非系统程序
				appInfos.add(getAppInfo(app));
			else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0)// 本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了
				appInfos.add(getAppInfo(app));
		}
		return appInfos;
	}

	/**
	 * 安装在SDCard的应用程序
	 * 
	 * @return
	 */
	public List< AppInfo> queryAllSDCardAppInfo() {
		List< AppInfo> appInfos = new ArrayList< AppInfo>(); // 保存过滤查到的AppInfo
		for (ApplicationInfo app :  queryAllInstallAppInfo()) {
//			if ((app.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
				appInfos.add(getAppInfo(app));
//			}
		}
		return appInfos;
	}

	private  AppInfo getAppInfo(ApplicationInfo app) {
		 AppInfo appInfo = new  AppInfo();
		appInfo.setAppLabel((String) app.loadLabel(pm));
		appInfo.setAppIcon(app.loadIcon(pm));
		appInfo.setPkgName(app.packageName);
		return appInfo;
	}

	
	
	
	
	private  static TelephonyManager tManager;
	
	
	
	/**
	 * 获取当前客户端的整数版本号
	 * 
	 */
	public static int appVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (Exception e) {
			LogUtils.e(e.getMessage());
		}

		return 0;
	}
	
	public static String appName(Context context) {
		String name = null;
		try {
			name = context.getString(context.getApplicationInfo().labelRes);
		} catch (Exception e) {
			LogUtils.e(e.getMessage());
		}

		if (null == name) {
			name = "";
		}

		return name;
	}

	public static String appVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (Exception e) {
			LogUtils.e(e.getMessage());
		}

		return "";
	}
	
	/**
	 * 获取一个对象的类名
	 * @return String
	 */
	public static String getClassName(Object x) {
		if (null != x) {
			try {
				String className;
				if (x instanceof Class<?>) {
					className = ((Class<?>) x).getName();
				} else {
					className = x.getClass().getName();
				}

				String[] classNames = className.split("\\.");
				return classNames[classNames.length - 1];
			} catch (Exception e) {
				LogUtils.e(e.getMessage());
			}
		}

		return "";
	}
	
	
	/**
	 * 获取当前执行文件的路径
	 * 
	 */
	public static String getAppRunPath(Context context) {
		String path;

		try {
			path = context.getFilesDir().getAbsolutePath();
		} catch (Exception e) {
			path = "";
		}

		return path;
	}
	
	/**
	 * 获取当前应用 apk 的路径
	 * 
	 * @return String
	 */
	public static String getPackagePath(Context context) {
		String path;

		try {
			path = context.getPackageResourcePath();
		} catch (Exception e) {
			path = "";
		}

		return path;
	}
	
	

		
	//獲得設備信息
	public static String getDeviceId(Context context) throws Exception {
			if(tManager==null)
				tManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String deviceId=tManager.getDeviceId();
			
			return deviceId;
			
		}
		
		/**
		 * 获取SIM卡序列号
		 * 
		 * @return
		 */
	public static String getToken(Context context) {
			if(tManager==null)
				tManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			return tManager.getSimSerialNumber();
		}

		/*獲得系統版本*/
		
		public static String getClientOs() {
			return android.os.Build.ID;
		}
		
		/*獲得系統版本號*/
		public static String getClientOsVer() {
			return android.os.Build.VERSION.RELEASE;
		}
		
		//獲得系統語言包
		public static String getLanguage() {
			return Locale.getDefault().getLanguage();
		}
		
		public static String getCountry() {
			return Locale.getDefault().getCountry();
		}
		
		public static boolean isMobileNO(String mobiles){  
			String telRegex = "[1][3578]\\d{9}";
			if (TextUtils.isEmpty(mobiles)) return false;  
			 else return mobiles.matches(telRegex); 
			  
		} 
}
