package noisyninja.com.marvelcharacterlibrary;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Custom applicaation to maintain app specific volley network request queue and Image loader(unused), using Glide to load and chache images
 * Created by ir2pid on 13/03/16.
 */
public class NoisyApplication extends Application {

    public static final String TAG = NoisyApplication.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static NoisyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized NoisyApplication getInstance() {
        return mInstance;
    }

    /**
     * returns new request queue if not created
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * adds request to queue
     * @param req request to be added
     * @param tag tag of request
     * @param <T> generic request type
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * adds request to queue without specific tag
     * @param req request to be added
     * @param <T> generic request type
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * to cancel a request
     * @param tag tag of request to be cancelled
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
