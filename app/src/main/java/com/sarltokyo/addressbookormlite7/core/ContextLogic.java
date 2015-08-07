package com.sarltokyo.addressbookormlite7.core;

import android.content.Context;
import com.sarltokyo.addressbookormlite7.db.OpenHelper;

/**
 * https://github.com/cattaka/FastCheckList/blob/master/app/src/main/java/net/cattaka/android/fastchecklist/core/ContextLogic.java
 */
public class ContextLogic {
    protected Context mContext;

    public ContextLogic(Context context) {
        mContext = context;
    }

    public OpenHelper createOpenHelper() {
        return new OpenHelper(mContext);
    }
}
