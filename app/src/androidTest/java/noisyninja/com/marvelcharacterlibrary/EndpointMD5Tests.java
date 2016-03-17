package noisyninja.com.marvelcharacterlibrary;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import noisyninja.com.marvelcharacterlibrary.utils.NoisyUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class EndpointMD5Tests {

    @Before
    public void setUp() throws Exception {

        System.out.print("Running endpoint and authentication tests");
    }

    @Test
    public void testEndpoints() {

        assertNotNull(NoisyUtils.getCharacterURI(0));

        assertNotNull(NoisyUtils.getSearchCharacterURI(0, "H"));
        assertNotNull(NoisyUtils.getComicsURI("http://fakeuri.com/", 0));
    }

    @Test
    public void testRequestStamp() {
        assertNotNull(NoisyUtils.getStamp());
    }

    @Test
    public void testMD5() {
        String randomText = "randomText";
        assertNotNull(NoisyUtils.getMD5(randomText));
    }

    @Test
    public void testOther() {

        assertNotNull(NoisyUtils.getTag(this.getClass()));

    }

    @After
    public void tearDown() throws Exception {

        System.out.print("Completed endpoint and authentication tests");
    }
}