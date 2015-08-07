package com.sarltokyo.addressbookormlite7.app.test;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import com.sarltokyo.addressbookormlite7.core.ContextLogic;
import com.sarltokyo.addressbookormlite7.core.ContextLogicFactory;
import com.sarltokyo.addressbookormlite7.db.OpenHelper;
import com.sarltokyo.addressbookormlite7.entity.Address;
import com.sarltokyo.addressbookormlite7.entity.Person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by osabe on 15/07/14.
 *
 * This file is a modified version of BaseTestCase.java
 * https://github.com/cattaka/FastCheckList/blob/master/app/src/androidTest/java/net/cattaka/android/fastchecklist/test/BaseTestCase.java
 */
public class BaseTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {
    protected ContextLogic mContextLogic;
    private final static String TAG = BaseTestCase.class.getSimpleName();

    public BaseTestCase(Class<T> tClass) {
        super(tClass);
    }

    protected void setUp() throws Exception {
        super.setUp();
        Context context = getInstrumentation().getTargetContext();
        mContextLogic = new TestContextLogic(context);
        {   // Replace ContextLogicFactory to use RenamingDelegatingContext.
            ContextLogicFactory.replaceInstance(new ContextLogicFactory() {
                @Override
                public ContextLogic newInstance(Context context) {
                    return mContextLogic;
                }
            });
        }
        {   // Unlock keyguard and screen on
            KeyguardManager km = (KeyguardManager) getInstrumentation()
                    .getTargetContext().getSystemService(Context.KEYGUARD_SERVICE);
            PowerManager pm = (PowerManager) getInstrumentation()
                    .getTargetContext().getSystemService(Context.POWER_SERVICE);
            if (km.inKeyguardRestrictedInputMode() || !pm.isScreenOn()) {
                Intent intent = new Intent(getInstrumentation().getContext(), UnlockKeyguardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getInstrumentation().getTargetContext().startActivity(intent);
                while (km.inKeyguardRestrictedInputMode()) {
                    SystemClock.sleep(100);
                }
            }
        }

        cleanData();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void cleanData() {
        Log.d(TAG, "cleanData");
        OpenHelper openHelper = mContextLogic.createOpenHelper();
        List<Person> persons = null;
        try {
            persons = openHelper.findPerson();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        for (Person person : persons) {
            try {
                openHelper.deletePerson(person.getName());
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public List<Person> createTestData(int personsNum) {
        Log.d(TAG, "createTestData");
        List<Person> persons = new ArrayList<Person>();
        // Creating dummy data.
        OpenHelper openHelper = mContextLogic.createOpenHelper();
        for (int i = 0; i < personsNum; i++) {
            Person person = new Person();
            Address address = new Address();
            address.setZipcode("123-456" + i);
            address.setPrefecture("Tokyo");
            address.setCity("Shinjyuku-ku");
            address.setOther("Higash-shinjyuku 1-2-" + i);
            person.setName("Hoge" + i);
            person.setAddress(address);
            try {
                openHelper.registerPerson(person);
                persons.add(person);
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return persons;
    }
}

