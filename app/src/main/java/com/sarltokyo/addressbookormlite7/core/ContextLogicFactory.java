package com.sarltokyo.addressbookormlite7.core;

import android.content.Context;

/**
 * https://github.com/cattaka/FastCheckList/blob/master/app/src/main/java/net/cattaka/android/fastchecklist/core/ContextLogicFactory.java
 */
public class ContextLogicFactory {
    static ContextLogicFactory INSTANCE = new ContextLogicFactory();

    public ContextLogic newInstance(Context context) {
        return new ContextLogic(context);
    }

    public static ContextLogic createContextLogic(Context context) {
        return INSTANCE.newInstance(context);
    }

    public static void replaceInstance(ContextLogicFactory INSTANCE) {
        ContextLogicFactory.INSTANCE = INSTANCE;
    }
}