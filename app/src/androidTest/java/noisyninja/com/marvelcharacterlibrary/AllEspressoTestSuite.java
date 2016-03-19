package noisyninja.com.marvelcharacterlibrary;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.junit.runners.Suite;

/**
 * suite contains all UI espresso tests
 * Created by ir2pid on 18/03/16.
 */

public class AllEspressoTestSuite {
    public static Test suite() {
        TestSuite suite = new TestSuite(AllEspressoTestSuite.class.getName());
        suite.addTest(TestSuite.createTest(MainActivityTests.class, "testMainActivity"));
        suite.addTest(TestSuite.createTest(SearchActivityTests.class, "testSearchActivity"));
        return suite;
    }
}
