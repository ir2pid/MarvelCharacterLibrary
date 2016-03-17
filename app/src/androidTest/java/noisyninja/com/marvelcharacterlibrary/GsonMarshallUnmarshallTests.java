package noisyninja.com.marvelcharacterlibrary;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import noisyninja.com.marvelcharacterlibrary.models.CharacterDataWrapper;
import noisyninja.com.marvelcharacterlibrary.models.Comic;
import noisyninja.com.marvelcharacterlibrary.models.Image;
import noisyninja.com.marvelcharacterlibrary.utils.NoisyUtils;

import static org.junit.Assert.assertNotNull;

/**
 * Created by ir2pid on 17/03/16.
 */
public class GsonMarshallUnmarshallTests {

    Comic comic;

    @Before
    public void setUp() throws Exception {

        System.out.print("Running JSON Marshall Unmarshall tests");
        comic = new Comic();
        comic.setId(1);
        comic.setDescription("setDescription");
        comic.setResourceURI("getResourceURI");
        comic.setTitle("setTitle");
        Image i = new Image();
        i.setExtension(".jpg");
        i.setPath("setPath");
        comic.setThumbnail(i);
    }

    @Test
    public void testMarshallUnmarshall() {

        String toJson = NoisyUtils.getToJson(comic);

        assertNotNull(toJson);

        Comic fromJson = (Comic) NoisyUtils.getFromJson(toJson, Comic.class);

        assertNotNull(fromJson);

    }

    @After
    public void tearDown() throws Exception {

        System.out.print("Completed JSON Marshall Unmarshall tests");
    }
}
