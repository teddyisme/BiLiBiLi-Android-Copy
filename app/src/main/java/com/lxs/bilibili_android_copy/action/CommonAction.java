package com.lxs.bilibili_android_copy.action;

import com.lxs.bilibili_android_copy.base.Action;
import com.lxs.bilibili_android_copy.base.IActionEntityBuilder;

public class CommonAction extends Action<CommonAction.CommonActionEntitry> {

    public static final String ACTION_DAY_THEM_CHANGE = "change_day_them";
    public static final String ACTION_NIGHT_THEM_CHANGE = "change_night_them";

    public CommonAction(String type, CommonActionEntitry data) {
        super(type, data);
    }

    public static class CommonActionEntitry implements IActionEntityBuilder {

        @Override
        public Action buildWithType(String type) {
            return new CommonAction(type, this);
        }
    }
}
