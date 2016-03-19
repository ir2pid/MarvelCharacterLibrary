package noisyninja.com.marvelcharacterlibrary;


import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.junit.runners.Suite;

/**
 * Suite contains all unit tests
 * Created by ir2pid on 18/03/16.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        EndpointMD5Tests.class,
        GsonSerializeDeserializeTests.class
})
public class AllUnitTestSuite {

}
