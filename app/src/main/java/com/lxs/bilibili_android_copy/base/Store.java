package com.lxs.bilibili_android_copy.base;


import org.greenrobot.eventbus.EventBus;


/**
 * Forrest
 * Flux的Store模块
 */
public abstract class Store {
    public static Integer STATE_SUCSSCE = 0;
    public static Integer STATE_Failed = 1;

    protected Store() {

    }


    public void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public void unRegister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }

    }

    /**
     * 传入操作类型，然后出发主界面更新
     */
    public void emitStoreChange(StoreChangeEvent storeChangeEvent) {
        EventBus.getDefault().post(storeChangeEvent);
    }

    /**
     * 传入操作类型，然后出发主界面更新
     *
     * @param operationType
     */
    public void emitStoreChange(String operationType) {
        EventBus.getDefault().post(changeEvent(operationType));
    }

    public abstract StoreChangeEvent changeEvent(String operationType);

    /**
     * 所有逻辑的处理，在实现类中可以简单想象成对应着一个Activity（View）的增删改查的处理
     *
     * @param action
     */
    public abstract void onAction(Action action);

    /**
     * 返回到view中的对象，在activity得到这个对象，通过operationtype来判断对应的操作来更新对应的ui
     */
    public class StoreChangeEvent {
        private String operationType;

        public String getOperationType() {
            return operationType;
        }

        public StoreChangeEvent(String operationType) {
            this.operationType = operationType;
        }
    }


    /**
     * 基类覆盖该方法可以在activity或者fragment的onstart回调
     */
    public void onStart() {

    }

    ;


}
