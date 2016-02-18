package com.nowy.android.basiclib.task;


import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.nowy.android.basiclib.R;
import com.nowy.android.basiclib.app.AppTasks;
import com.nowy.android.basiclib.utils.Tips;


/**
 * 异步任务基础封装
 */
public abstract class BasicTask<T> extends AsyncTask<String, Integer, T> {
	private boolean showTipsDialog = true;
	protected Activity curActivity = null;
	private String tipMsg;
	public BasicTask(){
	}

	public BasicTask(boolean showTips,String tipMsg){
		showTipsDialog = showTips;
		this.tipMsg=tipMsg;
	}
	public BasicTask(boolean showTips){
		showTipsDialog = showTips;
	}

	public BasicTask(Activity activity){
		curActivity = activity;
	}

	public BasicTask(Activity activity, boolean showTips){
		curActivity = activity;
		showTipsDialog = showTips;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		Tips.hideToast();
		AppTasks.removeTask(this);
	}

	/**
	 * 执行异步任务
	 */
	@Override
	protected abstract T doInBackground(String... params);

	/**
	 * 异步任务执行完以后的回调函数
	 */
	protected abstract void onTaskFinished(T result);

	/**
	 * 执行异步任务后的处理：将其从指定 BasicActivity 任务队列中移除
	 */
	@Override
	protected void onPostExecute(T result) {
		super.onPostExecute(result);
		
		if(showTipsDialog){
			Tips.hideToast();
		}

		// 运行回调函数
		onTaskFinished(result);

		AppTasks.removeTask(this);
	}

	/**
	 * 执行异步任务前的预处理：将其加入指定 BasicActiviy 的任务队列
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		AppTasks.AddTask(curActivity, this);

		if(showTipsDialog){
			if(tipMsg!=null&&!TextUtils.isEmpty(tipMsg.trim())){
				Tips.showShort(curActivity, tipMsg);
			}else{
				Tips.showShort(curActivity, R.string.loading);
			}
			
		}
	}
}
