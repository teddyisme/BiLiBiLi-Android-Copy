package com.lxs.bilibili_android_copy.action;

import com.lxs.bilibili_android_copy.base.Action;
import com.lxs.bilibili_android_copy.base.IActionEntityBuilder;

/**
 * Created by 00245338 on 2016/7/27.
 */
public class MainAction extends Action<MainAction.MainActionEntitry> {
    public static final String ACTION_NEW_MESSAGE = "new_message";

    public MainAction(String type, MainActionEntitry data) {
        super(type, data);
    }

    public static class MainActionEntitry implements IActionEntityBuilder {
        private String text;

        public String getText() {
            return text;
        }

        public MainActionEntitry setText(String text) {
            this.text = text;
            return this;
        }

        @Override
        public Action buildWithType(String type) {
            return new MainAction(type, this);
        }
    }
}
