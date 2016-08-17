package com.lxs.bilibili_android_copy.base;



/**
 * Flux的Dispatcher模块
 * Forrest
 */
public class Dispatcher {

    public final String TAG=Dispatcher.class.getSimpleName();

    private static Dispatcher instance;

    private volatile Store currentStore;

    public static Dispatcher get() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }

    Dispatcher() {}

    public void register(Object subscriber, final Store store) {

        if(store!=null){
            store.register(subscriber);
        }
        this.currentStore=store;
    }

    public void unregister(Object subscriber, final Store store) {
        if(store!=null){
            store.unRegister(subscriber);
        }
        if(currentStore==store){
            currentStore=null;
        }
    }

    public void dispatch(Action action) {
        post(action);
    }



    private void post(final Action action) {
        if(currentStore!=null){
            currentStore.onAction(action);
        }

    }
}
