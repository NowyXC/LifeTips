package com.nowy.android.lifetips.ui;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.nowy.android.basiclib.base.BaseActivity;
import com.nowy.android.lifetips.R;

/**
 * Created by Nowy on 2016/2/17.
 */
public class DetailActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {

        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);


        setContentView(R.layout.detail_page);



        ImageView ivImg = (ImageView) findViewById(R.id.ivImg);
        ivImg.setTransitionName("image");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
