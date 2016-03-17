package noisyninja.com.marvelcharacterlibrary.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import noisyninja.com.marvelcharacterlibrary.NoisyApplication;
import noisyninja.com.marvelcharacterlibrary.NoisyConstants;
import noisyninja.com.marvelcharacterlibrary.interfaces.INetworkCallback;

/**
 * Created by ir2pid on 15/03/16.
 */
public class NoisyNetwork {

    private static String TAG = NoisyNetwork.class.getSimpleName();


    public static void get(final Context context, String url, final NoisyConstants.Requests requests, final INetworkCallback iNetworkCallback) {

        if (!NoisyUtils.isNetworkAvailable(context)) {
            NoisyUtils.showDialog(context, NoisyConstants.ERROR, NoisyConstants.ERROR_NO_NETWORK);
        }

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage(NoisyConstants.LOADING);
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //NoisyUtils.logD(TAG, response.toString());
                        iNetworkCallback.response(response.toString(), requests.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NoisyUtils.showToast(context, error.getLocalizedMessage());
                pDialog.hide();
            }
        }) {
            @Override
            public Priority getPriority() {
                return Priority.LOW;
            }
        };

        // Add request to request queue
        NoisyApplication.getInstance().addToRequestQueue(jsonObjReq, requests.toString());
    }

    public static void getBackground(final Context context, String url, final NoisyConstants.Requests requests, final INetworkCallback iNetworkCallback) {

        if (!NoisyUtils.isNetworkAvailable(context)) {
            NoisyUtils.showDialog(context, NoisyConstants.ERROR, NoisyConstants.ERROR_NO_NETWORK);
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //NoisyUtils.logD(TAG, response.toString());
                        iNetworkCallback.response(response.toString(), requests.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NoisyUtils.showToast(context, error.getLocalizedMessage());
            }
        }) {
            @Override
            public Priority getPriority() {
                return Priority.LOW;
            }
        };

        // Add request to request queue
        NoisyApplication.getInstance().addToRequestQueue(jsonObjReq, requests.toString());
    }
}
