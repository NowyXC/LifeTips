package com.nowy.android.basiclib.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nowy.android.basiclib.app.AppActivities;


/**
 * Created by Nowy on 2015/12/30.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppActivities.pushActivity(this);

        initViews();
    }

    protected abstract  void initViews();


    @Override
    protected void onResume() {
        super.onResume();
        AppActivities.setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        AppActivities.removeActivity(this);
        super.onDestroy();
    }
}
