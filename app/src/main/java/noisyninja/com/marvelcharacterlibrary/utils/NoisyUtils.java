package noisyninja.com.marvelcharacterlibrary.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.DimenRes;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import noisyninja.com.marvelcharacterlibrary.NoisyConstants;
import noisyninja.com.marvelcharacterlibrary.R;
import noisyninja.com.marvelcharacterlibrary.components.CircleTransform;
import noisyninja.com.marvelcharacterlibrary.components.FlipAnimation;
import noisyninja.com.marvelcharacterlibrary.interfaces.IDialogCallback;
import noisyninja.com.marvelcharacterlibrary.models.Image;

/**
 * NoisyUtils class provides commonly used code snippets as static methods
 * Created by ir2pid on 15/03/16.
 */
public class NoisyUtils {

    private static String TAG = NoisyUtils.class.getSimpleName();
    private static ProgressDialog mProgressDialog;

    /**
     * returns the imagename uri by appending the path to extension
     *
     * @param image
     * @return
     */
    public static String getImageName(Image image) {
        return image.getPath() + "." + image.getExtension();
    }

    /**
     * returns a time and md5 stamped GET request uri to fetch characters
     *
     * @param offset offset for next set of characters
     * @return request uri as string
     */
    public static String getCharacterURI(int offset) {
        return NoisyConstants.ENDPOINT + NoisyConstants.GET_CHARACTERS + getStamp() + NoisyConstants.PARAM_OFFSET + offset + NoisyConstants.PARAM_LIMIT100;
    }

    /**
     * returns a time and md5 stamped GET request uri to fetch characters with keyword 'startswith'
     *
     * @param offset     offset for next set of characters
     * @param startswith keyword for search
     * @return request uri as string
     */
    public static String getSearchCharacterURI(int offset, String startswith) {
        return NoisyConstants.ENDPOINT + NoisyConstants.GET_CHARACTERS + getStamp() + NoisyConstants.PARAM_OFFSET + offset + NoisyConstants.PARAM_STARTSWITH + startswith + NoisyConstants.PARAM_LIMIT100;
    }

    /**
     * returns a time and md5 stamped GET request uri to fetch comics
     *
     * @param url    uri for character's comics
     * @param offset offset for next set of characters
     * @return request uri as string
     */
    public static String getComicsURI(String url, int offset) {
        return url + getStamp() + NoisyConstants.PARAM_OFFSET + offset + NoisyConstants.PARAM_LIMIT100;
    }

    /**
     * appends timestamp, MD5 of timestamp + public key + private key and privatekey parameters
     *
     * @return request authentication stamp
     */
    public static String getStamp() {
        long time = new Date().getTime();
        String hash = getMD5(time + NoisyConstants.PRIVATE_KEY + NoisyConstants.PUBLIC_KEY);
        return NoisyConstants.PARAM_TIMESTAMP + time + NoisyConstants.PARAM_HASH + hash + NoisyConstants.PARAM_APIKEY + NoisyConstants.PUBLIC_KEY;
    }

