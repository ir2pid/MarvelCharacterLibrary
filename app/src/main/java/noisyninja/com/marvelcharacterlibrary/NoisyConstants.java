package noisyninja.com.marvelcharacterlibrary;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * App constants go here
 * Created by ir2pid on 15/03/16.
 */
public class NoisyConstants {

    public static String PUBLIC_KEY = "bea8592a910bdd1a957e0377f62d28d5";

    public static String PRIVATE_KEY = "99b6b66ac3ae48aefeb5f2e94a24cc1a94cca200";

    public static String ENDPOINT = "http://gateway.marvel.com:80";

    public static String GET_CHARACTERS = "/v1/public/characters";
    public static String GET_COMICS = "/v1/public/comics";
    public static String PARAM_TIMESTAMP = "?ts=";
    public static String PARAM_APIKEY = "&apikey=";
    public static String PARAM_HASH = "&hash=";
    public static String PARAM_OFFSET = "&offset=";
    public static String PARAM_STARTSWITH = "&nameStartsWith=";
    public static String PARAM_LIMIT100 = "&limit=100";
    public static String PARAM_LIMIT1 = "&limit=1";

    public static long ANIMATION_TIME_700 = 700;    // Sleep for some time
    public static String LOADING = "(LOADING)";
    public static String ERROR = "ERROR";
    public static String ERROR_NO_NETWORK = "NO_NETWORK";
    public static String ERROR_INVALID_JSON = "INVALID_JSON_OR_NOT_FOUND";
    public static String INVALID_NETWORK_REQUEST = "INVALID_NETWORK_REQUEST";
    public static String ERROR_FILE_NOT_FOUND = "FILE_NOT_FOUND";


    public static String URL_KEY = "URL_KEY";
    public enum Requests {

        GET_CHARACTERS("GET_CHARACTERS"),
        GET_ALL_CHARACTERS("GET_ALL_CHARACTERS"),
        GET_COMICS("GET_COMICS"),
        GET_ALL_COMICS("GET_ALL_COMICS");

        private final String url;

        /**
         * @param url
         */
        Requests(final String url) {
            this.url = url;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return url;
        }
    }
}
