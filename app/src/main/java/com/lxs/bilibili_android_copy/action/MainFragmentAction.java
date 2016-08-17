package com.lxs.bilibili_android_copy.action;

import com.lxs.bilibili_android_copy.base.Action;
import com.lxs.bilibili_android_copy.base.IActionEntityBuilder;

public class MainFragmentAction extends Action<MainFragmentAction.MainFragmentEntitrys> {

    public static final String ACTION_AD_IMAGES = "ad_images";
    public static final String VIDEO_ITEMS = "video_items";

    public MainFragmentAction(String type, MainFragmentEntitrys data) {
        super(type, data);
    }

    public static class MainFragmentEntitrys implements IActionEntityBuilder {

        @Override
        public Action buildWithType(String type) {
            return new MainFragmentAction(type, this);
        }
    }
}
