package com.nowy.android.basiclib.app;


import android.app.Activity;

import com.nowy.android.basiclib.task.AsyncTaskManager;
import com.nowy.android.basiclib.task.BasicTask;

import java.util.HashMap;
import java.util.Map;


/**
 * 应用异步任务管理器
 */
public class AppTasks {
	private final static Map<Activity, AsyncTaskManager> mActivityTaskMap = new HashMap<Activity, AsyncTaskManager>();
	public static void createActivityTasks(Activity activity) {
		if (null == activity) {
			return;
		}

		synchronized (mActivityTaskMap) {
			AsyncTaskManager manager = new AsyncTaskManager();
			mActivityTaskMap.put(activity, manager);
		}
	}

	public static void removeTask(BasicTask task) {
		if (null == task) {
			return;
		}

		synchronized (mActivityTaskMap) {
			for (AsyncTaskManager manager : mActivityTaskMap.values()) {
				manager.removeTask(task);
			}
		}
	}

	public static void RemoveActivityTasks(Activity activity) {
		if (null == activity) {
			return;
		}

		synchronized (mActivityTaskMap) {
			if (mActivityTaskMap.containsKey(activity)) {
				mActivityTaskMap.get(activity).clear();
				mActivityTaskMap.remove(activity);
			}
		}
	}

	public static void removeAllTasks() {
		synchronized (mActivityTaskMap) {
			for (AsyncTaskManager manager : mActivityTaskMap.values()) {
				manager.clear();
			}

			mActivityTaskMap.clear();
		}
	}

	public static void AddTask(BasicTask task) {
		if (null == task) {
			return;
		}

		AddTask(null, task);
	}

	public static void AddTask(Activity activity, BasicTask task) {
		if(null == activity){
			activity = AppActivities.getCurrentActivity();
		}

		if (null == activity) {
			return;
		}

		if (null == task) {
			return;
		}

		synchronized (mActivityTaskMap) {
			if (mActivityTaskMap.containsKey(activity)) {
				mActivityTaskMap.get(activity).addTask(task);
			}
		}
	}
}
