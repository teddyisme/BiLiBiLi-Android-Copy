package com.lxs.bilibili_android_copy.base;

/**
 * Created by 00245338 on 2016/7/27.
 */
public class ActionsCreator {
    private static ActionsCreator instance;
    final Dispatcher dispatcher;

    public ActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public static ActionsCreator get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new ActionsCreator(dispatcher);
        }
        return instance;
    }

    public void sendMessage(Action action) {
        dispatcher.dispatch(action);
    }
}
