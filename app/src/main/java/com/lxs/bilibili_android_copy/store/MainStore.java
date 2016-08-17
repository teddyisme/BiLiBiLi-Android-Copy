package com.lxs.bilibili_android_copy.store;

import com.lxs.bilibili_android_copy.action.MainAction;
import com.lxs.bilibili_android_copy.base.Action;
import com.lxs.bilibili_android_copy.base.Store;

public class MainStore extends Store {

    @Override
    public StoreChangeEvent changeEvent(String operationType) {
        return new MainStoreEvent(operationType);
    }

    @Override
    public void onAction(Action action) {
        String operationType = action.getType();
        switch (operationType) {
            case MainAction.ACTION_NEW_MESSAGE:
                MainAction.MainActionEntitry messageActionEntity = (MainAction.MainActionEntitry) action.getData();
//                mMessage.setMessage(messageActionEntity.getText());
                break;
            default:
        }
        emitStoreChange(operationType);
    }

    public class MainStoreEvent extends StoreChangeEvent {

        public MainStoreEvent(String operationType) {
            super(operationType);
        }
    }
}
