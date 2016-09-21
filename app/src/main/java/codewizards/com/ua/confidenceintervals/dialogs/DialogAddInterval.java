package codewizards.com.ua.confidenceintervals.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import codewizards.com.ua.confidenceintervals.DataContainer;
import codewizards.com.ua.confidenceintervals.OnDialogEventListener;
import codewizards.com.ua.confidenceintervals.R;
import codewizards.com.ua.confidenceintervals.logic.Interval;

/**
 * Created by Интернет on 02.09.2016.
 */

public class DialogAddInterval extends AbstractDialogFragment implements View.OnClickListener {
    private EditText etLeftLimit;
    private EditText etRightLimit;
    private CheckBox cbInversion;
    private CheckBox cbReflection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_interval, container, false);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);

        cbInversion = (CheckBox) view.findViewById(R.id.inversion);
        cbReflection = (CheckBox) view.findViewById(R.id.reflection);

        etLeftLimit = (EditText) view.findViewById(R.id.et_left_limit);
        etRightLimit = (EditText) view.findViewById(R.id.et_right_limit);
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
        if(etLeftLimit.getText().toString().equals("")) {
            etLeftLimit.setError("Введіть значення");
            return;
        }
        if(etLeftLimit.getText().toString().equals("-")) {
            etLeftLimit.setError("Неприпустиме значення");
            return;
        }
        if(etRightLimit.getText().toString().equals("")) {
            etRightLimit.setError("Введіть значення");
            return;
        }
        if(etRightLimit.getText().toString().equals("-")) {
            etRightLimit.setError("Неприпустиме значення");
            return;
        }
        double leftLimit = Double.valueOf(etLeftLimit.getText().toString());
        double rightLimit = Double.valueOf(etRightLimit.getText().toString());
        if(leftLimit > rightLimit) {
            etLeftLimit.setError("Ліва границя більша за праву");
        } else if(cbInversion.isChecked() && ((leftLimit < 0 && rightLimit > 0) || (leftLimit > 0 && rightLimit < 0))) {
            etLeftLimit.setError("Інверсія не може виконуватись в множині R");
        } else if(cbInversion.isChecked() && (leftLimit == 0 || rightLimit == 0)) {
            if(leftLimit == 0) {
                etLeftLimit.setError("Інверсія з нулем неможлива");
            }
            if(rightLimit == 0) {
                etRightLimit.setError("Інверсія з нулем неможлива");
            }
        } else {
            DataContainer.getInstance().addElement(new Interval(leftLimit, rightLimit, cbInversion.isChecked(), cbReflection.isChecked()));
            listener.onSuccess();
            dismiss();
        }
    }
}
