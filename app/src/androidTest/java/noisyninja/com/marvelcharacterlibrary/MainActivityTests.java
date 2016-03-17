package noisyninja.com.marvelcharacterlibrary;

import android.app.Application;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {


    public MainActivityTests() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
        sleep();
    }

    @Test
    public void testMainActivity() {
        // Perform a click on first element in the RecyclerView
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        sleep();
        // Perform swipe on gallery
        onView(allOf(withId(R.id.gallery1), isDisplayed())).perform(click());
        sleep();
        pressBack();
        onView(withId(R.id.gallery1)).perform(swipeLeft());
        pressBack();
    }

    private void sleep() {
        SystemClock.sleep(20000);//sleep 20 sec to sync data, increase time if loading is slow
    }
}