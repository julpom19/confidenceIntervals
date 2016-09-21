package codewizards.com.ua.confidenceintervals.dialogs;

import android.app.DialogFragment;
import android.view.View;

import codewizards.com.ua.confidenceintervals.OnDialogEventListener;

/**
 * Created by Интернет on 05.09.2016.
 */

abstract public class AbstractDialogFragment extends DialogFragment {
    OnDialogEventListener listener;
    public void attachListener(OnDialogEventListener listener) {
        this.listener = listener;
    }
}
