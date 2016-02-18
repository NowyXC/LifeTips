package com.nowy.android.basiclib.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class DirUtil {
	private final static String DIR_TASK="task";//任务文件缓存
	private final static String DIR_ICON="icon";//图片缓存



	public static String getTaskDir(Context context) {
		String dir = getDir(context, DIR_TASK);
		return dir;
	}

	public static String getIconDir(Context context) {
		String dir = getDir(context, DIR_ICON);
		return dir;
	}

	private static String getDir(Context context, String path) {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(state)) {
			File root = Environment.getExternalStorageDirectory();

			File dir = new File(root, "Android/data/"
					+ context.getPackageName() + "/" + path);

			if (!dir.exists()) {
				dir.mkdirs();
			}
			return dir.getAbsolutePath();
		} else {
			File dir = new File(context.getFilesDir(), path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return dir.getAbsolutePath();
		}
	}

}