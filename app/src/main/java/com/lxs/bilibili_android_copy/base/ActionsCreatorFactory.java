package com.lxs.bilibili_android_copy.base;


/**
 * Created by forrest on 16/3/1.
 */
public abstract class ActionsCreatorFactory {

   public  ActionsCreator actionsCreator;

    public ActionsCreatorFactory(Dispatcher dispatcher){
        actionsCreator=ActionsCreator.get(dispatcher);
    }
}
