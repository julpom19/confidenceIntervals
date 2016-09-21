package codewizards.com.ua.confidenceintervals.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import codewizards.com.ua.confidenceintervals.DataContainer;
import codewizards.com.ua.confidenceintervals.OnDialogEventListener;
import codewizards.com.ua.confidenceintervals.R;
import codewizards.com.ua.confidenceintervals.logic.Interval;

/**
 * Created by Интернет on 05.09.2016.
 */

public class DialogAddNumber extends AbstractDialogFragment implements View.OnClickListener {
    EditText etNumber;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_number, container, false);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);
        etNumber = (EditText) view.findViewById(R.id.et_number);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                performAdding();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    void performAdding() {
        if(etNumber.getText().toString().equals("")) {
            etNumber.setError("Введіть значення");
            return;
        }
        if(etNumber.getText().toString().equals("-")) {
            etNumber.setError("Неприпустиме значення");
            return;
        }
        double number = Double.valueOf(etNumber.getText().toString());
        DataContainer.getInstance().addElement(new Interval(number));
        listener.onSuccess();
        dismiss();
    }
}
