package com.nowy.android.lifetips.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nowy.android.basiclib.base.BaseFragment;
import com.nowy.android.lifetips.R;

/**
 * Created by Nowy on 2016/2/4.
 */
public class EmptyFragment extends BaseFragment {

    public static EmptyFragment newInstance(){
        EmptyFragment emptyFragment = new EmptyFragment();

        return  emptyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.empty_data,container,false);
        return view;
    }
}
