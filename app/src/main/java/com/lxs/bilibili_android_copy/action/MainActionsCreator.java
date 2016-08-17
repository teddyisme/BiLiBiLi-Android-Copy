package com.lxs.bilibili_android_copy.action;


import com.lxs.bilibili_android_copy.base.ActionsCreatorFactory;
import com.lxs.bilibili_android_copy.base.Dispatcher;


public class MainActionsCreator extends ActionsCreatorFactory {

    public MainActionsCreator(Dispatcher dispatcher){
        super(dispatcher);
    }


    public void setText(String text){

        actionsCreator.sendMessage(new MainAction.MainActionEntitry()
                .setText(text).buildWithType(MainAction.ACTION_NEW_MESSAGE));

    }


}
