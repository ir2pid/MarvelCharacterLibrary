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
 * Created by ir2pid on 15/03/16.
 */
public class NoisyUtils {

    private static String TAG = NoisyUtils.class.getSimpleName();
    private static ProgressDialog mProgressDialog;

    public static String getImageName(Image image) {
        return image.getPath() + "." + image.getExtension();
    }

    public static String getCharacterURI(int offset) {
        return NoisyConstants.ENDPOINT + NoisyConstants.GET_CHARACTERS + getStamp() + NoisyConstants.PARAM_OFFSET + offset + NoisyConstants.PARAM_LIMIT100;
    }

    public static String getSearchCharacterURI(int offset, String startswith) {
        return NoisyConstants.ENDPOINT + NoisyConstants.GET_CHARACTERS + getStamp() + NoisyConstants.PARAM_OFFSET + offset + NoisyConstants.PARAM_STARTSWITH + startswith;
    }

    public static String getComicsURI(String url, int offset) {
        return url + getStamp() + NoisyConstants.PARAM_OFFSET + offset + NoisyConstants.PARAM_LIMIT100;
    }

    public static String getStamp() {
        long time = new Date().getTime();
        String hash = getMD5(time + NoisyConstants.PRIVATE_KEY + NoisyConstants.PUBLIC_KEY);
        return NoisyConstants.PARAM_TIMESTAMP + time + NoisyConstants.PARAM_HASH + hash + NoisyConstants.PARAM_APIKEY + NoisyConstants.PUBLIC_KEY;
    }

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

    public static void startActivity(Context context, Class nextClass) {
        Intent intent = new Intent(context, nextClass);
        //myIntent.putExtra("key", value); //Optional parameters
        context.startActivity(intent);
    }

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

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void makeAnimation(View view, Techniques techniques) {
        YoYo.with(techniques)
                .duration(NoisyConstants.ANIMATION_TIME_700)
                .playOn(view);
    }

    public static void makeAnimation(View view) {
        makeAnimation(view, Techniques.RubberBand);
    }

    public static String getToJson(Object o) {
        String json = "";
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            json = gson.toJson(o);
        } catch (Exception e) {
           // Log.e(TAG, "Json Error" + e.getMessage());
        }
        return json;
    }

    public static Object getFromJson(String data, Class clazz) {
        Object o = null;

        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            o = gson.fromJson(data, clazz);
        } catch (Exception e) {
            //Log.e(TAG, "Json Error" + e.getMessage());
        }

        return o;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void setPreference(Context context, String key, String value) {
        logD("saving key:" + key + " value:" + value);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPreference(Context context, String key) {
        return getPreference(context, key, null);
    }

    public static String getPreference(Context context, String key, String defaultValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(key, defaultValue);

        logD("got key:" + key + " value:" + value);
        return value;
    }

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


    public static void animateFlip(View rootLayout, View cardFace, View cardBack) {
        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.INVISIBLE) {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    public static float getFloat(Context context, @DimenRes int dimen) {
        TypedValue outValue = new TypedValue();
        context.getResources().getValue(dimen, outValue, true);
        return outValue.getFloat();
    }


    public static int getColorResource(Context context, int colorId) {
        return context.getResources().getColor(colorId);
    }

    public static String getStringResource(Context context, int stringId) {
        return context.getResources().getString(stringId);
    }

    public static String getTempString(String... args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : args) {
            stringBuilder.append(str);
        }

        return stringBuilder.toString();
    }

    public static void backPress(Activity activity) {
        activity.onBackPressed();
    }

    public static void buttonActivate(Button button) {
        button.setEnabled(true);
        //button.setBackgroundResource(R.drawable.button_green);
    }

    public static void buttonDeactivate(Button button) {
        button.setEnabled(false);
        //button.setBackgroundResource(R.drawable.button_grey);
    }

    public static void logD(String s) {
        logD("LOG", s);
    }

    public static void logD(String tag, String s) {
        Log.d(tag, s);
    }

    public static void handleError(Context context, String e) {

        Log.e(NoisyUtils.class.getCanonicalName(), NoisyConstants.ERROR + e);
        Toast.makeText(context, NoisyConstants.ERROR + e, Toast.LENGTH_SHORT).show();

    }

    public static String getTag(Class c) {
        return c.getSimpleName();
    }
}
