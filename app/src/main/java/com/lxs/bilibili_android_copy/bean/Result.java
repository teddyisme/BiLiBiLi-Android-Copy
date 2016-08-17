package com.lxs.bilibili_android_copy.bean;

import java.util.List;

public class Result<T> extends BaseBean {
    private List<T> results;

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
