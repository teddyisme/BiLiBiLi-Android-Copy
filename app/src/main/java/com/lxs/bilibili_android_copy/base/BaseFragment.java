package com.lxs.bilibili_android_copy.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by 00245338 on 2016/7/28.
 */
public abstract class BaseFragment extends Fragment implements IBaseFragment {
    public Dispatcher dispatcher;
    private Store store;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(bindLayout(), container, false);
        ButterKnife.bind(this, view);
        // 控件初始化
        initView(view);

        dispatcher=Dispatcher.get();

        doBusiness(getActivity());

        return view;
    }

    /**
     * 获取当前Fragment依附在的Activity
     *
     * @return
     */
    public Activity getContext() {
        return getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        store=initStore();
        if(store!=null){
            dispatcher.register(this, store);
            store.onStart();
        }    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(store!=null){
            dispatcher.unregister(this, store);
        }
    }

    @Subscribe
    public void onEventMainThread(Object event) {
        onViewUpdate(event);
    }
}
