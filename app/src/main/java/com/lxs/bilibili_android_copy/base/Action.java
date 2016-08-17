package com.lxs.bilibili_android_copy.base;

import java.io.Serializable;

/**
 * Created by 00245338 on 2016/7/27.
 */
public class Action<T extends IActionEntityBuilder> implements Serializable {
    private final String type;
    private final T data;

    public Action(String type, T data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}
