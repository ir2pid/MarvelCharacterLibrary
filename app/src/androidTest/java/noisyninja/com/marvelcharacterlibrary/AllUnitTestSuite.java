package noisyninja.com.marvelcharacterlibrary;

import android.test.suitebuilder.TestSuiteBuilder;


import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.junit.runners.Suite;

/**
 * Created by ir2pid on 18/03/16.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EndpointMD5Tests.class,
        GsonMarshallUnmarshallTests.class
})
public class AllUnitTestSuite {
    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        suite.addTest(TestSuite.createTest(MainActivityTests.class, "test_MainActivity"));
        suite.addTest(TestSuite.createTest(SearchActivityTests.class, "test_SearchActivity"));
        return suite;
    }
}
