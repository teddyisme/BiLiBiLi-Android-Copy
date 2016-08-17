package com.lxs.bilibili_android_copy.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lxs.bilibili_android_copy.R;
import com.lxs.bilibili_android_copy.ui.fragment.MainFragment;

import java.util.HashMap;

public class MyTabFragmentAdapter extends FragmentStatePagerAdapter {

    private static HashMap<String, Fragment> fragments;

    public static String[] tabs;


    public MyTabFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        tabs = context.getResources().getStringArray(R.array.tabs);
        fragments = new HashMap<String, Fragment>();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                fragment = new MainFragment();
                break;
            case 1:
                fragment = new MainFragment();
                bundle.putInt("type", 0);
                break;
            case 2:
                fragment = new MainFragment();
                bundle.putInt("type", 1);

                break;
            case 3:
                fragment = new MainFragment();
                bundle.putInt("type", 2);
                break;
            case 4:
                fragment = new MainFragment();
                bundle.putInt("type", 3);

                break;
            case 5:
                fragment = new MainFragment();
                break;
            default:
                fragment = new MainFragment();
        }
        fragment.setArguments(bundle);
        fragments.put(String.valueOf(position), fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }


    public static Fragment getFragment(int position) {
        return fragments.get(String.valueOf(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }


}

