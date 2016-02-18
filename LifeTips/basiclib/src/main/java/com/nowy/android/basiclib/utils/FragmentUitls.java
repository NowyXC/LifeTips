package com.nowy.android.basiclib.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Nowy on 2016/1/7.
 */
public class FragmentUitls {
    /**
     * Fragment跳转
     * @param fm
     * @param fragmentClass
     * @param tag
     * @param args
     */
    public static void turnToFragment(FragmentManager fm, Class<? extends Fragment> fragmentClass,
                               int resId,String tag, boolean isAddToBackStack, Bundle args) {

        Fragment fragment = fm.findFragmentByTag(tag);
        boolean isFragmentExist = true;
        if (fragment == null) {
            try {
                isFragmentExist = false;
                fragment = fragmentClass.newInstance();
                fragment.setArguments(new Bundle());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(fragment.isAdded()){
            return;
        }
        if( args != null && !args.isEmpty() ) {
            fragment.getArguments().putAll(args);
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_in, android.R.anim.fade_out);
        if( isFragmentExist ) {
            ft.replace(resId, fragment);
        } else {
            ft.replace(resId, fragment, tag);
        }

        if(isAddToBackStack)
            ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
    }
}
