package noisyninja.com.marvelcharacterlibrary.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import noisyninja.com.marvelcharacterlibrary.NoisyApplication;
import noisyninja.com.marvelcharacterlibrary.NoisyConstants;
import noisyninja.com.marvelcharacterlibrary.R;
import noisyninja.com.marvelcharacterlibrary.interfaces.INetworkCallback;

/**
 * Utility class to make network calls,
 * errors are handled here,
 * valid response is passed to respective network callback implementer
 * Created by ir2pid on 15/03/16.
 */
public class NoisyNetwork {

    private static String TAG = NoisyNetwork.class.getSimpleName();

    /**
     * to make REST get call and block the UI
     *
     * @param context          context of caller activity
     * @param url              url for the request
     * @param requests         request type as enum
     * @param iNetworkCallback callback on sucess response
     */
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

    /**
     * to make REST background get call and not block the UI
     *
     * @param context          context of caller activity
     * @param url              url for the request
     * @param requests         request type as enum
     * @param iNetworkCallback callback on sucess response
     */
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

                if (error instanceof TimeoutError) {
                    Toast.makeText(context, NoisyUtils.getStringResource(context, R.string.timeouterror),
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, NoisyUtils.getStringResource(context, R.string.authfailureerror),
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, NoisyUtils.getStringResource(context, R.string.servererror),
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(context, NoisyUtils.getStringResource(context, R.string.networkerror),
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, NoisyUtils.getStringResource(context, R.string.parseerror),
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(context, NoisyUtils.getStringResource(context, R.string.noconnectionerror),
                            Toast.LENGTH_LONG).show();
                }

                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    NoisyUtils.logD("Status code", String.valueOf(networkResponse.statusCode));
                }
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
