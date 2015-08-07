package com.sarltokyo.addressbookormlite7.app;

import android.os.IBinder;
import android.support.test.espresso.*;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.WindowManager;
import android.widget.ListView;
import com.sarltokyo.addressbookormlite7.app.test.BaseTestCase;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.anything;



public class MainActivity3Test extends BaseTestCase<MainActivity> {
    private final static String TAG = MainActivity3Test.class.getSimpleName();


    public MainActivity3Test() {
        super(MainActivity.class);
    }



    public void testTransitionRegisterActivity1() throws Throwable {
        // Creating dummy data
        createTestData(200);

        MainActivity activity = getActivity();

        assertFalse(activity.isFinishing());
        final ListView listView = (ListView) activity.findViewById(android.R.id.list);

        // The test works OK on API Level 21 emulator, but fails on API Level 19 emulator.
        assertEquals(200, listView.getCount());

        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0)
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));


        // todo: これでOK
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0)
                .perform(click());
        // todo: これでOK
        onView(withId(R.id.nameEt)).check(matches(withText("Hoge0")));
        onView(withId(R.id.zipcodeEt)).check(matches(withText("123-4560")));
        onView(withId(R.id.prefectureEt)).check(matches(withText("Tokyo")));
        onView(withId(R.id.cityEt)).check(matches(withText("Shinjyuku-ku")));
        onView(withId(R.id.otherEt)).check(matches(withText("Higash-shinjyuku 1-2-0")));
        // todo : ボタンがUPDATEと表示されてることをテストしたい

        onView(withId(R.id.btn)).check(matches(isClickable()));
    }


    public void testTransitionRegisterActivity2() throws Throwable {
        // Creating dummy data
        createTestData(200);

        MainActivity activity = getActivity();

        assertFalse(activity.isFinishing());

        onView(withId(R.id.input_add)).perform(click());

        onView(withId(R.id.nameEt)).check(matches(withText("")));
        onView(withId(R.id.zipcodeEt)).check(matches(withText("")));
        onView(withId(R.id.prefectureEt)).check(matches(withText("")));
        onView(withId(R.id.cityEt)).check(matches(withText("")));
        onView(withId(R.id.otherEt)).check(matches(withText("")));
        // todo : ボタンがREGISTERと表示されてることをテストしたい

        onView(withId(R.id.btn)).check(matches(isClickable()));
    }

    public void testAddPerson() throws Throwable {
        // Creating dummy data
        createTestData(200);

        MainActivity activity = getActivity();

        onView(withId(R.id.input_add)).perform(click());

        assertFalse(activity.isFinishing());
        onView(withId(R.id.nameEt)).perform(typeText("Bar"));
        onView(withId(R.id.zipcodeEt)).perform(typeText("123-4567"));
        onView(withId(R.id.prefectureEt)).perform(typeText("Tokyo"));
        onView(withId(R.id.cityEt)).perform(typeText("Nerima-ku"));
        onView(withId(R.id.otherEt)).perform(typeText("Nerima 1-2-3"));

        onView(withId(R.id.btn)).perform(click());
    }

    public void testUpdateAddress() throws Throwable {
        // Creating dummy data
        createTestData(200);

        MainActivity activity = getActivity();

        assertFalse(activity.isFinishing());

        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0)
                .perform(click());

        onView(withId(R.id.zipcodeEt)).perform(clearText()).perform(typeText("345-6789"));
        onView(withId(R.id.prefectureEt)).perform(clearText()).perform(typeText("Kyoto"));
        onView(withId(R.id.cityEt)).perform(clearText()).perform(typeText("Shimogyo-ku"));
        onView(withId(R.id.otherEt)).perform(clearText()).perform(typeText("Higashi 1-2-3"));
        onView(withId(R.id.btn)).perform(click());
        // todo: MainActivityに戻ったことをテストしたい
    }

    public void testNameEmptyInfo() throws Throwable {
        // Creating dummy data
        createTestData(200);

        MainActivity activity = getActivity();

        assertFalse(activity.isFinishing());

        onView(withId(R.id.input_add)).perform(click());
        onView(withId(R.id.nameEt)).perform(typeText(""));

        // todo: うまくいかない
//        onView(withText("name is empty.")).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView()))))
//                .check(matches(isDisplayed()));

        onView(withText("name is empty.")).inRoot(isToast());
    }

    public void testZipcodeEmptyInfo() throws Throwable {
        // Creating dummy data
        createTestData(200);

        MainActivity activity = getActivity();

        assertFalse(activity.isFinishing());

        onView(withId(R.id.input_add)).perform(click());
        onView(withId(R.id.zipcodeEt)).perform(typeText(""));

        onView(withText("zipcode is empty.")).inRoot(isToast());
    }

    public void testPrefectureEmptyInfo() throws Throwable {
        // Creating dummy data
        createTestData(200);

        MainActivity activity = getActivity();

        assertFalse(activity.isFinishing());

        onView(withId(R.id.input_add)).perform(click());
        onView(withId(R.id.prefectureEt)).perform(typeText(""));

        onView(withText("prefecture is empty.")).inRoot(isToast());
    }

    public void testCityEmptyInfo() throws Throwable {
        // Creating dummy data
        createTestData(200);

        MainActivity activity = getActivity();

        assertFalse(activity.isFinishing());

        onView(withId(R.id.input_add)).perform(click());
        onView(withId(R.id.cityEt)).perform(typeText(""));

        onView(withText("city is empty.")).inRoot(isToast());
    }

    public void testOtherEmptyInfo() throws Throwable {
        // Creating dummy data
        createTestData(200);

        MainActivity activity = getActivity();

        assertFalse(activity.isFinishing());

        onView(withId(R.id.input_add)).perform(click());
        onView(withId(R.id.otherEt)).perform(typeText(""));

        onView(withText("other is empty.")).inRoot(isToast());
    }


//    private void setSelection(final ListView listView, final int position) throws Throwable {
//        runTestOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                listView.setSelection(position);
//            }
//        });
//    }
////    private void select(final int position) throws Throwable {
////        runTestOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                onData(allOf(hasToString(startsWith("Hoge")))).inAdapterView(withId(android.R.id.list)).atPosition(position)
////                        .perform(click());
////            }
////        });
////    }

    /**
     * Matcher that is Toast window.
     * http://baroqueworksdevjp.blogspot.jp/2015/03/espressotoast.html
     */
    public static Matcher<Root> isToast() {
        return new TypeSafeMatcher<Root>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("is toast");
            }

            @Override
            public boolean matchesSafely(Root root) {
                int type = root.getWindowLayoutParams().get().type;
                if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                    IBinder windowToken = root.getDecorView().getWindowToken();
                    IBinder appToken = root.getDecorView().getApplicationWindowToken();
                    if (windowToken == appToken) {
                        // windowToken == appToken means this window isn't contained by any other windows.
                        // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
