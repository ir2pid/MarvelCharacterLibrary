package noisyninja.com.marvelcharacterlibrary;

import android.os.SystemClock;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * UI tests SearchActivityTests and DetailActivity
 * Created by ir2pid on 17/03/16.
 */
public class SearchActivityTests extends ActivityInstrumentationTestCase2<SearchActivity> {


    public SearchActivityTests() {
        super(SearchActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @Test
    public void testSearchActivity() {
        // Perform a click on first element in the RecyclerView
        onView(withId(R.id.editText)).perform(typeText("H"));
        sleep();
        onView(withId(R.id.clear)).perform(click());
        onView(withId(R.id.editText)).perform(typeText("AIR"));
        sleep();

        // Perform a click on first element in the RecyclerView
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        sleep();
        // Perform a swipe on gallery
        onView(withId(R.id.gallery1)).perform(swipeLeft());
        pressBack();
    }

    private void sleep() {
        SystemClock.sleep(20000);//sleep 20sec to sync data, increase time if loading is slow
    }
}