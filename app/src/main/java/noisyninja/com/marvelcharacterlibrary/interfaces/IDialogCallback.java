package noisyninja.com.marvelcharacterlibrary.interfaces;

import android.content.DialogInterface;

/**
 * Callback interface for dialogs
 * Created by ir2pid on 13/03/16.
 */
public interface IDialogCallback {

    public void ok(DialogInterface dialog);

    public void cancel(DialogInterface dialog);
}
