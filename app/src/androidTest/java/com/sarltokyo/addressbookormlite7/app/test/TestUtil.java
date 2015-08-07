package com.sarltokyo.addressbookormlite7.app.test;

import android.os.SystemClock;

/**
 * https://github.com/cattaka/FastCheckList/blob/master/app/src/androidTest/java/net/cattaka/android/fastchecklist/test/TestUtil.java
 */
public class TestUtil {
    public static void waitForBoolean(BooleanFunc func, int timeout) {
        long time = SystemClock.elapsedRealtime();
        do {
            if (func.run()) {
                break;
            }
            SystemClock.sleep(50);
        } while (SystemClock.elapsedRealtime() - time <= time);
    }

    public interface BooleanFunc {
        public boolean run();
    }
}
