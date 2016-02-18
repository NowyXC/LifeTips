package com.nowy.android.basiclib.task;


import android.os.AsyncTask;

import com.nowy.android.basiclib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 异步任务管理器
 * 
 */
public class AsyncTaskManager {
	private List< BasicTask> taskList = new ArrayList< BasicTask>();

	/**
	 * 从队列中移除异步任务
	 *
	 */
	public synchronized void removeTask( BasicTask task) {
		if (null == task) {
			return;
		}

		if (taskList.contains(task)) {
			taskList.remove(task);
		}
	}

	/**
	 * 取消队列中所有异步任务
	 */
	public synchronized void clear() {
		int taskNum = taskList.size();

		for (int i = taskNum; i > 0; i--) {
			 BasicTask task = taskList.get(i - 1);

			// 如果Task还在运行，则先取消它
			if (!task.isCancelled() && task.getStatus() != AsyncTask.Status.FINISHED) {
				try {
					task.cancel(true);
				} catch (Exception e) {
					LogUtils.e(e.getMessage());
				}
			}
		}

		taskList.clear();
	}

	/**
	 * 添加异步任务到异步任务队列
	 */
	public void addTask(BasicTask task) {
		if (null == task) {
			return;
		}

		if (taskList.contains(task)) {
			return;
		}

		taskList.add(task);
	}
}