    /**
     * generates MD5 of input string
     *
     * @param input string to be hashed
     * @return MD5 hash of string
     */
    public static String getMD5(String input) {

        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.reset();
        m.update(input.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        //zero pad to get the full 32 chars.
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

    /**
     * starts an activity
     *
     * @param context   context of calling activity
     * @param nextClass activity class to be opened
     */
    public static void startActivity(Context context, Class nextClass) {
        Intent intent = new Intent(context, nextClass);
        context.startActivity(intent);
    }

    /**
     * shows a dialog
     *
     * @param context context of calling activity
     * @param title   title of dialog
     * @param message message to be shown in dialog
     */
    public static void showDialog(Context context, String title, String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = dialogBuilder.create();
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation_down_up;
        dialog.show();
    }


    /**
     * shows a dialog, provides callback for two buttons
     *
     * @param activity activity which can have the callback implemented for two buttons
     * @param title    title of dialog
     * @param message  message to be shown in dialog
     * @param isInfo   if dialog is info dialog
     */
    public static void showDialog(final Activity activity, String title, String message, boolean isInfo) {
        // custom dialog

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (activity instanceof IDialogCallback) {
                            ((IDialogCallback) activity).ok(dialog);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });

        if (!isInfo) {

            dialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (activity instanceof IDialogCallback) {
                        ((IDialogCallback) activity).cancel(dialog);
                    }
                }
            }).setIcon(android.R.drawable.ic_dialog_alert);
        }
        AlertDialog dialog = dialogBuilder.create();

        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation_down_up;
        dialog.show();
    }

    /**
     * shows a toast
     *
     * @param context context of calling activity
     * @param text    text to be displayed
     */
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * animates the view
     *
     * @param view       view to animate
     * @param techniques animation tyoe
     */
    public static void makeAnimation(View view, Techniques techniques) {
        YoYo.with(techniques)
                .duration(NoisyConstants.ANIMATION_TIME_700)
                .playOn(view);
    }

    /**
     * animate the view with rubberband animation type
     *
     * @param view view to animate
     */
    public static void makeAnimation(View view) {
        makeAnimation(view, Techniques.RubberBand);
    }

    /**
     * returns  json of an object
     *
     * @param o object to be deserialized
     * @return json string
     */
    public static String getToJson(Object o) {
        String json = "";
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            json = gson.toJson(o);
        } catch (Exception e) {
            System.out.println("Json Error" + e.getMessage());
        }
        return json;
    }

    /**
     * returns an object serialized from json
     *
     * @param data  json string to be serialized
     * @param clazz class to serialize into
     * @return serialized object
     */
    public static Object getFromJson(String data, Class clazz) {
        Object o = null;

        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            o = gson.fromJson(data, clazz);
        } catch (Exception e) {
            System.out.println("Json Error" + e.getMessage());
        }

        return o;
    }

    /**
     * checks for available network
     *
     * @param context context of calling activity
     * @return if network is available
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * to save in sharedpreference
     *
     * @param context context of calling activity
     * @param key     key for storing
     * @param value   value to be stored
     */
    public static void setPreference(Context context, String key, String value) {
        logD("saving key:" + key + " value:" + value);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * to retrieve a value from sharedpreference
     *
     * @param context context of calling activity
     * @param key     key whose value is to be fetchs
     * @return value or null if key not found
     */
    public static String getPreference(Context context, String key) {
        return getPreference(context, key, null);
    }

    /**
     * to retrieve a value from sharedpreference
     *
     * @param context      context of calling activity
     * @param key          key whose value is to be fetchs
     * @param defaultValue to be returned if key not found
     * @return value or defaultvalue if key not found
     */
    public static String getPreference(Context context, String key, String defaultValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(key, defaultValue);

        logD("got key:" + key + " value:" + value);
        return value;
    }

    /**
     * lazy loads the passed view with image in url, and caches image,
     * the bitmap image is then rounded with CircleTransform class
     *
     * @param context context of calling activity
     * @param view    view to hold the image
     * @param url     url of image
     */
    public static void glideLoadRounded(Context context, ImageView view, String url) {
        Glide.with(context)
                .load(url)
                .transform(new CircleTransform(context))
                .sizeMultiplier(1.0f)//fitCenter()
                .placeholder(R.drawable.loading)
                .crossFade()
                .animate(new FlipAnimation(view, view))
                .into(view);
    }

    /**
     * lazy loads the passed view with image in url, and caches image,
     *
     * @param context context of calling activity
     * @param view    view to hold the image
     * @param url     url of image
     */
    public static void glideLoad(Context context, ImageView view, String url) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                        //.fitCenter()
                        //.sizeMultiplier(1.0f)//
                .placeholder(R.drawable.loading)
                .crossFade()
                .animate(new FlipAnimation(view, view))
                .into(view);
    }

    /**
     * method to animate flip, flips top view to reveal the bottom view
     *
     * @param rootLayout root layout of both views
     * @param cardFace   first exposed view
     * @param cardBack   second hidden view
     */
    public static void animateFlip(View rootLayout, View cardFace, View cardBack) {
        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.INVISIBLE) {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    /**
     * gets float from resource values
     *
     * @param context context of calling activity
     * @param dimen   resource id
     * @return float
     */
    public static float getFloat(Context context, @DimenRes int dimen) {
        TypedValue outValue = new TypedValue();
        context.getResources().getValue(dimen, outValue, true);
        return outValue.getFloat();
    }


    /**
     * gets color from resource values
     *
     * @param context context of calling activity
     * @param colorId resource id
     * @return color id
     */
    public static int getColorResource(Context context, int colorId) {
        return context.getResources().getColor(colorId);
    }

    /**
     * gets string from resource values
     *
     * @param context  context of calling activity
     * @param stringId resource id
     * @return string
     */
    public static String getStringResource(Context context, int stringId) {
        return context.getResources().getString(stringId);
    }

    /**
     * uses a string builder to merge a set of strings
     *
     * @param args multiple string inputs
     * @return concatinated string
     */
    public static String getTempString(String... args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : args) {
            stringBuilder.append(str);
        }

        return stringBuilder.toString();
    }

    /**
     * to programatically press back button
     *
     * @param activity calling activity
     */
    public static void backPress(Activity activity) {
        activity.onBackPressed();
    }

    /**
     * activates a button and adds corrosponding drawable
     *
     * @param button
     */
    public static void buttonActivate(Button button) {
        button.setEnabled(true);
        button.setBackgroundResource(R.drawable.button_green);
    }

    /**
     * deactivates a button and adds corrosponding drawable
     *
     * @param button
     */
    public static void buttonDeactivate(Button button) {
        button.setEnabled(false);
        button.setBackgroundResource(R.drawable.button_grey);
    }

    /**
     * log debug a string
     *
     * @param s string to be logged
     */
    public static void logD(String s) {
        logD("LOG", s);
    }

    /**
     * log debug a string
     *
     * @param tag tag for logging
     * @param s   string to be logged
     */
    public static void logD(String tag, String s) {
        Log.d(tag, s);
    }

    /**
     * class to handle app errors
     *
     * @param context context of calling activity
     * @param e       error string
     */
    public static void handleError(Context context, String e) {

        Log.e(NoisyUtils.class.getCanonicalName(), NoisyConstants.ERROR + e);
        Toast.makeText(context, NoisyConstants.ERROR + e, Toast.LENGTH_SHORT).show();

    }

    /**
     * returns simple name of class
     *
     * @param c class which is to be named
     * @return simple name of class
     */
    public static String getTag(Class c) {
        return c.getSimpleName();
    }
}
