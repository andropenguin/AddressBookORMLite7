package com.sarltokyo.addressbookormlite7.app.test;

import android.content.Context;
import android.test.RenamingDelegatingContext;
import com.sarltokyo.addressbookormlite7.core.ContextLogic;
import com.sarltokyo.addressbookormlite7.db.OpenHelper;

/**
 * https://github.com/cattaka/FastCheckList/blob/master/app/src/androidTest/java/net/cattaka/android/fastchecklist/test/TestContextLogic.java
 */
public class TestContextLogic extends ContextLogic {
    private RenamingDelegatingContext mRdContext;
    public TestContextLogic(Context context) {
        super(context);
        mRdContext = new RenamingDelegatingContext(context, "test_");
    }

    @Override
    public OpenHelper createOpenHelper() {
        return new OpenHelper(mRdContext);
    }
}
