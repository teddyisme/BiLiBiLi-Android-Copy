package com.lxs.bilibili_android_copy.action;

import com.lxs.bilibili_android_copy.base.ActionsCreatorFactory;
import com.lxs.bilibili_android_copy.base.Dispatcher;

public class MainFragmentActionsCreator extends ActionsCreatorFactory {

    public MainFragmentActionsCreator(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public void setLooperViewImages() {
        actionsCreator.sendMessage(new MainFragmentAction.MainFragmentEntitrys().buildWithType(MainFragmentAction.ACTION_AD_IMAGES));
    }

    public void setVideoItems() {
        actionsCreator.sendMessage(new MainFragmentAction.MainFragmentEntitrys().buildWithType(MainFragmentAction.VIDEO_ITEMS));
    }

}
